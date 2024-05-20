package com.example.demo.service;

import com.example.demo.entity.ParkingHistory;
import com.example.demo.entity.ParkingSlot;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.repository.SlotRepository;
import com.example.demo.SizeValidator;
import com.example.demo.dto.CarInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;
@Data
@Service

public class ParkingLotService {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private SizeValidator sizeValidator;

    public String createParkingLot(int n) {
        for (int i = 0; i < n; i++) {
            ParkingSlot parkingSlot = new ParkingSlot();
            parkingSlot.setStatus(false);
            slotRepository.save(parkingSlot);
        }
        return "Parking lot created with " + n + " slots";
    }

    public String parkCar(String registrationNumber, String size) {
        List<ParkingSlot> availableParkingSlots = slotRepository.findByStatusFalse();
//        System.out.println(availableParkingSlots);
        if (availableParkingSlots.isEmpty()) {
            return "No slot available";
        }
        if (!SizeValidator.validateSize(size)){
            throw new IllegalArgumentException("Invalid slot size. Size must be small, medium, or large.");
        }

        ParkingSlot parkingSlot = availableParkingSlots.get(0);
        parkingSlot.setStatus(true);
        parkingSlot.setPlateNumber(registrationNumber);
        parkingSlot.setSize(size);
        parkingSlot.setTimeEntry(LocalDateTime.now());
        slotRepository.save(parkingSlot);
        return "The car parking in slot number : "+ parkingSlot.getId();
    }

    public String leaveSlot(String plateNumber) throws Exception {
        List<ParkingSlot> parkingSlotById = slotRepository.findByPlateNumber(plateNumber);
        if (parkingSlotById.isEmpty()) {
            return "Slot not found";
        }

        if (Boolean.FALSE.equals(parkingSlotById.get(0).getStatus())) {
            return "Slot is already vacant";
        }

        ParkingSlot parkingSlot = parkingSlotById.get(0);
        parkingSlot.setTimeExit(LocalDateTime.now());
        double parkingFee = paymentService.calculateParkingFee(parkingSlot);
        parkingSlot.setParkingFee((long) parkingFee);

        // Save parking history
        ParkingHistory parkingHistory = new ParkingHistory();
        parkingHistory.getHistory(parkingSlot);
        historyRepository.save(parkingHistory);

        // Generate PDF receipt
        pdfService.generateReceiptPdf(parkingHistory);

        // Save the receipt to S3

        // Reset parking slot and save
        parkingSlot.resetSlot();
        slotRepository.save(parkingSlot);

        return "Slot number " + parkingSlot.getId() + " is now available.";
    }

    public List<ParkingSlot> getParkingLotStatus() {
        return slotRepository.findAll();
    }

    public List<CarInfo> getPlateNumberBySize(String size) {
        if (!SizeValidator.validateSize(size)){
            throw new IllegalArgumentException("size not match");
        }
        List<ParkingSlot> parkingSlots = slotRepository.findBySizeAndStatus(size,true);
        if (parkingSlots.isEmpty()) {
            throw new IllegalArgumentException("No car with size " + size + " parked in the parking lot.");
        }
        return parkingSlots.stream()
                .map(slot -> new CarInfo(slot.getPlateNumber(), slot.getSize()))
                .toList();
    }

    public List<CarInfo> getSortBySize() {
        List<ParkingSlot> parkingSlots = slotRepository.findByStatusTrue();
        List<CarInfo> carInfoList = parkingSlots.stream()
                .map(slot -> new CarInfo(slot.getPlateNumber(), slot.getSize()))
                .toList();

        Comparator<CarInfo> sizeComparator = Comparator.comparingInt(carInfo -> {
            switch (carInfo.getSize().toLowerCase()) {
                case "small":
                    return 1;
                case "medium":
                    return 2;
                case "large":
                    return 3;
                default:
                    return 0;
            }
        });
        return carInfoList.stream()
                .sorted(sizeComparator)
                .toList();
    }


    public String deleteParkingLot() {
        slotRepository.deleteAll();
        historyRepository.deleteAll();
        return "Deleted all slots";

    }

}

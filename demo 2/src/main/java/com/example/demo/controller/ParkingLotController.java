package com.example.demo.controller;

import com.example.demo.Car;
import com.example.demo.entity.ParkingSlot;
import com.example.demo.service.ParkingLotService;
import com.example.demo.dto.CarInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;


    @PostMapping("/create_parking_lot")
    public String createParkingLot(@RequestParam int n) {
        return parkingLotService.createParkingLot(n);
    }

    @DeleteMapping("/delete_parking_lot")
    public String deleteParkingLot() {
        return parkingLotService.deleteParkingLot();
    }
    @PostMapping("/park")
    public ResponseEntity<?> parkCar(@RequestBody Car request) {
        ParkingSlot parkingSlot = parkingLotService.parkCar(request.getRegistrationNumber(), request.getSize());
        if (parkingSlot == null) {
            return ResponseEntity.status(400).body("Parking lot is full");
        }
        return ResponseEntity.ok(parkingSlot);
    }

    @PostMapping("/leave")
    public ResponseEntity<String> leaveSlot(@RequestParam String plateNumber) throws Exception {
        String response = parkingLotService.leaveSlot(plateNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/parking_lot_status")
    public ResponseEntity<List<ParkingSlot>> getParkingLotStatus() {
        List<ParkingSlot> parkingSlots = parkingLotService.getParkingLotStatus();
        return ResponseEntity.ok(parkingSlots);
    }

    @GetMapping("/registration_by_size")
    public ResponseEntity<List<CarInfo>> getRegistrationBySize(@RequestParam String size) {
        List<CarInfo> carInfoList = parkingLotService.getPlateNumberBySize(size);
        return ResponseEntity.ok(carInfoList);
    }

    @GetMapping("/slots_by_size")
    public ResponseEntity<List<CarInfo>> getSlotsBySize(@RequestParam String size) {
        List<CarInfo> carInfoList = parkingLotService.getSlotsBySize(size);
        return ResponseEntity.ok(carInfoList);
    }


}


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


    @PostMapping("/createParkingLot")
    public String createParkingLot(@RequestParam int n) {
        return parkingLotService.createParkingLot(n);
    }

    @DeleteMapping("/deleteParkingLot")
    public String deleteParkingLot() {
        return parkingLotService.deleteParkingLot();
    }

    @PostMapping("/park")
    public ResponseEntity<String> parkCar(@RequestBody Car request) {
        String response = parkingLotService.parkCar(request.getRegistrationNumber(), request.getSize());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/exitParkingLot")
    public ResponseEntity<String> leaveSlot(@RequestParam String plateNumber) throws Exception {
        String response = parkingLotService.leaveSlot(plateNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/parkingStatus")
    public ResponseEntity<List<ParkingSlot>> getParkingLotStatus() {
        List<ParkingSlot> parkingSlots = parkingLotService.getParkingLotStatus();
        return ResponseEntity.ok(parkingSlots);
    }

    @GetMapping("/plateNumberBySize")
    public ResponseEntity<List<CarInfo>> getRegistrationBySize(@RequestParam String size) {
        List<CarInfo> carInfoList = parkingLotService.getPlateNumberBySize(size);
        return ResponseEntity.ok(carInfoList);
    }

    @GetMapping("/sortSlotsBySize")
    public ResponseEntity<List<CarInfo>> getPlateNumberSortBySize() {
        List<CarInfo> carInfoList = parkingLotService.getSortBySize();
        return ResponseEntity.ok(carInfoList);
    }


}


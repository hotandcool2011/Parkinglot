package com.example.demo.dto;

import lombok.Data;

@Data
public class CarInfo {
    private String plateNumber;
    private String size;

    public CarInfo(String plateNumber, String size) {
        this.plateNumber = plateNumber;
        this.size = size;
    }
}

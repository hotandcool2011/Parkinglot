package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "slots")
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "size")
    private String size;

    @Column(name = "time_entry")
    private LocalDateTime timeEntry;

    @Column(name = "time_exit")
    private LocalDateTime timeExit;

    @Column(name = "parking_fee",columnDefinition = "BIGINT DEFAULT 0")
    private long parkingFee;

    public void resetSlot() {
        this.setStatus(false);
        this.setPlateNumber(null);
        this.setSize(null);
        this.setTimeEntry(null);
        this.setTimeExit(null);
        this.setParkingFee(0L);
    }


}

package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "parking_history")
public class ParkingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @Column(name = "slot_number")
    private Long slotNumber;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "size")
    private String size;

    @Column(name = "time_entry")
    private LocalDateTime timeEntry;

    @Column(name = "time_exit")
    private LocalDateTime timeExit;

    @Column(name = "parking_fee", columnDefinition = "BIGINT DEFAULT 0")
    private long parkingFee;

    public void getHistory(ParkingSlot parkingSlot) {
        this.setSlotNumber(parkingSlot.getId());
        this.setPlateNumber(parkingSlot.getPlateNumber());
        this.setSize(parkingSlot.getSize());
        this.setTimeEntry(parkingSlot.getTimeEntry());
        this.setTimeExit(parkingSlot.getTimeExit());
        this.setParkingFee(parkingSlot.getParkingFee());
    }


}

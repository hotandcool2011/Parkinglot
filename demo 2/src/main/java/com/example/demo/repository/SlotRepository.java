package com.example.demo.repository;

import com.example.demo.entity.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<ParkingSlot, Long> {

    List<ParkingSlot> findByPlateNumber(String plateNumber);
    List<ParkingSlot> findByStatusFalse();
    List<ParkingSlot> findBySizeAndStatus(String size, Boolean status);
    List<ParkingSlot> findByStatusTrue();

    List<ParkingSlot> findBySize(String size);
}

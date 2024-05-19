package com.example.demo.repository;

import com.example.demo.entity.ParkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<ParkingHistory, Long> {
}
package com.example.demo.service;

import com.example.demo.entity.ParkingSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class PaymentService {
    @Autowired
    private PdfService pdfService;


    public boolean validatePayment(String payment) {
            return payment != null && (payment.equals("cash") || payment.equals("credit"));
        }

        public double calculateParkingFee(ParkingSlot parkingSlot) {
            Duration duration = Duration.between(parkingSlot.getTimeEntry(), parkingSlot.getTimeExit());
            long hours = duration.toHours();
            System.out.println(hours);
            int fee = 0;
            long totalfee;
            switch (parkingSlot.getSize()) {
                case "small" -> fee = 10;
                case "medium" -> fee = 15;
                case "large" -> fee = 20;
            }

            if (hours <= 1) {
                totalfee= 0;
            } else {
                totalfee = fee * (hours - 1);
            }
            return totalfee;
        }
}

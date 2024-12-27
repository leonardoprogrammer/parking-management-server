package com.parkingmanagement.qrpayment.service;

import com.parkingmanagement.qrpayment.entity.PaymentRecord;
import com.parkingmanagement.qrpayment.repository.PaymentRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRecordRepository paymentRecordRepository;

    public PaymentRecord createPayment(Long vehicleRecordId, String qrCodeData, Double amount) {
        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setVehicleRecordId(vehicleRecordId);
        paymentRecord.setQrCodeData(qrCodeData);
        paymentRecord.setAmount(amount);
        paymentRecord.setGeneratedAt(LocalDateTime.now());
        paymentRecord.setPaid(false);
        return paymentRecordRepository.save(paymentRecord);
    }

    public PaymentRecord markAsPaid(Long paymentId) {
        PaymentRecord paymentRecord = paymentRecordRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment record not found"));
        paymentRecord.setPaid(true);
        return paymentRecordRepository.save(paymentRecord);
    }
}

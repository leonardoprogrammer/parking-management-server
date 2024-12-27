package com.parkingmanagement.qrpayment.controller;

import com.parkingmanagement.qrpayment.entity.PaymentRecord;
import com.parkingmanagement.qrpayment.service.PaymentService;
import com.parkingmanagement.qrpayment.service.QRCodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private QRCodeGeneratorService qrCodeGeneratorService;

    @PostMapping("/create")
    public ResponseEntity<PaymentRecord> createPayment(@RequestParam Long vehicleRecordId,
                                                       @RequestParam String qrCodeData,
                                                       @RequestParam Double amount) {
        PaymentRecord payment = paymentService.createPayment(vehicleRecordId, qrCodeData, amount);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/mark-paid")
    public ResponseEntity<PaymentRecord> markAsPaid(@PathVariable Long id) {
        PaymentRecord payment = paymentService.markAsPaid(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @GetMapping("/generate-qr")
    public ResponseEntity<BufferedImage> generateQRCode(@RequestParam String data) {
        BufferedImage qrImage = qrCodeGeneratorService.generateQRCode(data, 250, 250);
        return new ResponseEntity<>(qrImage, HttpStatus.OK);
    }
}

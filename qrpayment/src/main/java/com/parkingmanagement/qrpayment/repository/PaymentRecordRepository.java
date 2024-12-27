package com.parkingmanagement.qrpayment.repository;

import com.parkingmanagement.qrpayment.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {
}

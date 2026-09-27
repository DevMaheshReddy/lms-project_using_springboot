package com.lms.service;

import java.util.List;

import com.lms.dto.PaymentRequestDTO;
import com.lms.dto.PaymentResponseDTO;

public interface PaymentService {

    String makePayment(PaymentRequestDTO dto);
    List<PaymentResponseDTO> getPaymentsByEnrollment(Long enrollmentId);
}
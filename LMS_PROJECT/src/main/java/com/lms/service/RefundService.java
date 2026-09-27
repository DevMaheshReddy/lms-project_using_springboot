package com.lms.service;

import java.util.List;
import com.lms.entity.Refund;

public interface RefundService {

    Refund createRefund(Long enrollmentId);

    List<Refund> getRefundsByEnrollment(Long enrollmentId);
}
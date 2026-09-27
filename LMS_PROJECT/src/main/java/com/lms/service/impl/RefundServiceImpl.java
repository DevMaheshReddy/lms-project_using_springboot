package com.lms.service.impl;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import com.lms.entity.*;
import com.lms.exception.ResourceNotFoundException;
import com.lms.repository.*;
import com.lms.service.RefundService;

@Service
public class RefundServiceImpl implements RefundService {

	private final RefundRepository refundRepo;
	private final EnrollmentRepository enrollmentRepo;
	private final PaymentRepository paymentRepo;

	public RefundServiceImpl(RefundRepository refundRepo, EnrollmentRepository enrollmentRepo,
			PaymentRepository paymentRepo) {
		this.refundRepo = refundRepo;
		this.enrollmentRepo = enrollmentRepo;
		this.paymentRepo = paymentRepo;
	}

	@Override
	public Refund createRefund(Long enrollmentId) {

		Enrollment enrollment = enrollmentRepo.findById(enrollmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));

		List<Payment> payments = paymentRepo.findByEnrollmentId(enrollmentId);

		Double totalPaid = payments.stream().map(Payment::getAmount).reduce(0.0, Double::sum);

		Double fee = enrollment.getCourse().getFee();

		Double refundAmount;

		if (totalPaid == 0) {
			refundAmount = 0.0;
		} else if (totalPaid < fee) {
			refundAmount = totalPaid * 0.5;
		} else {
			refundAmount = totalPaid * 0.8;
		}

		Refund refund = new Refund();
		refund.setAmount(refundAmount);
		refund.setRefundDate(LocalDateTime.now());
		refund.setEnrollment(enrollment);

		return refundRepo.save(refund);
	}

	@Override
	public List<Refund> getRefundsByEnrollment(Long enrollmentId) {
		return refundRepo.findByEnrollmentId(enrollmentId);
	}
}
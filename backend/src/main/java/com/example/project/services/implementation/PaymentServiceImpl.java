package com.example.project.services.implementation;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.project.dtos.responses.PaymentResponse;
import com.example.project.dtos.responses.ReceiptResponse;
import com.example.project.exception.EntityDoesNotExist;
import com.example.project.models.Payment;
import com.example.project.models.Receipt;
import com.example.project.repositories.PaymentRepository;
import com.example.project.services.PaymentService;


@Service
public class PaymentServiceImpl implements PaymentService {
	
	private final PaymentRepository paymentRepository;
	
	public PaymentServiceImpl(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}
	
	@Override
	public List<PaymentResponse> findAll() {
		
		return mapToPaymentResponseList(paymentRepository.findAll());
	}

	@Override
	public PaymentResponse create(Payment payment) {
		
		return mapToPaymentResponse(paymentRepository.save(payment));
	}

	@Override
	public void delete(Integer id) {
		
		Payment payment = paymentRepository.findById(id)
				.orElseThrow(()-> new EntityDoesNotExist(id));
		
		paymentRepository.delete(payment);
		
	}

	@Override
	public PaymentResponse update(Payment payment) {
		
		paymentRepository.findById(payment.getPaymentId())
				.orElseThrow(()-> new EntityDoesNotExist(payment.getPaymentId()));
		
		return mapToPaymentResponse(paymentRepository.save(payment));
	}

	@Override
	public PaymentResponse findById(Integer id) {
		
		Payment payment = paymentRepository.findById(id)
				.orElseThrow(()-> new EntityDoesNotExist(id));
		return mapToPaymentResponse(payment);
	}
	

	@Override
	public List<PaymentResponse> findByReceiptId(Integer receiptId) {
		return mapToPaymentResponseList(paymentRepository.findByReceiptReceiptId(receiptId));
	}
	
	@Override
	public List<PaymentResponse> findByAuthuserId(Integer userId) {
		return mapToPaymentResponseList(paymentRepository.findAllByReceiptClientCompanyAuthuserId(userId));
	}
	
	@Override
	public List<PaymentResponse> findByCompanyIdForLast365(Integer companyId) {
		List<Payment> payments = paymentRepository.findAllByReceiptClientCompanyCompanyId(companyId);
		List<Payment> filteredPayments = new ArrayList<Payment>();
		final LocalDate newDate = LocalDate.now(ZoneId.of("UTC")).minus(Period.ofDays(365));
		for (int i = 0; i <= payments.size() - 1; i++) {
			if (payments.get(i).getDateOfIssue().isAfter(newDate)) {
				filteredPayments.add(payments.get(i));
			}
		}
		return mapToPaymentResponseList(filteredPayments);
	
	}
	
	
    private List<PaymentResponse> mapToPaymentResponseList(List<Payment> payments) {
        return payments.stream().map(this::mapToPaymentResponse).collect(Collectors.toList());
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
            .paymentId(payment.getPaymentId())
            .amount(payment.getAmount())
            .dateOfIssue(payment.getDateOfIssue())
            .receipt(payment.getReceipt())
            .build();
    }
}

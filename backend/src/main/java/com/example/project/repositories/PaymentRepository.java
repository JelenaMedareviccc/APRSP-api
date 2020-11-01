package com.example.project.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.project.models.Payment;
import com.example.project.models.Receipt;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	List<Payment> findByReceiptReceiptId (Integer receiptId);	
    List<Payment> findAllByReceiptClientCompanyAuthuserId( Integer id);
    List<Payment> findAllByReceiptClientClientId(Integer id);
    List<Payment> findAllByReceiptClientCompanyCompanyId(Integer id);

}

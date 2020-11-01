package com.example.project.dtos.responses;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.project.models.Authuser;
import com.example.project.models.Receipt;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

	private Integer paymentId;
	
	private double amount;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
    private LocalDate dateOfIssue;
	
	private Receipt receipt;
}

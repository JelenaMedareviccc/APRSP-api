package com.example.project.dtos.responses;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.example.project.models.Authuser;
import com.example.project.models.Client;
import com.example.project.models.Item;
import com.example.project.models.Payment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptResponse {

	private Integer receiptId;

    private LocalDate dateOfIssue;
	
	private int timeLimit;
	
	private String year;
	
	private String receiptNumber;
	
	private Client client;
	
	private double debt;
	
	private double totalAmount;
	
	private int percentageInterest;

}

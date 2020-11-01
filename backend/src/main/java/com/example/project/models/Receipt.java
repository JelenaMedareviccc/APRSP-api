package com.example.project.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"payments", "items"})
@Table(name="receipt")
public class Receipt implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer receiptId;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
	@Column(name="date_of_issue")
	private LocalDate dateOfIssue;
	
	@NotNull
	private int timeLimit;
	
	@Pattern(regexp = "^[0-9]{5}$", message = "Invalid format.")
	@Column(name="receipt_number", unique=true)
	private String receiptNumber;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy")
	@Column(name="year")
	private String year;
	
	@Min(0)
	@Max(100)
	@Column(name="percentage_interest")
	private int percentageInterest = 0;

	@ManyToOne
	@JoinColumn(name = "client")
	private Client client;
	
	@OneToMany(mappedBy = "receipt",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Payment> payments;
	
	@OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Item> items;
	
	@Transient
	private double debt;
	
	@Transient
	private double totalAmount;

}

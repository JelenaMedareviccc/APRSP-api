package com.example.project.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Lazy;

import com.example.project.enums.Measure;

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
@Table(name="item")
public class Item implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer itemId;

	//@NotBlank(message = "Price cannot be empty!")
	@NotNull
	private double price;
	
	@NotNull
	@Min(1)
	@Max(999999)
	private int measure;
	
	@NotBlank
	@Length(min = 3, max = 100, message = "Invalid format")
	private String name;
	
	@Transient
	private double totalPrice;
	
	@ManyToOne
	@JoinColumn(name = "receipt")
	private Receipt receipt;
	
	public double getTotalPrice() {
		return this.measure*this.price;
	}
}

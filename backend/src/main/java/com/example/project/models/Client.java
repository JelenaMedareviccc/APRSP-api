package com.example.project.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Lazy;

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
@ToString(exclude = {"receipts"})
@Table(name="client")
public class Client implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer clientId;
	
	@NotBlank(message="Client name cannot be empty!")
	@Length(min = 3, max = 40, message = "Invalid format")
	@Column(unique=true)
	private String name;
	
	@NotBlank(message="Client registration number cannot be empty!")
	@Pattern(regexp = "^[0-9]{6}$", message = "Invalid format.")
	@Column(name="client_reg_number", unique = true)
	private String clientRegNumber;
	
	@NotBlank
	@Length(min = 5, max = 40)
	private String address;
	
	@NotBlank
	@Pattern(regexp = "^\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})", message = "Phone number format is invalid!")
	private String contact;
	
	@NotBlank
	@Email(message="Email is not valid!")
	@Column(unique = true)
	private String email;
	
	@NotBlank
	@Pattern(regexp = "^\\d{3}[-]{0,1}\\d{13}[-]\\d{2}$", message = "Invalid format.")
	@Column(name="account_number", unique = true)
	private String accountNumber;
	
	@ManyToOne
	@JoinColumn(name="company")
	private Company company;
	
	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Receipt> receipts;
	
}

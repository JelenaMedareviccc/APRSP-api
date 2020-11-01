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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.context.annotation.Lazy;

import com.example.project.dtos.GetAuthuserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"clients"})
@Table(name="company")
public class Company implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer companyId;
	
	@NotBlank(message="Name cannot be empty!")
    @Size(min=3, max = 30)
	@Column(unique = true)
	private String name;
	
	@NotBlank(message="PIB cannot be empty!")
	@Pattern(regexp = "^[0-9]{9}$", message = "Invalid format.")
	@Column(unique = true)
	private String pib;
	
	@Length(min = 5, max = 70)
	private String address;   
	
	@Pattern(regexp = "^\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})", message = "Phone number format is invalid!")
	private String contact;
	
	@NotBlank
	@Email(message="Email is not valid!")
	@Column(unique = true)
	private String email;
	
	@Pattern(regexp = "^\\d{3}[-]{0,1}\\d{13}[-]\\d{2}$", message = "Invalid format.")
	@Column(name="account_number", unique = true)
	private String accountNumber;
	
	@NotBlank
	private String currency;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Client> clients;
	
	@ManyToOne
	@JoinColumn(name="authuser")
	private Authuser authuser;
	

}

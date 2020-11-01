package com.example.project.models;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity

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
	private String name;
	
	@NotBlank(message="PIB cannot be empty!")
	private String pib;
	
	private String address;
	
	@Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Format of number must be +1 123456789")
	private String contact;
	
	@NotBlank
	@Email(message="Email is not valid!")
	private String email;
	
	private String account_number;

	@NotBlank(message="Password cannot be empty!")
	private String password;

	@Lazy
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Client> clients;
	
	public Company() {	
	}
	
	public Company(Integer companyId, String name, String pib, String address, String contact, String email,
			String account_number) {
		this.companyId = companyId;
		this.name = name;
		this.pib = pib;
		this.address = address;
		this.contact = contact;
		this.email = email;
		this.account_number = account_number;
	
	
	}
	
	
	public Integer getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPib() {
		return pib;
	}
	
	public void setPib(String pib) {
		this.pib = pib;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getContact() {
		return contact;
	}

	public String getPassword() {
		return password;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAccount_number() {
		return account_number;
	}
	
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

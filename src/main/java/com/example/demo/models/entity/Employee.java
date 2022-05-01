package com.example.demo.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="employees")

public class Employee implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String name;
	
	@NotEmpty
	@Column(name="last_name")
	private String lastName;
	
	@NotNull
	@Column(name="date_of_birth")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private Date dateOfBirth;
	
	@NotEmpty
	@Email
	private String email;
	
	
	
	
	
	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getLastName() {
		return lastName;
	}





	public void setLastName(String lastName) {
		this.lastName = lastName;
	}





	public Date getDateOfBirth() {
		return dateOfBirth;
	}





	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}





	public static long getSerialversionuid() {
		return serialVersionUID;
	}





	private static final long serialVersionUID = 1L;

}

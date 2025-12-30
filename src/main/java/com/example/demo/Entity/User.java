package com.example.demo.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String email;
	private String userName;
	private String password;
	private String contactNo;//
	private String gender;
	private String dateOfBirth;
	private String currentAddress;
	private String permanentAddress;
	private String profession;
	private String idProofNumber;
	private String guardianName;
	private String guardianContact;

	//Used for email verfication
	@Column(nullable = false)
	private boolean emailVerified = false;
	private String otp;
	private LocalDateTime otpExpiryTime;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDate;

	@UpdateTimestamp
	private LocalDateTime updatedDate;

	@OneToOne
	@JsonIgnore
	@JoinColumn(name = "bedId", referencedColumnName = "id")
	private Bed bed;
	
	

	
}
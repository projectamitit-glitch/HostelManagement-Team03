package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Bed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int bedNo;
	private String status;
	private int price;
	private int deposit;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "roomId", referencedColumnName = "id")
	private Room room;

	
	@OneToOne(mappedBy = "bed")
	@JsonIgnore
	private User user;


}

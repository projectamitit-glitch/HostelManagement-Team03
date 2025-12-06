package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

	@OneToOne(mappedBy = "bed", cascade = CascadeType.ALL)
	@JsonIgnore
	private User user;

}

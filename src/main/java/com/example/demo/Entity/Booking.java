package com.example.demo.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Booking {
		
	            @Id
			    @GeneratedValue(strategy = GenerationType.IDENTITY)
			    private int id;
			    private LocalDateTime createdDate;
			    private LocalDateTime endDate;
			    private int finalPrice;
			    private String status;
			    private int bedId;
			    private int userId;

			    
			    @OneToOne(mappedBy = "booking")
			    private Payment payment;


				public int getId() {
					return id;
				}


				public void setId(int id) {
					this.id = id;
				}


				public LocalDateTime getCreatedDate() {
					return createdDate;
				}


				public void setCreatedDate(LocalDateTime createdDate) {
					this.createdDate = createdDate;
				}


				public LocalDateTime getEndDate() {
					return endDate;
				}


				public void setEndDate(LocalDateTime endDate) {
					this.endDate = endDate;
				}


				public int getFinalPrice() {
					return finalPrice;
				}


				public void setFinalPrice(int finalPrice) {
					this.finalPrice = finalPrice;
				}


				public String getStatus() {
					return status;
				}


				public void setStatus(String status) {
					this.status = status;
				}


				public int getBedId() {
					return bedId;
				}


				public void setBedId(int bedId) {
					this.bedId = bedId;
				}


				public int getUserId() {
					return userId;
				}


				public void setUserId(int userId) {
					this.userId = userId;
				}


				public Payment getPayment() {
					return payment;
				}


				public void setPayment(Payment payment) {
					this.payment = payment;
				}

}

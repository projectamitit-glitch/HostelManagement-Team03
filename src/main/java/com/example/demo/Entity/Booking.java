package com.example.demo.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Booking {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
	    private Long userId;
	    private int finalPrice;
	    private String status;
	    private LocalDateTime startDate;
	    private LocalDateTime endDate;

	    @ManyToOne
	    @JoinColumn(name = "bed_id")
	    private Bed bed;

	    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
	    private Payment payment;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
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

		public LocalDateTime getStartDate() {
			return startDate;
		}

		public void setStartDate(LocalDateTime startDate) {
			this.startDate = startDate;
		}

		public LocalDateTime getEndDate() {
			return endDate;
		}

		public void setEndDate(LocalDateTime endDate) {
			this.endDate = endDate;
		}

		public Bed getBed() {
			return bed;
		}

		public void setBed(Bed bed) {
			this.bed = bed;
		}

		public Payment getPayment() {
			return payment;
		}

		public void setPayment(Payment payment) {
			this.payment = payment;
		}
	    
	    
}

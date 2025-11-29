package com.example.demo.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Payment {

            @Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
		    private int id;
	        private Long transactionId; 
	        private int amount; 
	        private Long orderId; 
	        private String status; 
	        private LocalDateTime paymentDate; 

		    @OneToOne
		    @JoinColumn(name = "booking_id") 
		    private Booking booking;

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public Long getTransactionId() {
				return transactionId;
			}

			public void setTransactionId(Long transactionId) {
				this.transactionId = transactionId;
			}

			public int getAmount() {
				return amount;
			}

			public void setAmount(int amount) {
				this.amount = amount;
			}

			public Long getOrderId() {
				return orderId;
			}

			public void setOrderId(Long orderId) {
				this.orderId = orderId;
			}

			public String getStatus() {
				return status;
			}

			public void setStatus(String status) {
				this.status = status;
			}

			public LocalDateTime getPaymentDate() {
				return paymentDate;
			}

			public void setPaymentDate(LocalDateTime paymentDate) {
				this.paymentDate = paymentDate;
			}

			public Booking getBooking() {
				return booking;
			}

			public void setBooking(Booking booking) {
				this.booking = booking;
			}

		    
		    
		


	}


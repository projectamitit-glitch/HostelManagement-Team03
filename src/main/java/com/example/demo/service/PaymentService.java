package com.example.demo.service;

public interface PaymentService {

	public void updatePayment(long orderId, long transactionId, boolean success, int bookingId);
}

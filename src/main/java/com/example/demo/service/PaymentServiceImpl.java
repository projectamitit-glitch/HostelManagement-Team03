package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Booking;
import com.example.demo.Entity.Payment;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void updatePayment(int orderId, int transactionId, boolean success, int paymentId) {

        Payment payment = paymentRepository.findById(paymentId).get();
        if (payment == null) {
            throw new RuntimeException("PAYMENT NOT FOUND");
        }

        Booking booking = payment.getBooking();

        payment.setOrderId(orderId);
        payment.setTransactionId(transactionId);

        if (success) {
            payment.setStatus("SUCCESS");
            booking.setStatus("CONFIRMED");
        } else {
            payment.setStatus("FAILED");
            booking.setStatus("CANCELLED");
        }

        payment.setBooking(booking);

  
        paymentRepository.save(payment);
        bookingRepository.save(booking);
    }
}

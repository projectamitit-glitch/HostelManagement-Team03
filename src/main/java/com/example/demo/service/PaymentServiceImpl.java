package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Booking;
import com.example.demo.Entity.Payment;
import com.example.demo.constant.ErrorConstant;
import com.example.demo.exception.PaymentServiceException;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void updatePayment(long orderId, long transactionId, boolean success, int bookingId) {

    	Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);

    	if (optionalBooking.isEmpty()) {
    	    throw new PaymentServiceException(ErrorConstant.PAYMENT_NOT_FOUND,HttpStatus.NOT_FOUND);
    	}

    	Booking booking = optionalBooking.get();


       Payment payment = new Payment();

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

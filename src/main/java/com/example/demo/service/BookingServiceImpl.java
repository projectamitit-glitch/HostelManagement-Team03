package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Bed;
import com.example.demo.Entity.Booking;
import com.example.demo.Entity.Payment;
import com.example.demo.Entity.User;
import com.example.demo.repository.BedRepository;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.UserRepository;

@Service
public class BookingServiceImpl implements BookingService{
	    @Autowired
	    BedRepository bedRepository;

	    @Autowired
	    BookingRepository bookingRepository;

	    @Autowired
	    PaymentRepository paymentRepository;

	    @Autowired
	    UserRepository userRepository;

	    @Override
	    public void createBooking(int userId, int bedId, int finalAmount) {

	    
	        User user = userRepository.findById(userId).get();
	        if (user == null) {
	            throw new RuntimeException("USER NOT FOUND");
	        }
	        
	        Bed bed = bedRepository.findById(bedId).get();
	        if (bed == null) {
	            throw new RuntimeException("BED NOT FOUND");
	        }

	        Booking booking = new Booking();
	        booking.setUserId(userId);
	        booking.setBedId(bedId);
	        booking.setFinalPrice(finalAmount);
	        booking.setCreatedDate(LocalDateTime.now());
	        booking.setStatus("IN PROGRESS");

	        bookingRepository.save(booking);

	        
	        Payment payment = new Payment();
	        payment.setBooking(booking);
	        payment.setStatus("PENDING");

	        paymentRepository.save(payment);

	        
	        booking.setPayment(payment);
	        bookingRepository.save(booking);

	    }
	}



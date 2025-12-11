package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Bed;
import com.example.demo.Entity.Booking;
import com.example.demo.Entity.Payment;
import com.example.demo.Entity.User;
import com.example.demo.constant.ErrorConstant;
import com.example.demo.exception.BookingServiceException;
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

	    
	    	Optional<User> optionalUser = userRepository.findById(userId);
	    	if (optionalUser.isEmpty()) {
	    	    throw new BookingServiceException(ErrorConstant.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
	    	}
	    	User user = optionalUser.get();


	    	Optional<Bed> optionalBed = bedRepository.findById(bedId);
	    	if (optionalBed.isEmpty()) {
	    	    throw new BookingServiceException(ErrorConstant.BED_NOT_FOUND, HttpStatus.NOT_FOUND);
	    	}
	    	
	    	Bed bed = optionalBed.get();
	    	
	    	if (!"AVAILABLE".equalsIgnoreCase(bed.getStatus())) {
	    	    throw new BookingServiceException(ErrorConstant.BED_NOT_AVAILABLE, HttpStatus.BAD_REQUEST);
	    	}
	    
	    
	    	Booking booking = new Booking();
	        booking.setUserId(userId);
	        booking.setBedId(bedId);
	        booking.setFinalAmount(finalAmount);
	        booking.setCreatedDate(LocalDateTime.now());
	        booking.setStatus("IN PROGRESS");
	        bookingRepository.save(booking);
	        
	        Payment payment = new Payment();
	        payment.setBooking(booking);
	        payment.setStatus("PENDING");
	        booking.setPayment(payment);
	        
	        paymentRepository.save(payment);
	        bookingRepository.save(booking);

	    }
	    	
}
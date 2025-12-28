package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constant.Constant;
import com.example.demo.service.BookingService;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/createbooking")
    public ResponseEntity createBooking(@RequestParam int userId,@RequestParam int bedId,@RequestParam int finalAmount) {

        bookingService.createBooking(userId, bedId, finalAmount);

        return new ResponseEntity (Constant.BOOKING_CREATED, HttpStatus.CREATED);
    }
}


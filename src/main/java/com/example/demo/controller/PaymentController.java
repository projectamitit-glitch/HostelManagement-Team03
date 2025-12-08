package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constant.Constant;
import com.example.demo.service.PaymentService;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/updatepayment")
    public ResponseEntity updatePayment(@RequestParam int orderId,@RequestParam int transactionId,@RequestParam boolean success,@RequestParam int paymentId) {

        paymentService.updatePayment(orderId, transactionId, success, paymentId);

        return new ResponseEntity(Constant.PAYMENT_UPDATED, HttpStatus.OK);
    }
}

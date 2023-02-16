package com.project.controller;

import com.project.dto.request.ProductRequest;
import com.project.model.Payment;
import com.project.model.Product;
import com.project.repository.ProductRepository;
import com.project.response.ResponseResult;
import com.project.service.PaymentService;
import com.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;


    @GetMapping("/getAllPayment")
    List<Payment> getAll(){
        return (List<Payment>) paymentService.getAllPayment();
    }

    @GetMapping("/{id}")
        //Let's return an object with: data, message, status
    ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        return paymentService.findById(id);
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseResult> insertPayment(@RequestBody Payment newPay) {
        return paymentService.insertPay(newPay);

    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseResult> updatePayment(@RequestBody  Payment newPay, @PathVariable Long id) {
        return paymentService.updatePayment(newPay,id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseResult> deletePayment(@PathVariable Long id) {
        return paymentService.deletePay(id);
    }
}

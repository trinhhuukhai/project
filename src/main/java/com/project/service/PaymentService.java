package com.project.service;

import com.project.model.Payment;
import com.project.model.Shipping;
import com.project.repository.PaymentRepository;
import com.project.repository.ShippingRepository;
import com.project.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayment(){
        return paymentRepository.findAll();
    }

    public ResponseEntity<ResponseResult> insertPay(Payment newPay) {

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Insert Customer successfully", paymentRepository.save(newPay))
        );
    }


    public ResponseEntity<ResponseResult> updatePayment(@RequestBody Payment newPay, @PathVariable Long id) {
        Payment updatedPay = paymentRepository.findById(id)
                .map(pay -> {
                    pay.setPaymentMethod(newPay.getPaymentMethod());
                    pay.setAmount(newPay.getAmount());
                    pay.setPaidDate(newPay.getPaidDate());
                    return paymentRepository.save(pay);
                }).orElseGet(() -> {
                    newPay.setId(id);
                    return paymentRepository.save(newPay);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Update shipping successfully", updatedPay)
        );
    }

    //Delete a Product => DELETE method
    public ResponseEntity<ResponseResult> deletePay(@PathVariable Long id) {
        boolean exists = paymentRepository.existsById(id);
        if(exists) {
            paymentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseResult("ok", "Delete payment successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseResult("failed", "Cannot find payment to delete", "")
        );
    }

    //Let's return an object with: data, message, status
    public ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        Optional<Payment> foundPay = paymentRepository.findById(id);
        return foundPay.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseResult("ok", "Query payment successfully", foundPay)
                        //you can replace "ok" with your defined "error code"
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseResult("failed", "Cannot find payment with id = "+id, "")
                );
    }
}

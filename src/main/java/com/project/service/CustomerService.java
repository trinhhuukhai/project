package com.project.service;

import com.project.model.Customer;
import com.project.repository.CustomerRepository;
import com.project.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public ResponseEntity<ResponseResult> insertCus(Customer newCus) {
        //2 products must not have the same name !
        List<Customer> foundCus = customerRepository.findByName(newCus.getName().trim());
        if(foundCus.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseResult("failed", "Product name already taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Insert Product successfully", customerRepository.save(newCus))
        );
    }
}

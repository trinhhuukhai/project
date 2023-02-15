package com.project.controller;

import com.project.model.Customer;
import com.project.response.ResponseResult;
import com.project.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor

public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/getAllCustomer")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    List<Customer> getAll(){
        return customerService.getAllCustomer();
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<ResponseResult> insertProduct(@RequestBody Customer newCus) {
        return customerService.insertCus(newCus);

    }


}

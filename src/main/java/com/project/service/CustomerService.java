package com.project.service;

import com.project.model.Customer;
import com.project.repository.CustomerRepository;
import com.project.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public ResponseEntity<ResponseResult> insertCus(Customer newCus) {
        //2 products must not have the same name !
//        List<Customer> foundCus = customerRepository.findByName(newCus.getName().trim());
//        if(foundCus.size() > 0) {
//            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
//                    new ResponseResult("failed", "Customer name already taken", "")
//            );
//        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Insert Customer successfully", customerRepository.save(newCus))
        );
    }


    public ResponseEntity<ResponseResult> updateCustomer(@RequestBody Customer newCus, @PathVariable Long id) {
        Customer updatedCus = customerRepository.findById(id)
                .map(cus -> {
                    cus.setName(newCus.getName());
                    cus.setEmail(newCus.getEmail());
                    cus.setPhone(newCus.getPhone());
                    cus.setAddress(newCus.getAddress());
                    return customerRepository.save(cus);
                }).orElseGet(() -> {
                    newCus.setId(id);
                    return customerRepository.save(newCus);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Update Customer successfully", updatedCus)
        );
    }

    //Delete a Product => DELETE method
    public ResponseEntity<ResponseResult> deleteCustomer(@PathVariable Long id) {
        boolean exists = customerRepository.existsById(id);
        if(exists) {
            customerRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseResult("ok", "Delete Customer successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseResult("failed", "Cannot find Customer to delete", "")
        );
    }

        //Let's return an object with: data, message, status
    public ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        Optional<Customer> foundProduct = customerRepository.findById(id);
        return foundProduct.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseResult("ok", "Query Customer successfully", foundProduct)
                        //you can replace "ok" with your defined "error code"
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseResult("failed", "Cannot find Customer with id = "+id, "")
                );
    }
}

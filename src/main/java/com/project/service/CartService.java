package com.project.service;

import com.project.dto.request.CartRequest;
import com.project.dto.request.ProductRequest;
import com.project.model.Cart;
import com.project.model.Category;
import com.project.model.Customer;
import com.project.model.Product;
import com.project.repository.CartRepository;
import com.project.repository.CategoryRepository;
import com.project.repository.CustomerRepository;
import com.project.repository.ProductRepository;
import com.project.response.ResponseResult;
import com.project.service.iService.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Cart> getAllCart(){
        return cartRepository.findAll();
    }


    public ResponseEntity<ResponseResult> insertCart(@RequestBody CartRequest newCart) {

        Customer customer = customerRepository.findById(newCart.getCustomerId()).orElseThrow();

        Cart cart = new Cart();
        cart.setCustomer(customer);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Insert Product successfully",cartRepository.save(cart),1)
        );
    }

    public ResponseEntity<ResponseResult> updateCart(@RequestBody CartRequest newCart, @PathVariable Long id) {

        Customer customer = customerRepository.findById(newCart.getCustomerId()).orElseThrow();
        Optional<Cart> updatedCart = cartRepository.findById(id)
                .map(cart -> {
                    cart.setCustomer(customer);
                    return cartRepository.save(cart);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Update Customer successfully", updatedCart,1)
        );
    }

    //Delete a Product => DELETE method
    public ResponseEntity<ResponseResult> deleteCart(@PathVariable Long id) {
        boolean exists = cartRepository.existsById(id);
        if(exists) {
            cartRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseResult("ok", "Delete cart successfully", "",1)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseResult("failed", "Cannot find Customer to delete", "",1)
        );
    }

    //Let's return an object with: data, message, status
    public ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        Optional<Cart> foundCart = cartRepository.findById(id);
        return foundCart.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseResult("ok", "Query Customer successfully", foundCart,1)
                        //you can replace "ok" with your defined "error code"
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseResult("failed", "Cannot find Customer with id = "+id, "",1)
                );
    }

}

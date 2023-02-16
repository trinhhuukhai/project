package com.project.controller;

import com.project.dto.request.CartRequest;
import com.project.dto.request.ProductRequest;
import com.project.model.Cart;
import com.project.model.Product;
import com.project.repository.ProductRepository;
import com.project.response.ResponseResult;
import com.project.service.CartService;
import com.project.service.CustomerService;
import com.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/getAllCart")
    List<Cart> getAll(){
        return (List<Cart>) cartService.getAllCart();
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseResult> insertProduct(@RequestBody CartRequest newCart) {
        return cartService.insertCart(newCart);

    }

}

package com.project.controller;

import com.project.dto.request.CartItemRequest;
import com.project.dto.request.CartRequest;
import com.project.model.Cart;
import com.project.model.CartItem;
import com.project.response.ResponseResult;
import com.project.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cartItem")
@CrossOrigin("http://localhost:3000")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;
    @GetMapping("/getAllCartItem")
    List<CartItem> getAll(){
        return (List<CartItem>) cartItemService.getAllCart();
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseResult> insertProduct(@RequestBody CartItemRequest newCart) {
        return cartItemService.insertCartItem(newCart);

    }
}

package com.project.controller;


import com.project.dto.request.OrderItemRequest;
import com.project.dto.request.ProductRequest;
import com.project.model.OrderItem;
import com.project.model.Product;
import com.project.repository.ProductRepository;
import com.project.response.ResponseResult;
import com.project.service.OrderItemService;
import com.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderItem")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;


    @GetMapping("/getAllOrderItem")
    List<OrderItem> getAll(){
        return (List<OrderItem>) orderItemService.getAllOrderItem();
    }

    @GetMapping("/{id}")
        //Let's return an object with: data, message, status
    ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        return orderItemService.findById(id);
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseResult> insertOrderItem(@RequestBody OrderItemRequest newItem) {
        return orderItemService.insertOrderItem(newItem);

    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseResult> updateOrderItem(@RequestBody OrderItemRequest newItem, @PathVariable Long id) {
        return orderItemService.updateOrderItem(newItem,id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseResult> deleteOrderItem(@PathVariable Long id) {
        return orderItemService.deleteOrderItem(id);
    }
}

package com.project.controller;

import com.project.dto.request.OrderRequest;
import com.project.dto.request.ProductRequest;
import com.project.model.Order;
import com.project.model.Product;
import com.project.repository.ProductRepository;
import com.project.response.ResponseResult;
import com.project.service.OrderItemService;
import com.project.service.OrderService;
import com.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin("http://localhost:3000")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/getOrder")
    List<Order> getAll(){
        return (List<Order>) orderService.getAllOrder();
    }

    @GetMapping("/{id}")
        //Let's return an object with: data, message, status
    ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseResult> insertOrder(@RequestBody OrderRequest newOrder) {
        return orderService.insertOrder(newOrder);

    }

    @GetMapping("/{id}/orderItem")
    ResponseEntity<ResponseResult> findByOrderId(@PathVariable Long id) {
        return orderItemService.findOrderItemsByOrderId(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseResult> updateOrder(@RequestBody OrderRequest newOrder, @PathVariable Long id) {
        return orderService.updateOrder(newOrder,id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseResult> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }
}

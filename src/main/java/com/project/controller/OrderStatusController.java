package com.project.controller;

import com.project.model.Customer;
import com.project.model.OrderStatus;
import com.project.response.ResponseResult;
import com.project.service.CustomerService;
import com.project.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order_status")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class OrderStatusController {
    @Autowired
    private OrderStatusService orderStatusService;

    @GetMapping("/getAllStatus")
    List<OrderStatus> getAll(){
        return orderStatusService.getAllOrderStatus();
    }


    @GetMapping("/{id}")
        //Let's return an object with: data, message, status
    ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        return orderStatusService.findById(id);
    }

    @PostMapping("/insert")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<ResponseResult> insertStatus(@RequestBody OrderStatus newStatus) {
        return orderStatusService.insertOrderStatus(newStatus);

    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseResult> updateStatus(@RequestBody OrderStatus newStatus, @PathVariable Long id) {
        return orderStatusService.updateOrderStatus(newStatus,id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseResult> deleteStatus(@PathVariable Long id) {
        return orderStatusService.deleteOrderStatus(id);
    }
}

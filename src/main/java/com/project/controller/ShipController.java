package com.project.controller;

import com.project.model.Customer;
import com.project.model.Shipping;
import com.project.response.ResponseResult;
import com.project.service.CustomerService;
import com.project.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ship")
@RequiredArgsConstructor
public class ShipController {
    @Autowired
    private ShippingService shippingService;

    @GetMapping("/getAllShip")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    List<Shipping> getAll(){
        return shippingService.getAllShipping();
    }


    @GetMapping("/{id}")
        //Let's return an object with: data, message, status
    ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        return shippingService.findById(id);
    }

    @PostMapping("/insert")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<ResponseResult> insertShip(@RequestBody Shipping newShip) {
        return shippingService.insertShip(newShip);

    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseResult> updateShip(@RequestBody Shipping newShip, @PathVariable Long id) {
        return shippingService.updateShip(newShip,id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseResult> deleteShip(@PathVariable Long id) {
        return shippingService.deleteShip(id);
    }
}

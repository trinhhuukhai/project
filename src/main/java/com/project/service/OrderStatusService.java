package com.project.service;

import com.project.model.Customer;
import com.project.model.Order;
import com.project.model.OrderStatus;
import com.project.repository.OrderStatusRepository;
import com.project.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class OrderStatusService {
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    public List<OrderStatus> getAllOrderStatus(){
        return orderStatusRepository.findAll();
    }

    public ResponseEntity<ResponseResult> insertOrderStatus(OrderStatus newStatus) {
        //2 products must not have the same name !
        List<OrderStatus> foundStatus = orderStatusRepository.findByName(newStatus.getName().trim());
        if(foundStatus.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseResult("failed", "status name already taken", "",1)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Insert status successfully", orderStatusRepository.save(newStatus),1)
        );
    }


    public ResponseEntity<ResponseResult> updateOrderStatus(@RequestBody OrderStatus newStatus, @PathVariable Long id) {
        OrderStatus updatedStatus = orderStatusRepository.findById(id)
                .map(status -> {
                    status.setName(newStatus.getName());
                    return orderStatusRepository.save(status);
                }).orElseGet(() -> {
                    newStatus.setId(id);
                    return orderStatusRepository.save(newStatus);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Update Customer successfully", updatedStatus,1)
        );
    }

    //Delete a Product => DELETE method
    public ResponseEntity<ResponseResult> deleteOrderStatus(@PathVariable Long id) {
        boolean exists = orderStatusRepository.existsById(id);
        if(exists) {
            orderStatusRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseResult("ok", "Delete status successfully", "",1)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseResult("failed", "Cannot find status to delete", "",1)
        );
    }

    //Let's return an object with: data, message, status
    public ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        Optional<OrderStatus> foundStatus = orderStatusRepository.findById(id);
        return foundStatus.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseResult("ok", "Query status successfully", foundStatus,1)
                        //you can replace "ok" with your defined "error code"
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseResult("failed", "Cannot find status with id = "+id, "",1)
                );
    }
}

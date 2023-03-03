package com.project.service;

import com.project.dto.request.OrderItemRequest;
import com.project.dto.request.OrderRequest;
import com.project.dto.request.ProductRequest;
import com.project.model.*;
import com.project.repository.*;
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
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    public List<OrderItem> getAllOrderItem(){
        return orderItemRepository.findAll();
    }


    public ResponseEntity<ResponseResult> insertOrderItem(@RequestBody OrderItemRequest newOrderItem) {


        Order order = orderRepository.findById(newOrderItem.getOrderId()).orElseThrow();
        Product product = productRepository.findById(newOrderItem.getProductId()).orElseThrow();

        OrderItem orderItem = new OrderItem();

        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(newOrderItem.getQuantity());
        orderItem.setPrice(newOrderItem.getPrice());

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Insert Order successfully",orderItemRepository.save(orderItem),1)
        );
    }

    public ResponseEntity<ResponseResult> updateOrderItem(@RequestBody OrderItemRequest newOrderItem, @PathVariable Long id) {

        Order order = orderRepository.findById(newOrderItem.getOrderId()).orElseThrow();
        Product product = productRepository.findById(newOrderItem.getProductId()).orElseThrow();


        Optional<OrderItem> updatedOrderItem = orderItemRepository.findById(id)
                .map(orderItem -> {
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    orderItem.setQuantity(newOrderItem.getQuantity());
                    orderItem.setPrice(newOrderItem.getPrice());

                    return orderItemRepository.save(orderItem);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Update order successfully", updatedOrderItem,1)
        );
    }

    //Delete a Product => DELETE method
    public ResponseEntity<ResponseResult> deleteOrderItem(@PathVariable Long id) {
        boolean exists = orderItemRepository.existsById(id);
        if(exists) {
            orderItemRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseResult("ok", "Delete order item successfully", "",1)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseResult("failed", "Cannot find order item to delete", "",1)
        );
    }

    //Let's return an object with: data, message, status
    public ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        Optional<OrderItem> foundOrderItem = orderItemRepository.findById(id);
        return foundOrderItem.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseResult("ok", "Query order item successfully", foundOrderItem,1)
                        //you can replace "ok" with your defined "error code"
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseResult("failed", "Cannot find order item with id = "+id, "",1)
                );
    }

    public ResponseEntity<ResponseResult> findOrderItemsByOrderId(@PathVariable Long id) {
        List<OrderItem> foundOrderItem = orderItemRepository.findByOrderId(id);
        return !foundOrderItem.isEmpty() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseResult("ok", "Query order item successfully", foundOrderItem, foundOrderItem.size())
                        //you can replace "ok" with your defined "error code"
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseResult("failed", "Cannot find order item with id = "+id, "",foundOrderItem.size())
                );
    }
}

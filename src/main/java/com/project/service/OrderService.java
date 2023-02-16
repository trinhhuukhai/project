package com.project.service;

import com.project.dto.request.OrderRequest;
import com.project.dto.request.ProductRequest;
import com.project.model.*;
import com.project.repository.*;
import com.project.response.ResponseResult;
import com.project.service.iService.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }


    public ResponseEntity<ResponseResult> insertOrder(@RequestBody OrderRequest newOrder) {


        OrderStatus orderStatus = orderStatusRepository.findById(newOrder.getOrderStatusId()).orElseThrow();
        Customer customer = customerRepository.findById(newOrder.getCustomerId()).orElseThrow();
        Shipping shipping = shippingRepository.findById(newOrder.getShippingId()).orElseThrow();
        Payment payment = paymentRepository.findById(newOrder.getPaymentId()).orElseThrow();

        Order order = new Order();
        order.setOrderDate(newOrder.getOrderDate());
        order.setOrderStatus(orderStatus);
        order.setCustomer(customer);
        order.setShipping(shipping);
        order.setPayment(payment);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Insert Order successfully",orderRepository.save(order))
        );
    }

    public ResponseEntity<ResponseResult> updateOrder(@RequestBody OrderRequest newOrder, @PathVariable Long id) {

        OrderStatus orderStatus = orderStatusRepository.findById(newOrder.getOrderStatusId()).orElseThrow();
        Customer customer = customerRepository.findById(newOrder.getCustomerId()).orElseThrow();
        Shipping shipping = shippingRepository.findById(newOrder.getShippingId()).orElseThrow();
        Payment payment = paymentRepository.findById(newOrder.getPaymentId()).orElseThrow();


        Optional<Order> updatedOrder = orderRepository.findById(id)
                .map(order -> {
                    order.setOrderDate(newOrder.getOrderDate());
                    order.setOrderStatus(orderStatus);
                    order.setCustomer(customer);
                    order.setShipping(shipping);
                    order.setPayment(payment);
                    return orderRepository.save(order);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Update order successfully", updatedOrder)
        );
    }

    //Delete a Product => DELETE method
    public ResponseEntity<ResponseResult> deleteOrder(@PathVariable Long id) {
        boolean exists = orderRepository.existsById(id);
        if(exists) {
            orderRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseResult("ok", "Delete order successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseResult("failed", "Cannot find order to delete", "")
        );
    }

    //Let's return an object with: data, message, status
    public ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        Optional<Order> foundOrder = orderRepository.findById(id);
        return foundOrder.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseResult("ok", "Query order successfully", foundOrder)
                        //you can replace "ok" with your defined "error code"
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseResult("failed", "Cannot find order with id = "+id, "")
                );
    }
}

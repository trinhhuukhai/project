package com.project.service;

import com.project.dto.request.CartItemRequest;
import com.project.dto.request.CartRequest;
import com.project.model.Cart;
import com.project.model.CartItem;
import com.project.model.Customer;
import com.project.model.Product;
import com.project.repository.CartItemRepository;
import com.project.repository.CartRepository;
import com.project.repository.CustomerRepository;
import com.project.repository.ProductRepository;
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
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    public List<CartItem> getAllCart(){
        return cartItemRepository.findAll();
    }


    public ResponseEntity<ResponseResult> insertCartItem(@RequestBody CartItemRequest newCartItem) {

        Product product = productRepository.findById(newCartItem.getProductId()).orElseThrow();
        Cart cart = cartRepository.findById(newCartItem.getCartId()).orElseThrow();

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(newCartItem.getQuantity());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Insert Product successfully",cartItemRepository.save(cartItem),1)
        );
    }

    public ResponseEntity<ResponseResult> updateCartItem(@RequestBody CartItemRequest newCartItem, @PathVariable Long id) {

        Product product = productRepository.findById(newCartItem.getProductId()).orElseThrow();
        Cart cart = cartRepository.findById(newCartItem.getCartId()).orElseThrow();


        Optional<CartItem> updatedCartItem = cartItemRepository.findById(id)
                .map(cartItem -> {
                    cartItem.setCart(cart);
                    cartItem.setProduct(product);
                    cartItem.setQuantity(newCartItem.getQuantity());
                    return cartItemRepository.save(cartItem);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Update Customer successfully", updatedCartItem,1)
        );
    }

    //Delete a Product => DELETE method
    public ResponseEntity<ResponseResult> deleteCartItem(@PathVariable Long id) {
        boolean exists = cartItemRepository.existsById(id);
        if(exists) {
            cartItemRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseResult("ok", "Delete cartItem successfully", "",1)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseResult("failed", "Cannot find Customer to delete", "",1)
        );
    }

    //Let's return an object with: data, message, status
    public ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        Optional<CartItem> foundCartItem = cartItemRepository.findById(id);
        return foundCartItem.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseResult("ok", "Query Customer successfully", foundCartItem,1)
                        //you can replace "ok" with your defined "error code"
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseResult("failed", "Cannot find Customer with id = "+id, "",1)
                );
    }

    public ResponseEntity<ResponseResult> findItemByCartId(@PathVariable Long id) {
        List<CartItem> foundCartItem = cartItemRepository.findByCartId(id);
        return !foundCartItem.isEmpty() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseResult("ok", "Query product item successfully", foundCartItem, foundCartItem.size())
                        //you can replace "ok" with your defined "error code"
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseResult("failed", "Cannot find product item with id = "+id, "",foundCartItem.size())
                );
    }
}

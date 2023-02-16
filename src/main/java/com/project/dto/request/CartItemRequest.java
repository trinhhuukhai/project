package com.project.dto.request;


import com.project.model.Cart;
import com.project.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {

    private Long id;

    private Long cartId;

    private Long productId;

    private Integer quantity;
}

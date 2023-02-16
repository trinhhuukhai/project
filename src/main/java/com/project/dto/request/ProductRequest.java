package com.project.dto.request;

import com.project.model.Category;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String brand;

    private String color;

    private int inventory;

    private Long categoryId;

    private String productImage;

}

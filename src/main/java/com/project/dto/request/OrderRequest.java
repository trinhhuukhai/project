package com.project.dto.request;

import com.project.model.Customer;
import com.project.model.OrderStatus;
import com.project.model.Payment;
import com.project.model.Shipping;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Long id;

    private Date orderDate;

    private Long orderStatusId;

    private Long paymentId;

    private Long shippingId;

    private Long customerId;
}

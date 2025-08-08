package com.ecommerce.order.dto;

import com.ecommerce.order.models.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus status = OrderStatus.PENDING;
    private List<OrderItemDTO> items = new ArrayList<>();
    private LocalDateTime createdAt;

}

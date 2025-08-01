package com.blxxd.app.Pet_project.dto;

import com.blxxd.app.Pet_project.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class OrderItemDTO {

    private Long id;

    private Long productId;

    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;

}

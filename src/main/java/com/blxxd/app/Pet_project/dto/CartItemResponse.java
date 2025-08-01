package com.blxxd.app.Pet_project.dto;

import com.blxxd.app.Pet_project.model.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {
    private Product product;
    private Integer quantity;
    private BigDecimal price;

}

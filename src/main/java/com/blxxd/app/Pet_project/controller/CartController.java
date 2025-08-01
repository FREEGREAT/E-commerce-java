package com.blxxd.app.Pet_project.controller;


import com.blxxd.app.Pet_project.dto.CartItemRequest;
import com.blxxd.app.Pet_project.dto.CartItemResponse;
import com.blxxd.app.Pet_project.model.CartItem;
import com.blxxd.app.Pet_project.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request)
    {
        if(!cartService.addToCart(userId,request)){
            return ResponseEntity.badRequest().body("Product out of Stock or User not found or Product not found");
        };
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId
    ){
        boolean isDeleted =  cartService.deleteItemFromCart(productId, userId);
        return isDeleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            @RequestHeader("X-User-ID") String userId
    ){

        return new ResponseEntity<>(cartService.getCart(userId), HttpStatus.OK);
    };
}

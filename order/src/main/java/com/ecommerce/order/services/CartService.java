package com.ecommerce.order.services;


import com.ecommerce.order.dto.CartItemResponse;
import com.ecommerce.order.repository.CartItemRepository;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.models.CartItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItemRequest request){
//        Optional<Product> productOptional = productRepository.findById(request.getProductId());
//        if(productOptional.isEmpty())
//            return false;
//
//        Product product = productOptional.get();
//        if(product.getStockQuantity() < request.getQuantity())
//            return false;
//
//        Optional<User> userOptional = userRepository.findById( Long.valueOf(userId));
//        if(userOptional.isEmpty()){
//            return false;
//        }
//        User user = userOptional.get();

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setProductId(request.getProductId());
            cartItem.setUserId(userId);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
        }
       return true;
    }

    public boolean deleteItemFromCart(String productId, String userId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if(cartItem != null){
            cartItemRepository.delete(cartItem);
            return true;
        }

        return false;
    }

    public List<CartItem> getCart(String userId){
        return cartItemRepository.getCartItemsByUserId(userId);
    }

    public void clearCart(String userId){
        cartItemRepository.deleteByUserId(userId);
    }

    private CartItemResponse mapEntityToResponse(CartItem cartItem){
        CartItemResponse response = new CartItemResponse();

        response.setPrice(cartItem.getPrice());
        response.setProductId(cartItem.getProductId());
        response.setQuantity(cartItem.getQuantity());

        return  response;
    }
}

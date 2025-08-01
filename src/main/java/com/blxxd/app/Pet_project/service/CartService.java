package com.blxxd.app.Pet_project.service;

import com.blxxd.app.Pet_project.dto.CartItemRequest;
import com.blxxd.app.Pet_project.dto.CartItemResponse;
import com.blxxd.app.Pet_project.model.CartItem;
import com.blxxd.app.Pet_project.model.Product;
import com.blxxd.app.Pet_project.model.User;
import com.blxxd.app.Pet_project.repository.CartItemRepository;
import com.blxxd.app.Pet_project.repository.ProductRepository;
import com.blxxd.app.Pet_project.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public boolean addToCart(String userId, CartItemRequest request){
        Optional<Product> productOptional = productRepository.findById(request.getProductId());
        if(productOptional.isEmpty())
            return false;

        Product product = productOptional.get();
        if(product.getStockQuantity() < request.getQuantity())
            return false;

        Optional<User> userOptional = userRepository.findById( Long.valueOf(userId));
        if(userOptional.isEmpty()){
            return false;
        }
        User user = userOptional.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }else{
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setUser(user);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(Long productId, String userId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<User> userOptional = userRepository.findById( Long.valueOf(userId));

        if(productOptional.isPresent() && userOptional.isPresent()){
            cartItemRepository.deleteByUserAndProduct(userOptional.get(), productOptional.get());
            return true;
        }

        return false;
    }

    public List<CartItem> getCart(String userId){
        return userRepository.findById(Long.valueOf(userId))
                .map(cartItemRepository::getCartItemsByUser)
                .orElseGet(List::of);
    }

    public void clearCart(String userId){
        userRepository.findById(Long.valueOf(userId)).ifPresent(
                cartItemRepository::deleteByUser
                );
    }

    private CartItemResponse mapEntityToResponse(CartItem cartItem){
        CartItemResponse response = new CartItemResponse();

        response.setPrice(cartItem.getPrice());
        response.setProduct(cartItem.getProduct());
        response.setQuantity(cartItem.getQuantity());

        return  response;
    }
}

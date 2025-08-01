package com.blxxd.app.Pet_project.repository;

import com.blxxd.app.Pet_project.model.CartItem;
import com.blxxd.app.Pet_project.model.Product;
import com.blxxd.app.Pet_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> getCartItemsByUser(User user);

    void deleteByUser(User user);
}

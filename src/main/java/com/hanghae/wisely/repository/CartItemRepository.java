package com.hanghae.wisely.repository;

import com.hanghae.wisely.domain.Cart;
import com.hanghae.wisely.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByCart(Cart cart);
    CartItem findByItem_IdAndCart(Long id,Cart cart);
}

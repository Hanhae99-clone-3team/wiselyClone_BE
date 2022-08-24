package com.hanghae.wisely.repository;

import com.hanghae.wisely.domain.Cart;
import com.hanghae.wisely.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // 멤버 추가 필요
    List<CartItem> findAllByCart(Cart cart);
    CartItem findByItem_Id(Long id);
}

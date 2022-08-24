package com.hanghae.wisely.controller;

import com.hanghae.wisely.dto.response.BasicResponseDto;
import com.hanghae.wisely.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    // 장바구니 담기(제품보기 Detail page)
    @PostMapping("/items/detail/order/{id}")
    public BasicResponseDto createCart(@PathVariable Long id, HttpServletRequest request) {
        return cartService.addCart(id, request);
    }

    // 장바구니 전체 조회
    @GetMapping("/items/cart")
    public ResponseEntity<?> getCart() {
        return cartService.getCart();
    }

    // 장바구니 상품 삭제(X 버튼)
    @DeleteMapping("/items/cart/{carItemid}")
    public BasicResponseDto deleteCart(@PathVariable Long carItemid, HttpServletRequest request) {
        return cartService.deleteCart(carItemid, request);
    }

    // 장바구니 결제(결제하기 버튼)
    @PutMapping("/items/cart/{cartId}")
    public BasicResponseDto paidCart(@PathVariable Long cartId, HttpServletRequest request) {
        return cartService.paidCart(cartId, request);
    }

    // 장바구니 상품 수정(더 담기 버튼)
    @PatchMapping("/items/cart/{cartId}")
    public BasicResponseDto updateCart(@PathVariable Long cartId, HttpServletRequest request) {
        return cartService.updateCart(cartId, request);
    }
}

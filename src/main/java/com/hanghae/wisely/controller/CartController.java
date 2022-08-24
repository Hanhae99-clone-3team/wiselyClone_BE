package com.hanghae.wisely.controller;

import com.hanghae.wisely.domain.AuthMember;
import com.hanghae.wisely.domain.Member;
import com.hanghae.wisely.dto.response.BasicResponseDto;
import com.hanghae.wisely.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    // 장바구니 담기(제품보기 Detail page)
    @PostMapping("/items/detail/order/{id}")
    public BasicResponseDto createCart(@PathVariable Long id, @AuthenticationPrincipal AuthMember authMember) {
        Member member = authMember.getMember();
        return cartService.addCart(id, member);
    }

    // 장바구니 전체 조회
    @GetMapping("/items/cart")
    public ResponseEntity<?> getCart(@AuthenticationPrincipal AuthMember authMember) {
        Member member = authMember.getMember();
        return cartService.getCart(member);
    }

    // 장바구니 상품 삭제(X 버튼)
    @DeleteMapping("/items/cart/{carItemid}")
    public BasicResponseDto deleteCart(@PathVariable Long carItemid, @AuthenticationPrincipal AuthMember authMember) {
        Member member = authMember.getMember();
        return cartService.deleteCart(carItemid, member);
    }

    // 장바구니 결제(결제하기 버튼)
    @PutMapping("/items/cart/{cartId}")
    public BasicResponseDto paidCart(@PathVariable Long cartId, @AuthenticationPrincipal AuthMember authMember) {
        Member member = authMember.getMember();
        return cartService.paidCart(cartId, member);
    }

    // 장바구니 상품 수정(더 담기 버튼)
    @PatchMapping("/items/cart/{cartId}")
    public BasicResponseDto updateCart(@PathVariable Long cartId, @AuthenticationPrincipal AuthMember authMember) {
        Member member = authMember.getMember();
        return cartService.updateCart(cartId, member);
    }
}

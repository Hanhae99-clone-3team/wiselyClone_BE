package com.hanghae.wisely.service;

import com.hanghae.wisely.domain.Cart;
import com.hanghae.wisely.domain.CartItem;
import com.hanghae.wisely.domain.Member;
import com.hanghae.wisely.dto.response.BasicResponseDto;
import com.hanghae.wisely.dto.response.CartItemResponseDto;
import com.hanghae.wisely.dto.response.CartResponseDto;
import com.hanghae.wisely.repository.CartItemRepository;
import com.hanghae.wisely.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemService cartItemService;

    // 장바구니 담기
    @Transactional
    public BasicResponseDto addCart(Long itemId, Member member) {
        // 카트에 결재대기 항목있는지 체크
        Cart cart = cartRepository.findByIsPaidFalse(member);
        if (null == cart){
            return createCart(itemId, member);
        }
        // 해당 Cart에 item 추가
        return cartItemService.addCartItem(itemId,cart);
    }

    // 장바구니 생성
    @Transactional
    public BasicResponseDto createCart(Long itemId, Member member) {
        Cart cart = Cart.builder()
                .idPaid(false)
                .member(member)
                .build();
        cartRepository.save(cart);

        // CartItem 생성
        return cartItemService.createCartItem(itemId, cart);
    }

    // 장바구니 전체 조회
    @Transactional
    public ResponseEntity<?> getCart(Member member) {
        // 카트에 결재되지 않은 항목이 있는지 체크
        Cart cart = cartRepository.findByIsPaidFalse(member);
        if (null == cart){
            return new ResponseEntity(new BasicResponseDto("장바구니가 비어있습니다",false), HttpStatus.NOT_FOUND);
        }

        // 장바구니 안에 있는 item들 조회
        List<CartItem> cartItemList = cartItemRepository.findAllByCart(cart);
        List<CartItemResponseDto> cartItems = new ArrayList<>();

        for (CartItem cartItem : cartItemList) {
            cartItems.add(
                    CartItemResponseDto.builder()
                            .cartItemId(cartItem.getId())
                            .itemCount(cartItem.getItemCount())
                            .itemName(cartItem.getItem().getItemName())
                            .itemDesc(cartItem.getItem().getItemDesc())
                            .itemPrice(cartItem.getItem().getItemPrice())
                            .build()
            );
        }

        return ResponseEntity.ok(CartResponseDto.builder()
                .cartId(cart.getId())
                .cartItems(cartItems)
                .build());
    }

    // 장바구니 상품 삭제 (구현 중)
    @Transactional
    public BasicResponseDto deleteCart(Long carItemid, Member member) {
        // 멤버 체크 필요

        // 카트에 결재대기 항목있는지 체크
        Cart cart = cartRepository.findByIsPaidFalse(member);
        if (null == cart){
            return new BasicResponseDto("장바구니가 비어있습니다",false);
        }
        // CartItem에 해당 id가 있는지 확인
        // 있으면 삭제
        // 없으면 오류 메세지

        // CartItem이 하나일 때 Cart자체를 지움
        cartRepository.delete(cart);
        return new BasicResponseDto("장바구니를 삭제하였습니다.",true);
    }

    // 장바구니 결제
    @Transactional
    public BasicResponseDto paidCart(Long id, Member member) {
        // 멤버 체크 필요

        // 카트에 결재대기 항목있는지 체크
        Optional<Cart> optionalCart = cartRepository.findById(id);
        Cart cart = optionalCart.orElse(null);
        if (null == cart){
            return new BasicResponseDto("Cart가 비어있습니다",false);
        }

        cart.update();
        return new BasicResponseDto("결제가 완료되었습니다.",true);
    }

    // 장바구니 상품 수정(미구현)
    @Transactional
    public BasicResponseDto updateCart(Long id, Member member) {
        return new BasicResponseDto("장바구니가 업데이트되었습니다.",true);
    }
}

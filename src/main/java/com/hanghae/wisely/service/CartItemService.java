package com.hanghae.wisely.service;

import com.hanghae.wisely.domain.Cart;
import com.hanghae.wisely.domain.CartItem;
import com.hanghae.wisely.domain.Item;
import com.hanghae.wisely.dto.response.BasicResponseDto;
import com.hanghae.wisely.repository.CartItemRepository;
import com.hanghae.wisely.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    //
    @Transactional
    public BasicResponseDto createCartItem(Long itemId, Cart cart) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (null == item) {
            return new BasicResponseDto("해당하는 item이 존재하지 않습니다.", false);
        } else {
            CartItem cartItem = CartItem.builder()
                    .itemCount(Integer.toUnsignedLong(1))
                    .item(item.get())
                    .cart(cart)
                    .build();
            cartItemRepository.save(cartItem);
            return new BasicResponseDto("장바구니에 아이템을 추가하였습니다.", true);
        }
    }

    @Transactional
    public BasicResponseDto addCartItem(Long itemId, Cart cart) {
        CartItem cartItem = cartItemRepository.findByItem_Id(itemId);
        if (null == cartItem) {
            return createCartItem(itemId, cart);
        } else {
            cartItem.update(Integer.toUnsignedLong(1));
            return new BasicResponseDto("장바구니에 아이템 수량을 증가하였습니다.",true);
        }
    }

    @Transactional
    public void updateCartItem(Long itemId, Long itemCount) {

    }
}

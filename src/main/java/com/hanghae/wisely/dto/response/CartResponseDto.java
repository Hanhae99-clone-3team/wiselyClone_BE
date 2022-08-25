package com.hanghae.wisely.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private Long cartId;
    private List<CartItemResponseDto> cartItems;

}

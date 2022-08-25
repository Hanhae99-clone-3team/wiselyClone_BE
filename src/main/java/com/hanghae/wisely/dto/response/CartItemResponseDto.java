package com.hanghae.wisely.dto.response;

import lombok.*;
import org.springframework.stereotype.Service;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDto {
    private Long cartItemId;
    private Long itemCount;
    private String itemName;
    private String itemDesc;
    private String itemImgUrl;
    private Long itemPrice;
}

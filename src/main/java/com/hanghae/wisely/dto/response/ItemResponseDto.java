package com.hanghae.wisely.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ItemResponseDto {
    private String itemName;
    private String itemDesc;
    private String itemImgUrl;
    private Long itemPrice;
    // Review 구현되면 가능
    private Long itemRate;
    private Long itemReviewCount;

}

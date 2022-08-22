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
    private Long itemId;
    private String itemName;
    private String itemDesc;
    private String itemImgUrl;
    private Long itemPrice;
    private double itemRate;
    private Long itemReviewCount;

}

package com.hanghae.wisely.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ItemDetailResponseDto {
    private Long itemId;
    private String itemName;
    private String itemDesc;
    private Long itemPrice;
    private String itemImgUrl;
    private String itemDetailImg1;
    private String itemDetailImg2;
    // Review 구현되면 가능
    private double itemAvgRate;
    private Long itemReviewCount;
}

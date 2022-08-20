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
    private String itemName;
    private String itemDesc;
    private String itemImgUrl;
    private Long itemPrice;
    private String itemDetailImg;
    // Review 구현되면 가능
    private Long itemAvgRate;
    private Long itemReviewCount;
}

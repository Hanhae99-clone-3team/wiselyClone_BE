package com.hanghae.wisely.dto.request;

import com.hanghae.wisely.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequestDto {
    private String itemName;
    private String itemDesc;
    private Long itemPrice;
    private String itemImgUrl;
    private String itemDetailImg1;
    private String itemDetailImg2;
    private Category category;
}

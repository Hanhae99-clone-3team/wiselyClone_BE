package com.hanghae.wisely.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReviewListResponseDto {
    private Long itemId;
    private List<ReviewGetResponseDto> comments;
}

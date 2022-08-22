package com.hanghae.wisely.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewUpdateResponseDto {
    private Long commentId;
    private String comment;
    private String username;
    private String createdAt;
    private Long rate;
    private String age;
}

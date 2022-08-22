package com.hanghae.wisely.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewGetResponseDto {
    private Long commentId;
    private String comment;
    private String name;
    private String createdAt;
    private Long age;
    private boolean ismine;

}

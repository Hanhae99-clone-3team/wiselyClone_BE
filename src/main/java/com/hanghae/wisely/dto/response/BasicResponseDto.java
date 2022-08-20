package com.hanghae.wisely.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BasicResponseDto {
    private String msg;
    private boolean success;
}

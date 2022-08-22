package com.hanghae.wisely.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpRequestDto {

    private String email;

    private String name;

    private Long birthday;

    private String password;

}

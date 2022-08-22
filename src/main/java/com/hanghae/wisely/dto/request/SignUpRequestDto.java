package com.hanghae.wisely.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    private String email;

    private String name;

    private Long birthday;

    private String password;

}

package com.hanghae.wisely.controller;

import com.hanghae.wisely.dto.request.EmailCheckRequestDto;
import com.hanghae.wisely.dto.request.LoginRequestDto;
import com.hanghae.wisely.dto.request.SignUpRequestDto;
import com.hanghae.wisely.dto.response.BasicResponseDto;
import com.hanghae.wisely.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members/signup") //회원가입
    public BasicResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        memberService.signUp(signUpRequestDto.getEmail(), signUpRequestDto.getName(), signUpRequestDto.getBirthday(), signUpRequestDto.getPassword());
        return new BasicResponseDto("회원가입 성공",true);
    }
    @GetMapping("/members/email-check")// 이메일 가입여부 검사
    public BasicResponseDto emailcheck(@RequestBody EmailCheckRequestDto emailCheckRequestDto) {

        memberService.checkEmailIsDuplicate(emailCheckRequestDto.getEmail());

        return new BasicResponseDto("가입이 가능한 회원입니다.",true);
    }

    @PostMapping("/members/login")
    public BasicResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
         memberService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword(),response);


        return new BasicResponseDto("로그인 성공",true);
    }

}

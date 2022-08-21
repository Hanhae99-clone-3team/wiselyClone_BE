package com.hanghae.wisely.service;

import com.hanghae.wisely.domain.Member;
import com.hanghae.wisely.exception.BadRequestException;
import com.hanghae.wisely.jwt.JwtProvider;
import com.hanghae.wisely.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    @Transactional
    public  void signUp(String email, String name, Long birthday, String password) {
        checkEmailIsDuplicate(email);
        String encodedPassword = passwordEncoder.encode(password);
        Member newMember = Member.of(email,name,birthday,encodedPassword);
        memberRepository.save(newMember);
    }


    public void checkEmailIsDuplicate(String email) {
        boolean isDuplicate = memberRepository.existsByEmail(email);
       if(isDuplicate) {
            throw new BadRequestException("이미 존재하는 회원입니다.");
        }
    }

    public void login(String email, String password, HttpServletResponse response) {
        Member member = memberRepository
                .findByEmail(email).orElseThrow(() -> new BadRequestException("아이디 혹은 비밀번호를 확인하세요."));
        checkPassword(password, member.getPassword());


       String token = jwtProvider.createToken(member.getEmail(), member.getRole());
       tokenToHeaders(token,response);

    }
    private void checkPassword(String password, String encodedPassword) {
        boolean isSame = passwordEncoder.matches(password, encodedPassword);
        if(!isSame) {
            throw new BadRequestException("아이디 혹은 비밀번호를 확인하세요.");
        }
    }

    public void tokenToHeaders(String token, HttpServletResponse response) {
        response.addHeader("AccessToken", "Bearer " + token);
    }
}

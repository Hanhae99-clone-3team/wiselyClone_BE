package com.hanghae.wisely.service;

import com.hanghae.wisely.domain.Member;
import com.hanghae.wisely.exception.BadRequestException;
import com.hanghae.wisely.jwt.JwtProvider;
import com.hanghae.wisely.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    @Transactional
    public void signUp(String email, String name, Long birthday, String password) {
        checkEmailIsDuplicate(email);
        String encodedPassword = passwordEncoder.encode(password);
        Member newMember = Member.of(email, name, birthday, encodedPassword);
        memberRepository.save(newMember);
    }


    public void checkEmailIsDuplicate(String email) {
        boolean isDuplicate = memberRepository.existsByEmail(email);
        if (isDuplicate) {
            throw new BadRequestException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public void login(String email, String password, HttpServletResponse response) {
        Member member = memberRepository
                .findByEmail(email).orElseThrow(() -> new BadRequestException("아이디 혹은 비밀번호를 확인하세요."));
        checkPassword(password, member.getPassword());


        String accessToken = jwtProvider.createAccessToken(member.getEmail(), member.getRole());
        String refreshToken = jwtProvider.createRefreshToken(member.getEmail(), member.getRole());
        tokenToHeaders(accessToken, refreshToken, response);

    }

    private void checkPassword(String password, String encodedPassword) {
        boolean isSame = passwordEncoder.matches(password, encodedPassword);
        if (!isSame) {
            throw new BadRequestException("아이디 혹은 비밀번호를 확인하세요.");
        }
    }

    @Transactional
    public void reIssueAccessToken(Member member, HttpServletRequest request, HttpServletResponse response) {


        memberRepository.findByEmail(member.getEmail()).orElseThrow(() -> new BadRequestException("존재하지 않는 유저입니다."));


        jwtProvider.checkRefreshToken(member.getEmail(), request.getHeader("RefreshToken"));

        String accessToken = jwtProvider.createAccessToken(member.getEmail(), member.getRole());

        tokenToHeaders(accessToken, request.getHeader("RefreshToken"), response);
    }

    public void tokenToHeaders(String accessToken, String refreshToken, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("RefreshToken", refreshToken);
    }
}

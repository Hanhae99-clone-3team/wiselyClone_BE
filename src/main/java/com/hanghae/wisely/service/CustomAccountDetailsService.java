package com.hanghae.wisely.service;

import com.hanghae.wisely.domain.AuthMember;
import com.hanghae.wisely.domain.Member;
import com.hanghae.wisely.exception.BadRequestException;
import com.hanghae.wisely.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAccountDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("토큰을 확인하세요."));
        return new AuthMember(member);
    }
}

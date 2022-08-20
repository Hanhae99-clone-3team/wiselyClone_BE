package com.hanghae.wisely.service;

import com.hanghae.wisely.domain.Member;
import com.hanghae.wisely.exception.BadRequestException;
import com.hanghae.wisely.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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

}

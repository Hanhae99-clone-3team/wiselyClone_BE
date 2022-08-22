package com.hanghae.wisely.domain;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Objects;

@Getter
public class AuthMember extends User {

   private final Member member;

    public AuthMember(Member member) {
        super(member.getEmail(), member.getPassword(), List.of(new SimpleGrantedAuthority(member.getRole())));
        this.member = member;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuthMember that = (AuthMember) o;
        return Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), member);
    }
}




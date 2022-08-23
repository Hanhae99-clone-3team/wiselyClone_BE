package com.hanghae.wisely.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birthday;
    @Column(nullable = false)
    private String password;

    private String role;

    private Member(String email, String name,String birthday, String password) {
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.password = password;
        role = "USER";
    }

    public static Member of(String email, String name, String birthday,String password) {
        return new Member(email, name, birthday,password);
    }
}

package com.hanghae.wisely.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String itemName;

    @Column(nullable = false, unique = true)
    private String itemDesc;

    @Column(nullable = false)
    private Long itemPrice;

    @Column(nullable = false)
    private String itemImgUrl;


    // Category Enum (면도용품, 스킨케어, 두피케어, 영양제, 덴탈케어, 바디케어)
    @Enumerated(EnumType.STRING)
    private Category category;

    // Colum 에 imgUrl List를 어떻게..?
    private String itemDetailImag;
}

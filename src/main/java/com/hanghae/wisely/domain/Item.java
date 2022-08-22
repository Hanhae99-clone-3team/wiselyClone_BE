package com.hanghae.wisely.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String itemName;

    @Column(nullable = false, unique = true)
    private String itemDesc;

    @Column(nullable = false)
    private Long itemPrice;

    @Column(nullable = false)
    private String itemImgUrl;

    @Column(nullable = false)
    private String itemDetailImag1;

    @Column(nullable = false)
    private String itemDetailImag2;

    // Category Enum (면도용품, 스킨케어, 두피케어, 영양제, 덴탈케어, 바디케어)
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList;

    


}

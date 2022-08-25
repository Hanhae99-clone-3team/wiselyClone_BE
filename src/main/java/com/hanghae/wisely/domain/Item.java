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

    @Column(name = "itemname", nullable = false, unique = true)
    private String itemName;

    @Column(name = "itemdesc",nullable = false)
    private String itemDesc;

    @Column(name = "itemprice",nullable = false)
    private Long itemPrice;

    @Column(name = "itemimgurl",nullable = false, unique = true)
    private String itemImgUrl;

    @Column(name = "itemdetailimg1",nullable = false)
    private String itemDetailImg1;

    @Column(name = "itemdetailimg2",nullable = false)
    private String itemDetailImg2;

    // Category Enum (면도용품, 스킨케어, 두피케어, 영양제, 덴탈케어, 바디케어)
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList;

    


}

package com.hanghae.wisely.domain;

import com.hanghae.wisely.dto.request.ReviewRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Review extends Timestamped{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Long rate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void update(ReviewRequestDto requestDto){
        this.comment = requestDto.getComment();
        this.rate = requestDto.getStar();
    }
}

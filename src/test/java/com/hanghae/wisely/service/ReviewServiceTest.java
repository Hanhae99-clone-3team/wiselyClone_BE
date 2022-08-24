package com.hanghae.wisely.service;

import com.hanghae.wisely.domain.Item;
import com.hanghae.wisely.domain.Member;
import com.hanghae.wisely.dto.request.ReviewRequestDto;
import com.hanghae.wisely.dto.response.ReviewResponseDto;
import com.hanghae.wisely.repository.ItemRepository;
import com.hanghae.wisely.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock
    ItemRepository itemRepository;

    @Mock
    ReviewRepository reviewRepository;

    @InjectMocks
    ReviewService reviewService;

    @Test
    @DisplayName("정상 케이스")
    void createReview_Normal() {
        // given
        Long itemId = 1L;

        String comment = "너무 좋아요";
        Long star = 5L;

        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(comment,star);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(new Item()));

        //when
        ReviewResponseDto reviewResponseDto = reviewService.createReview(itemId, reviewRequestDto, new Member());

        //then
        assertNull(reviewResponseDto.getCommentId());
        assertNull(reviewResponseDto.getName());
        assertNull(reviewResponseDto.getCreatedAt());
        assertThat(reviewResponseDto.getComment()).isEqualTo(comment);
        assertThat(reviewResponseDto.getRate()).isEqualTo(star);
    }

    @Test
    @DisplayName("Star 범위 실패 케이스")
    void createReview_Fail1() {

        // given
        Long itemId = 1L;

        String comment = "너무 좋아요";
        Long star = 6L;


        ReviewRequestDto requestDto = new ReviewRequestDto(comment, star);

        reviewService = new ReviewService(reviewRepository,itemRepository);
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(new Item()));

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.createReview(itemId, requestDto, new Member());
        });

        // then
        assertEquals("star의 값은 1 이상 5 이하여야 합니다. star : " + star, exception.getMessage());
    }
}
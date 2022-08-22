package com.hanghae.wisely.service;

import com.hanghae.wisely.domain.Item;
import com.hanghae.wisely.domain.Review;
import com.hanghae.wisely.dto.request.ReviewRequestDto;
import com.hanghae.wisely.dto.response.ReviewGetResponseDto;
import com.hanghae.wisely.dto.response.ReviewListResponseDto;
import com.hanghae.wisely.dto.response.ReviewResponseDto;
import com.hanghae.wisely.dto.response.ReviewUpdateResponseDto;
import com.hanghae.wisely.repository.ItemRepository;
import com.hanghae.wisely.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;

    private final ItemService itemService;

    // Review 생성
    @Transactional //TODO : member 추가
    public ReviewResponseDto createReview(Long itemId, ReviewRequestDto requestDto) {

        // Item 찾아오기
        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 아이템이 존재하지 않습니다. itemId: " + itemId);
        });

        // rate 가 1이하 5 이상 이면 에러
        if (!(requestDto.getStar().intValue() >= 1 & requestDto.getStar().intValue() <= 5)) {
            throw new IllegalArgumentException("star의 값은 1 이상 5 이하여야 합니다. star : " + requestDto.getStar());
        }

        Review review = Review.builder()
                .comment(requestDto.getComment())
                .rate(requestDto.getStar())
                .item(item)
                .build();
        reviewRepository.save(review);

        return ReviewResponseDto.builder()
                .commentId(review.getId())
                .comment(review.getComment())
                .rate(review.getRate())
                .createdAt(review.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public ReviewListResponseDto getReview(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalArgumentException("Item이 존재하지 않습니다.")
        );

        List<Review> reviewList =  reviewRepository.findAllByItem_Id(item.getId());

        List<ReviewGetResponseDto> reviewGetResponseDtoList = new ArrayList<>();

        for (Review review : reviewList) {
            reviewGetResponseDtoList.add(
                    ReviewGetResponseDto.builder()
                            .commentId(review.getId())
                            .comment(review.getComment())
                            .createdAt(review.getCreatedAt())
                            .build()
            );
        }

        ReviewListResponseDto reviewListResponseDto = ReviewListResponseDto.builder()
                .itemId(itemId)
                .comments(reviewGetResponseDtoList)
                .build();

        return reviewListResponseDto;
    }


    // Review 수정
    @Transactional
    public ReviewUpdateResponseDto updateReview(Long itemId, Long commentId, ReviewRequestDto requestDto) {

        // review 에 해당 id 들이 있는지 확인하고 없으면 에러처리
       Review review = isPresentReview(itemId, commentId);

        // rate 가 1이하 5 이상 이면 에러
        if (!(requestDto.getStar().intValue() >= 1 & requestDto.getStar().intValue() <= 5)) {
            throw new IllegalArgumentException("star의 값은 1 이상 5 이하여야 합니다. star : " + requestDto.getStar());
        }

       // review 수정
        review.update(requestDto);

        ReviewUpdateResponseDto reviewUpdateResponseDto = ReviewUpdateResponseDto.builder()
                .commentId(commentId)
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .rate(review.getRate())
                .build();

        return reviewUpdateResponseDto;
    }

    // Review 삭제
    @Transactional
    public void deleteReview(Long itemId, Long commentId) {

        // 존재하는지 확인
        Review review = isPresentReview(itemId, commentId);

        // Reivew 삭제
        reviewRepository.delete(review);
    }

    // Review 검증
    @Transactional(readOnly = true)
    public Review isPresentReview(Long itemId, Long commentid) {
        Optional<Review> review = Optional.ofNullable(reviewRepository.findReviewByItem_IdAndId(itemId, commentid));
        return review.orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다. Item ID : "+ itemId + " Comment ID : " + commentid));
    }


}

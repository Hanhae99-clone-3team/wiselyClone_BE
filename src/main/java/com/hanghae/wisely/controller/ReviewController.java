package com.hanghae.wisely.controller;

import com.hanghae.wisely.dto.request.ReviewRequestDto;
import com.hanghae.wisely.dto.response.ReviewListResponseDto;
import com.hanghae.wisely.dto.response.ReviewResponseDto;
import com.hanghae.wisely.dto.response.ReviewUpdateResponseDto;
import com.hanghae.wisely.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // Review 생성
    @PostMapping("/items/detail/comments/{itemId}")
    public ResponseEntity<?> createReview(@PathVariable Long itemId, @RequestBody ReviewRequestDto requestDto) {

        ReviewResponseDto reviewResponseDto = reviewService.createReview(itemId,requestDto);

        return ResponseEntity.ok(reviewResponseDto);
    }

    // Review 조회
    @GetMapping("/items/detail/comments/{itemId}")
    public ResponseEntity<?> getReview(@PathVariable Long itemId) {

        ReviewListResponseDto reviewGetResponseDtoList = reviewService.getReview(itemId);

        return ResponseEntity.ok(reviewGetResponseDtoList);
    }

    // Review 수정
    @PutMapping("/items/detail/comments/{itemId}/{commentId}")
    public ResponseEntity<?> updateReview(@PathVariable Long itemId,
                                          @PathVariable Long commentId,
                                          @RequestBody ReviewRequestDto requestDto) {

        ReviewUpdateResponseDto reviewUpdateResponseDto = reviewService.updateReview(itemId, commentId, requestDto);

        return ResponseEntity.ok(reviewUpdateResponseDto);
    }

    //Review 삭제
    @DeleteMapping("/items/detail/comments/{itemId}/{commentId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long itemId, @PathVariable Long commentId) {
        reviewService.deleteReview(itemId, commentId);
        return ResponseEntity.ok("success : true");
    }
}

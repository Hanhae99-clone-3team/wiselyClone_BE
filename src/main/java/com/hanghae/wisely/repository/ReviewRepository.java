package com.hanghae.wisely.repository;

import com.hanghae.wisely.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByItem_Id(Long itemId);

    Review findReviewByItem_IdAndId(Long itemId, Long commentId);
}

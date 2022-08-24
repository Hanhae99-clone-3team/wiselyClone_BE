package com.hanghae.wisely.repository;

import com.hanghae.wisely.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByItem_Id(Long itemId, Pageable pageable);

    Review findReviewByItem_IdAndId(Long itemId, Long commentId);
}

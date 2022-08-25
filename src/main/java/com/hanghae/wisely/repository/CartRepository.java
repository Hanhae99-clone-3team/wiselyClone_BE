package com.hanghae.wisely.repository;

import com.hanghae.wisely.domain.Cart;
import com.hanghae.wisely.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // 멤버 추가 필요
    // 결제 되지 앟은 장바구니 검색
    @Query("SELECT c from Cart c where c.idPaid = false and c.member = :member ")
    Cart findByIsPaidFalse(Member member);
}

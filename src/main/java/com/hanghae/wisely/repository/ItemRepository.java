package com.hanghae.wisely.repository;

import com.hanghae.wisely.domain.Category;
import com.hanghae.wisely.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // 조회할 때 파라미터의 타입을 String 에서 Enum 타입으로 변환
    List<Item> findByCategory(@Param(value = "category") Category category);
}

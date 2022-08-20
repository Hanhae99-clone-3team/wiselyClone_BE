package com.hanghae.wisely.controller;

import com.hanghae.wisely.dto.request.ItemRequestDto;
import com.hanghae.wisely.dto.response.ItemDetailResponseDto;
import com.hanghae.wisely.dto.response.ItemResponseDto;
import com.hanghae.wisely.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // Item 등록
    @PostMapping("/api/item")
    public ResponseEntity<?> createItem(@RequestBody ItemRequestDto requestDto) {

        itemService.createItem(requestDto);

        return ResponseEntity.ok("success");
    }

    // Item 전체 조회
    @GetMapping("/home/main")
    public ResponseEntity<?> getItem() {
        List<ItemResponseDto> itemResponseDto = itemService.getItem();

        return ResponseEntity.ok(itemResponseDto);
    }

    // Item 카테고리에 따른 분류 조최
    @GetMapping("/home/main/{category}")
    public ResponseEntity<?> getCategoryItem(@PathVariable String category) {

        List<ItemResponseDto> itemResponseDtoList = itemService.getCategoryItem(category);

        return ResponseEntity.ok(itemResponseDtoList);
    }

    // Item Detail 조회
    @GetMapping("/items/detail/{itemId}")
    public ResponseEntity<?> getDetailItem(@PathVariable Long itemId) {
        ItemDetailResponseDto itemDetailResponseDto = itemService.getDetailItem(itemId);

        return ResponseEntity.ok(itemDetailResponseDto);
    }
}

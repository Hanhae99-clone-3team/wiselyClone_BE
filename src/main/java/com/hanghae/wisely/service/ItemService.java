package com.hanghae.wisely.service;

import com.hanghae.wisely.domain.Category;
import com.hanghae.wisely.domain.Item;
import com.hanghae.wisely.domain.Review;
import com.hanghae.wisely.dto.request.ItemRequestDto;
import com.hanghae.wisely.dto.response.ItemDetailResponseDto;
import com.hanghae.wisely.dto.response.ItemResponseDto;
import com.hanghae.wisely.repository.ItemRepository;
import com.hanghae.wisely.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;

    // Item 등록
    public void createItem(ItemRequestDto requestDto) {
        Item item = Item.builder()
                .itemName(requestDto.getItemName())
                .itemDesc(requestDto.getItemDesc())
                .itemPrice(requestDto.getItemPrice())
                .itemImgUrl(requestDto.getItemImgUrl())
                .itemDetailImag1(requestDto.getItemDetailImg1())
                .itemDetailImag2(requestDto.getItemDetailImg2())
                .category(requestDto.getCategory())
                .build();
        itemRepository.save(item);
    }

    // Item 전체 조회
    public List<ItemResponseDto> getItem() {

        List<Item> itemList = itemRepository.findAll();
        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();

        for (Item item : itemList) {
            // double avgRate 선언
            double avgRate = 0;
            // 반복문으로 review 에서 rate값을 꺼내 avgRate 더함
            for (Review review : item.getReviewList()) {
                avgRate = (avgRate + review.getRate());
            }
            itemResponseDtoList.add(
                    ItemResponseDto.builder()
                            .itemId(item.getId())
                            .itemName(item.getItemName())
                            .itemDesc(item.getItemDesc())
                            // avgRate를 review 개수로 나눔
                            .itemRate((avgRate/(item.getReviewList().stream().count())))
                            .itemReviewCount(item.getReviewList().stream().count())
                            .itemPrice(item.getItemPrice())
                            .itemImgUrl(item.getItemImgUrl())
                            .build()
            );
        }
        return itemResponseDtoList;
    }

    // 카테고리에 따른 조회
    @Transactional(readOnly = true)
    public List<ItemResponseDto> getCategoryItem(String category) {

        // String 타입의 category 를 Enum 타입으로 조회해서 repository 에 파라미터로 넘김
        Category categoryByEnum = Category.valueOf(category);

        List<Item> itemList = itemRepository.findByCategory(categoryByEnum);
        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();

        for (Item item : itemList) {
            // double avgRate 선언
            double avgRate = 0;
            // 반복문으로 review 에서 rate값을 꺼내 avgRate 더함
            for (Review review : item.getReviewList()) {
                avgRate = (avgRate + review.getRate());
            }
            itemResponseDtoList.add(
                    ItemResponseDto.builder()
                            .itemId(item.getId())
                            .itemName(item.getItemName())
                            .itemDesc(item.getItemDesc())
                            // avgRate를 review 개수로 나눔
                            .itemRate((avgRate/(item.getReviewList().stream().count())))
                            .itemReviewCount(item.getReviewList().stream().count())
                            .itemPrice(item.getItemPrice())
                            .itemImgUrl(item.getItemImgUrl())
                            .build()
            );
        }
        return itemResponseDtoList;
    }

    // Item 상세 보기
    public ItemDetailResponseDto getDetailItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 아이템이 존재하지 않습니다. itemId: " + itemId);
        });
        double avgRate = 0;
        for (Review review : item.getReviewList()){
            avgRate = (avgRate + review.getRate());
        }

        ItemDetailResponseDto itemDetailResponseDto = ItemDetailResponseDto.builder()
                .itemId(itemId)
                .itemName(item.getItemName())
                .itemDesc(item.getItemDesc())
                .itemAvgRate((avgRate/(item.getReviewList().stream().count())))
                .itemReviewCount(item.getReviewList().stream().count())
                .itemPrice(item.getItemPrice())
                .itemImgUrl(item.getItemImgUrl())
                .itemDetailImg1(item.getItemDetailImag1())
                .itemDetailImg2(item.getItemDetailImag2())
                .build();

        return itemDetailResponseDto;
    }

}

package com.hanghae.wisely.service;

import com.hanghae.wisely.domain.Category;
import com.hanghae.wisely.domain.Item;
import com.hanghae.wisely.domain.Review;
import com.hanghae.wisely.dto.request.ItemRequestDto;
import com.hanghae.wisely.dto.response.ItemDetailResponseDto;
import com.hanghae.wisely.dto.response.ItemResponseDto;
import com.hanghae.wisely.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private double avgRate =0;

    // Item 등록
    public void createItem(ItemRequestDto requestDto) {
        Item item = Item.builder()
                .itemName(requestDto.getItemName())
                .itemDesc(requestDto.getItemDesc())
                .itemPrice(requestDto.getItemPrice())
                .itemImgUrl(requestDto.getItemImgUrl())
                .itemDetailImg1(requestDto.getItemDetailImg1())
                .itemDetailImg2(requestDto.getItemDetailImg2())
                .category(requestDto.getCategory())
                .build();
        itemRepository.save(item);
    }

    // Item 전체 조회
    @Transactional(readOnly = true)
    public List<ItemResponseDto> getItem() {

        // Repository에서 모든 Item 조회
        List<Item> itemList = itemRepository.findAll();
        List<ItemResponseDto> itemResponseDtoList = ConvertToSimple(itemList);
        return itemResponseDtoList;
    }

    // 카테고리에 따른 조회
    @Transactional(readOnly = true)
    public List<ItemResponseDto> getCategoryItem(String category) {

        // String 타입의 category 를 Enum 타입으로 조회해서 repository 에 파라미터로 넘김
        Category categoryByEnum = Category.valueOf(category);
        List<Item> itemList = itemRepository.findByCategory(categoryByEnum);

        List<ItemResponseDto> itemResponseDtoList = ConvertToSimple(itemList);
        return itemResponseDtoList;
    }

    // Item 상세 보기
    public ItemDetailResponseDto getDetailItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 아이템이 존재하지 않습니다. itemId: " + itemId);
        });
        avgRate = 0;
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
                .itemDetailImg1(item.getItemDetailImg1())
                .itemDetailImg2(item.getItemDetailImg2())
                .build();

        return itemDetailResponseDto;
    }

    // 중복 제거
    private List<ItemResponseDto> ConvertToSimple(List<Item> itemList) {
        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        for (Item item : itemList) {
            // double avgRate 선언
            avgRate = 0;
            // 반복문으로 review 에서 rate값을 꺼내 avgRate 더함
            for (Review review : item.getReviewList()) {
                avgRate = (avgRate + review.getRate());
            }
            itemResponseDtoList.add(
                    ItemResponseDto.builder()
                            .itemId(item.getId())
                            .itemName(item.getItemName())
                            .itemDesc(item.getItemDesc())
                            // avgRate를 review 개수로 나눔, Math.round를 이용해서 소수점 첫 번째 자리까지 표시
                            .itemRate(Math.round((avgRate/(item.getReviewList().stream().count())) *10)/10.0)
                            .itemReviewCount(item.getReviewList().stream().count())
                            .itemPrice(item.getItemPrice())
                            .itemImgUrl(item.getItemImgUrl())
                            .build()
            );
        }
        return itemResponseDtoList;
    }

}

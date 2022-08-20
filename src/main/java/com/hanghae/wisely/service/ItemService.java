package com.hanghae.wisely.service;

import com.hanghae.wisely.domain.Category;
import com.hanghae.wisely.domain.Item;
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

    // Item 등록
    public void createItem(ItemRequestDto requestDto) {
        Item item = Item.builder()
                .itemName(requestDto.getItemName())
                .itemDesc(requestDto.getItemDesc())
                .itemPrice(requestDto.getItemPrice())
                .itemImgUrl(requestDto.getItemImgUrl())
                .category(requestDto.getCategory())
                .build();
        itemRepository.save(item);
    }

    // Item 전체 조회
    public List<ItemResponseDto> getItem() {

        List<Item> itemList = itemRepository.findAll();
        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();

        for (Item item : itemList) {
            itemResponseDtoList.add(
                    ItemResponseDto.builder()
                            .itemName(item.getItemName())
                            .itemDesc(item.getItemDesc())
                            .itemPrice(item.getItemPrice())
                            .itemImgUrl(item.getItemImgUrl())
                            .build()
            );
        }
        return itemResponseDtoList;
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDto> getCategoryItem(String category) {

        // String 타입의 category 를 Enum 타입으로 조회해서 repository 에 파라미터로 넘김
        Category categoryByEnum = Category.valueOf(category);

        List<Item> itemList = itemRepository.findByCategory(categoryByEnum);
        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();

        for (Item item : itemList) {
            itemResponseDtoList.add(
                    ItemResponseDto.builder()
                            .itemName(item.getItemName())
                            .itemDesc(item.getItemDesc())
                            .itemPrice(item.getItemPrice())
                            .itemImgUrl(item.getItemImgUrl())
                            .build()
            );
        }
        return itemResponseDtoList;
    }

    public ItemDetailResponseDto getDetailItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 아이템이 존재하지 않습니다. itemId: " + itemId);
        });

        ItemDetailResponseDto itemDetailResponseDto = ItemDetailResponseDto.builder()
                .itemName(item.getItemName())
                .itemDesc(item.getItemDesc())
                .itemPrice(item.getItemPrice())
                .itemImgUrl(item.getItemImgUrl())
                .build();

        return itemDetailResponseDto;
    }
}

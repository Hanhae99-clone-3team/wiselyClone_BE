package com.hanghae.wisely.service;

import com.hanghae.wisely.domain.Item;
import com.hanghae.wisely.domain.Member;
import com.hanghae.wisely.domain.Review;
import com.hanghae.wisely.dto.request.ReviewRequestDto;
import com.hanghae.wisely.dto.response.ReviewGetResponseDto;
import com.hanghae.wisely.dto.response.ReviewListResponseDto;
import com.hanghae.wisely.dto.response.ReviewResponseDto;
import com.hanghae.wisely.dto.response.ReviewUpdateResponseDto;
import com.hanghae.wisely.repository.ItemRepository;
import com.hanghae.wisely.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;

    // Review 생성
    @Transactional
    public ReviewResponseDto createReview(Long itemId, ReviewRequestDto requestDto, Member member) {

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
                .member(member)
                .item(item)
                .build();
        reviewRepository.save(review);

        return ReviewResponseDto.builder()
                .commentId(review.getId())
                .comment(review.getComment())
                .name(member.getName())
                .rate(review.getRate())
                .createdAt(review.getCreatedAt())
                .build();
    }

    // 리뷰 조회
    @Transactional(readOnly = true)
    public ReviewListResponseDto getReview(Long itemId, Pageable pageable) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalArgumentException("Item이 존재하지 않습니다.")
        );

        Page<Review> reviewList =  reviewRepository.findAllByItem_Id(item.getId(),pageable);

        List<ReviewGetResponseDto> reviewGetResponseDtoList = new ArrayList<>();

        for (Review review : reviewList) {
            reviewGetResponseDtoList.add(
                    ReviewGetResponseDto.builder()
                            .commentId(review.getId())
                            .comment(review.getComment())
                            .name(review.getMember().getName())
                            .age((birthday(review.getMember().getBirthday()))+ "대")
                            .commentRate(review.getRate())
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
    public ReviewUpdateResponseDto updateReview(Long itemId, Long commentId, ReviewRequestDto requestDto, Member member) {

        // review 에 해당 id 들이 있는지 확인하고 없으면 에러처리
       Review review = isPresentReview(itemId, commentId);

       if (!review.getMember().getId().equals(member.getId())) {
           throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
       }

        // rate 가 1이하 5 이상 이면 에러
        if (!(requestDto.getStar().intValue() >= 1 & requestDto.getStar().intValue() <= 5)) {
            throw new IllegalArgumentException("star의 값은 1 이상 5 이하여야 합니다. star : " + requestDto.getStar());
        }

       // review 수정
        review.update(requestDto);

        ReviewUpdateResponseDto reviewUpdateResponseDto = ReviewUpdateResponseDto.builder()
                .commentId(commentId)
                .comment(review.getComment())
                .username(member.getName())
                .createdAt(review.getCreatedAt())
                .age((birthday(review.getMember().getBirthday()))+ "대")
                .rate(review.getRate())
                .build();

        return reviewUpdateResponseDto;
    }

    // Review 삭제
    @Transactional
    public void deleteReview(Long itemId, Long commentId, Member member) {

        // 존재하는지 확인
        Review review = isPresentReview(itemId, commentId);

        if (!review.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        // Reivew 삭제
        reviewRepository.delete(review);
    }

    // Review 검증
    @Transactional(readOnly = true)
    public Review isPresentReview(Long itemId, Long commentId) {
        Optional<Review> review = Optional.ofNullable(reviewRepository.findReviewByItem_IdAndId(itemId, commentId));
        return review.orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다. Item ID : "+ itemId + " Comment ID : " + commentId));}


    // 생년월일로  나이대 구하기
    public String birthday(String strAge) {
        int basicAge = 0;

        Date date = new Date(); // 날짜 선언

        SimpleDateFormat today = new SimpleDateFormat("MMdd"); // MM.dd 데이터
        String strDate = (today.format(date)); // MM.dd 값을 String strDate 로 지정
        int day = Integer.parseInt(strDate);

        // ex) 960707

        String str1 = strAge.substring(0,2); // 년도  ex) 96
        String str2 = strAge.substring(2); // 월, 일 ex) 0707

        int yy = Integer.parseInt(str1); // 년도 형변환  96
        int md = Integer.parseInt(str2); // 만나이  707

        if (md > day & yy > 20){ // 생일을 지나고 년도가 20보다 크면
            basicAge = 122 - yy; // 생일 지난 96년생 기준 만 26
        } else if (md >= day & yy <= 20) { // 생일을 지나고 년도가 20살 이하이면
            basicAge = 22 - yy; // 생일 지난 03년생 기준   만 19살
        } else if (md < day & yy > 20) { // 생일을 지나지 않았고 년도가 20살 초과면 
            basicAge = 121 - yy; // 생일 안지난 96년생 기준  만 25
        } else if (md < day & yy < 20) { // 생일을 지나지 않았고 년도가 20 미만
            basicAge = 21 - yy; // 생일 안지난 03년생 기준 만 18살
        }
        // 조건문을 통해 구한 만나이를 String 으로 변환하고 앞에 한 글자만 가져온다.
        String strBasicAge = (Integer.toString(basicAge)).substring(0,1);
        // 다시 한 글자를 Long 타입으로 변환하고 10을 곱해 10, 20, 30, 40 <-- 이런 식으로 반환한다.
        Long simpleAge = Long.parseLong(strBasicAge)*10;

        // 다시 String 으로 형병환 한다.
        String age = String.valueOf(simpleAge);

        return age;
    }

}

package com.hanghae.wisely.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

    // String 타입으로 변경
    @CreatedDate
    private String createdAt;

//    @LastModifiedDate
//    private String modifiedAt;

    // @PrePersist : 해당 엔티티를 저장하기 이전에 실행
    @PrePersist
    public void onPrePersist() {

        // LocalDate를 원하는 형식을 createdAt 에 대입
        this.createdAt = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
//        this.modifiedAt = createdAt;
    }

    // @PreUpdate : 해당 엔티티를 업데이트 하기 이전에 실행
//    @PreUpdate
//    public void onPreUpdate() {
//        this.modifiedAt = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
//    }
}

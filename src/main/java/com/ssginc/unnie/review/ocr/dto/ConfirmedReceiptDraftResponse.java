package com.ssginc.unnie.review.ocr.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ConfirmedReceiptDraftResponse {

    private String shopName;
    private int amount;
    private LocalDateTime dateTime;
    // 추가: 시스템 검증용 필드
    private String businessNumber;
    private String approvalNumber;

    @Builder
    public ConfirmedReceiptDraftResponse(String shopName, int amount, LocalDateTime dateTime,
                                         String businessNumber, String approvalNumber) {
        this.shopName = shopName;
        this.amount = amount;
        this.dateTime = dateTime;
        this.businessNumber = businessNumber;
        this.approvalNumber = approvalNumber;
    }
}
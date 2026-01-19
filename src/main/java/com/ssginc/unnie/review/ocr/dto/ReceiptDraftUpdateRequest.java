package com.ssginc.unnie.review.ocr.dto;

import lombok.Getter;
import java.time.LocalDateTime;

/**
 * UI 에서 최종 확정한 값
 * 의미 있는 값만 담는다
 */
@Getter
public class ReceiptDraftUpdateRequest {
    private String shopName;
    private String amount;
    private LocalDateTime dateTime;
}


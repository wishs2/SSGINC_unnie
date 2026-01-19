package com.ssginc.unnie.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReceiptForReviewContext {

    private long receiptId;
    private long shopId;
    private String receiptApprovalNumber;
    private LocalDateTime receiptDate;
}

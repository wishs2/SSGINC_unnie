package com.ssginc.unnie.review.service;

import com.ssginc.unnie.review.dto.ReceiptForReviewContext;

public interface ReceiptVerificationService {
    ReceiptForReviewContext verifyForReview(long receiptId);
}

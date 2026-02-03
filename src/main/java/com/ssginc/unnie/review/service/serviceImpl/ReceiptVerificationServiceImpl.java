package com.ssginc.unnie.review.service.serviceImpl;

import com.ssginc.unnie.common.exception.UnnieReviewException;
import com.ssginc.unnie.common.util.ErrorCode;
import com.ssginc.unnie.review.dto.ReceiptForReviewContext;
import com.ssginc.unnie.review.mapper.ReceiptMapper;
import com.ssginc.unnie.review.service.ReceiptVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReceiptVerificationServiceImpl implements ReceiptVerificationService {

    private final ReceiptMapper receiptMapper;

    @Override
    public ReceiptForReviewContext verifyForReview(long receiptId) {

        ReceiptForReviewContext receipt =
                receiptMapper.findReceiptForReviewContext(receiptId);

        if (receipt == null) {
            throw new UnnieReviewException(ErrorCode.RECEIPT_NOT_FOUND);
        }

        // 승인번호 검증
        if (receipt.getReceiptApprovalNumber() == null ||
                receipt.getReceiptApprovalNumber().equals("데이터 없음")) {
            throw new UnnieReviewException(ErrorCode.INVALID_RECEIPT_APPROVALNumber);
        }

        // 30일 이내
        long days = ChronoUnit.DAYS.between(
                receipt.getReceiptDate(),
                LocalDateTime.now()
        );

        if (days > 30) {
            throw new UnnieReviewException(ErrorCode.EXPIRED_RECEIPT);
        }

        return receipt;
    }
}
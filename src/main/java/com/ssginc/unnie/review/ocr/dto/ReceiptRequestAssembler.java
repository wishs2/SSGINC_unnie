package com.ssginc.unnie.review.ocr.dto;

import com.ssginc.unnie.review.dto.ReceiptRequest;

public class ReceiptRequestAssembler {

    private ReceiptRequestAssembler() {}

    public static ReceiptRequest from(
            ConfirmedReceiptDraftResponse draft,
            String businessNumber,
            String approvalNumber
    ) {
        return new ReceiptRequest(
                0L, // receiptId (DB 생성)
                0L, // receiptShopId (매핑 전)
                draft.getDateTime(),
                draft.getAmount(),
                businessNumber,
                approvalNumber,
                draft.getShopName()
        );
    }
}

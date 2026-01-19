package com.ssginc.unnie.review.ocr;

import com.ssginc.unnie.review.ocr.dto.ConfirmedReceiptDraftResponse;
import com.ssginc.unnie.review.ocr.dto.ReceiptDraftUpdateRequest;
import com.ssginc.unnie.review.ocr.extractor.FieldType;

/**
 * OCR Draft + 사용자 확정값을 합쳐
 * 리뷰 작성에 사용할 수 있는
 * 최종 확정 영수증 데이터 생성
 */
public class ReceiptFinalAssembler {

    public static ConfirmedReceiptDraftResponse assemble(
            ReceiptDraft draft,
            ReceiptDraftUpdateRequest request
    ) {
        return ConfirmedReceiptDraftResponse.builder()
                .shopName(request.getShopName())
                .amount(parseAmount(request.getAmount()))
                .dateTime(request.getDateTime())
                // Map에서 FieldType 키를 이용해 값을 추출하도록 수정
                .businessNumber(draft.getFieldValue(FieldType.BUSINESS_NUMBER))
                .approvalNumber(draft.getFieldValue(FieldType.APPROVAL_NUMBER))
                .build();
    }

    private static int parseAmount(String amount) {
        if (amount == null || amount.isBlank()) {
            return 0;
        }
        return Integer.parseInt(amount.replaceAll("[^0-9]", ""));
    }
}


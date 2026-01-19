package com.ssginc.unnie.review.ocr;

import com.ssginc.unnie.review.ocr.extractor.ExtractedField;
import com.ssginc.unnie.review.ocr.extractor.FieldType;
import lombok.Getter;

import java.util.Map;

/**
 * confirmed / uncertain 보존
 */

@Getter
public class ReceiptDraft {

    /**
     * confidence 기준 통과
     */
    private final Map<FieldType, ExtractedField<?>> confirmed;

    /**
     * confidence 낮아서 사용자 확인 필요
     */
    private final Map<FieldType, ExtractedField<?>> uncertain;

    public ReceiptDraft(
            Map<FieldType, ExtractedField<?>> confirmed,
            Map<FieldType, ExtractedField<?>> uncertain
    ) {
        this.confirmed = confirmed;
        this.uncertain = uncertain;
    }

    /**
     * 특정 타입의 필드 값을 문자열로 가져오는 공통 메서드
     */
    public String getFieldValue(FieldType type) {
        // confirmed에 있으면 우선 가져오고, 없으면 uncertain에서 찾음
        ExtractedField<?> field = confirmed.getOrDefault(type, uncertain.get(type));
        return (field != null && field.getValue() != null) ? field.getValue().toString() : "";
    }
}

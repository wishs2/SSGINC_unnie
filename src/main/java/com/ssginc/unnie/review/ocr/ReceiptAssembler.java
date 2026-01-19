package com.ssginc.unnie.review.ocr;

import com.ssginc.unnie.review.ocr.extractor.ExtractedField;
import com.ssginc.unnie.review.ocr.extractor.FieldType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * confidence 기준 분리
 */
@Component
public class ReceiptAssembler {

    //confidence 기준
    // 타입별 threshold 분리
    private static final Map<FieldType, Float> CONFIDENCE_THRESHOLD_MAP = Map.of(
            FieldType.AMOUNT, 0.75f,
            FieldType.DATE_TIME, 0.75f,
            FieldType.SHOP_NAME, 0.70f,
            FieldType.BUSINESS_NUMBER, 0.80f,
            FieldType.APPROVAL_NUMBER, 0.80f
    );

    public static ReceiptDraft assemble(
            Map<FieldType, ExtractedField<?>> extracted
    ) {
        Map<FieldType, ExtractedField<?>> confirmed = new HashMap<>();
        Map<FieldType, ExtractedField<?>> uncertain = new HashMap<>();

        extracted.forEach((type, field) -> {
            float threshold = CONFIDENCE_THRESHOLD_MAP
                    .getOrDefault(type, 0.85f); // fallback

            if (field.getConfidence() >= threshold) {
                confirmed.put(type, field);
            } else {
                uncertain.put(type, field);
            }
        });

        return new ReceiptDraft(confirmed, uncertain);
    }
}

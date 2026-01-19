package com.ssginc.unnie.review.ocr.dto;

import com.ssginc.unnie.review.ocr.ReceiptDraft;
import com.ssginc.unnie.review.ocr.extractor.ExtractedField;
import com.ssginc.unnie.review.ocr.extractor.FieldType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReceiptDraftResponseMapper {

    public static ReceiptDraftResponse from(ReceiptDraft draft) {

        List<ConfirmedFieldResponse> confirmed =
                mapConfirmed(draft.getConfirmed());

        List<UncertainFieldResponse> uncertain =
                mapUncertain(draft.getUncertain());

        return new ReceiptDraftResponse(confirmed, uncertain);
    }

    private static List<ConfirmedFieldResponse> mapConfirmed(
            Map<FieldType, ExtractedField<?>> fields
    ) {
        return fields.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry ->
                        new ConfirmedFieldResponse(
                                entry.getKey(),
                                entry.getValue().getValue()
                        )
                )
                .collect(Collectors.toList());
    }

    private static List<UncertainFieldResponse> mapUncertain(
            Map<FieldType, ExtractedField<?>> fields
    ) {
        return fields.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    ExtractedField<?> field = entry.getValue();
                    return new UncertainFieldResponse(
                            entry.getKey(),
                            field.getValue(),
                            field.getConfidence(),
                            field.getSourceText()
                    );
                })
                .collect(Collectors.toList());
    }
}

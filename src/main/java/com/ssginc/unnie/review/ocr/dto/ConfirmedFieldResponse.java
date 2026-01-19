package com.ssginc.unnie.review.ocr.dto;

import com.ssginc.unnie.review.ocr.extractor.FieldType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfirmedFieldResponse {
    private FieldType type;
    private Object value;
}

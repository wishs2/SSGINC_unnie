package com.ssginc.unnie.review.ocr.dto;

import com.ssginc.unnie.review.ocr.extractor.FieldType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UncertainFieldResponse {

    private FieldType type;
    private Object value;
    private float confidence;
    private String sourceText;
}

package com.ssginc.unnie.review.ocr.extractor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExtractedField<T> {
    private final T value;
    private final float confidence;
    private final String sourceText;
}

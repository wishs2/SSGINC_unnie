package com.ssginc.unnie.review.ocr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OCRToken {

    private final String text;
    private final float confidence;
    private final int lineIndex;

    private final float xMin;
    private final float yMin;
    private final float xMax;
    private final float yMax;
}


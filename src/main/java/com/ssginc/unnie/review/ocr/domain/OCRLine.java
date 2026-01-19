package com.ssginc.unnie.review.ocr.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class OCRLine {

    private final int lineIndex;
    private final List<OCRToken> tokens;

    // 의미 단위로 분리된 텍스트를 저장하기 위한 필드
    // null 이면 tokens 기반 getText() 사용
    private final String overriddenText;

    public OCRLine(int lineIndex, List<OCRToken> tokens) {
        this(lineIndex, tokens, null);
    }

    private OCRLine(int lineIndex, List<OCRToken> tokens, String overriddenText) {
        this.lineIndex = lineIndex;
        this.tokens = tokens;
        this.overriddenText = overriddenText;
    }

    /**
     * 기존 OCRLine의 신뢰도/토큰은 유지한 채
     * Extractor용 텍스트만 교체한 새로운 OCRLine 생성
     */
    public OCRLine withText(String newText) {
        return new OCRLine(this.lineIndex, this.tokens, newText);
    }

    public String getText() {
        if (overriddenText != null) {
            return overriddenText;
        }

        return tokens.stream()
                .map(OCRToken::getText)
                .reduce("", (a, b) -> a + " " + b)
                .trim();
    }

    public float averageConfidence() {
        return (float) tokens.stream()
                .mapToDouble(OCRToken::getConfidence)
                .average()
                .orElse(0.0);
    }
}

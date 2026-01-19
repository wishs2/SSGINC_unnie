package com.ssginc.unnie.review.ocr.extractor;

import com.ssginc.unnie.review.ocr.domain.OCRLine;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessNumberExtractor implements ReceiptFieldExtractor {
    private static final Pattern BUSINESS_NUMBER_PATTERN =
            Pattern.compile("(사업자(?:등록)?번호[:\\s]*)?(\\d{3}-?\\d{2}-?\\d{5})");

    @Override
    public FieldType getFieldType() {
        return FieldType.BUSINESS_NUMBER;
    }

    @Override
    public Optional<ExtractedField<?>> extract(List<OCRLine> lines) {
        return lines.stream()
                .map(this::extractFromLine)
                .flatMap(Optional::stream)
                .max(Comparator.comparing(ExtractedField::getConfidence));
    }

    private Optional<ExtractedField<?>> extractFromLine(OCRLine line) {
        Matcher matcher = BUSINESS_NUMBER_PATTERN.matcher(line.getText());
        if (!matcher.find()) return Optional.empty();

        String raw = matcher.group(2).replaceAll("-", ""); // 하이픈 제거 후 10자리 확인
        if (raw.length() != 10) return Optional.empty();

        float confidence = line.averageConfidence();
        if (matcher.group(1) != null) confidence += 0.1f;

        return Optional.of(new ExtractedField<>(raw, Math.min(confidence, 1.0f), line.getText()));
    }
}
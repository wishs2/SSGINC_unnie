package com.ssginc.unnie.review.ocr.extractor;

import com.ssginc.unnie.review.ocr.domain.OCRLine;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApprovalNumberExtractor implements ReceiptFieldExtractor {
    // 승인번호는 보통 8자리 숫자입니다. 앞에 '승인'이라는 단어가 붙어야 더 신뢰합니다.
    private static final Pattern APPROVAL_PATTERN =
            Pattern.compile("(승인\\s*(?:번호)?[:\\s]*)(\\d{8})");

    @Override
    public FieldType getFieldType() { return FieldType.APPROVAL_NUMBER; }

    @Override
    public Optional<ExtractedField<?>> extract(List<OCRLine> lines) {
        return lines.stream()
                .map(this::extractFromLine)
                .flatMap(Optional::stream)
                .max(Comparator.comparing(ExtractedField::getConfidence));
    }

    private Optional<ExtractedField<?>> extractFromLine(OCRLine line) {
        Matcher matcher = APPROVAL_PATTERN.matcher(line.getText());
        if (!matcher.find()) return Optional.empty();

        // 8자리 숫자만 추출
        String value = matcher.group(2);
        float confidence = line.averageConfidence();

        // 키워드가 명확히 포함되어 있으므로 가중치 부여
        confidence += 0.2f;

        return Optional.of(new ExtractedField<>(value, Math.min(confidence, 1.0f), line.getText()));
    }
}

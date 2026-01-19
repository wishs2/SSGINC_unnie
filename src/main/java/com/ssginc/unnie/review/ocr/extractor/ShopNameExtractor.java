package com.ssginc.unnie.review.ocr.extractor;

import com.ssginc.unnie.review.ocr.domain.OCRLine;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopNameExtractor implements ReceiptFieldExtractor {
    private static final Pattern SHOP_PATTERN =
            Pattern.compile("(상\\s*호|점\\s*포\\s*명|지\\s*점|가맹점명)[:\\s]*([^\\n\\r|]+)");

    @Override
    public FieldType getFieldType() { return FieldType.SHOP_NAME; }

    @Override
    public Optional<ExtractedField<?>> extract(List<OCRLine> lines) {
        return lines.stream()
                .map(this::extractFromLine)
                .flatMap(Optional::stream)
                .max(Comparator.comparing(ExtractedField::getConfidence));
    }

    private Optional<ExtractedField<?>> extractFromLine(OCRLine line) {
        String text = line.getText();
        Matcher matcher = SHOP_PATTERN.matcher(text);

        if (!matcher.find()) return Optional.empty();

        String rawName = matcher.group(2).trim();
        String cleanedName = cleanName(rawName);

        if (cleanedName.isEmpty() || cleanedName.length() > 25) return Optional.empty();

        return Optional.of(new ExtractedField<>(cleanedName, line.averageConfidence(), text));
    }

    private String cleanName(String name) {
        // 주요 키워드로 자르기 (영수증 줄바꿈 오류 대응)
        String[] filters = {"사업자", "대표", "전화", "주소", "번호", "TEL"};
        for (String filter : filters) {
            if (name.contains(filter)) {
                name = name.split(filter)[0];
            }
        }
        return name.replaceAll("[:\\-]", "").trim();
    }
}

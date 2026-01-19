package com.ssginc.unnie.review.ocr.util.parser;

import com.ssginc.unnie.review.ocr.domain.OCRLine;
import com.ssginc.unnie.review.ocr.domain.OCRToken;
import com.ssginc.unnie.review.ocr.extractor.*;
import com.ssginc.unnie.review.ocr.util.OCRLineGrouper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class OCRReceiptParser {

    private static final int MAX_LINE_LENGTH = 80;

    private static final List<String> SPLIT_KEYWORDS = List.of(
            "승인일시",
            "결제금액",
            "사업자등록번호",
            "가맹점번호",
            "카드번호",
            "승인번호",
            "메뉴명",
            "상호"
    );

    private final List<ReceiptFieldExtractor> extractors = List.of(
            new ShopNameExtractor(),
            new AmountExtractor(),
            new DateTimeExtractor(),
            new BusinessNumberExtractor(),
            new ApprovalNumberExtractor()
    );

    public Map<FieldType, ExtractedField<?>> parse(List<OCRToken> tokens) {

        tokens.forEach(t -> {
            if (t.getText().matches(".*\\d.*")) {
                log.debug("[TOKEN] {}", t.getText());
            }
        });

        // Token → Line
        List<OCRLine> rawLines = OCRLineGrouper.group(tokens);

        // Line 후처리 (의미 단위 분리)
        List<OCRLine> lines = rawLines.stream()
                // normalizeLine()이 List<OCRLine>을 반환하므로 stream()으로 펼쳐준다
                .flatMap(line -> normalizeLine(line).stream())
                .collect(Collectors.toList());

        lines.forEach(l ->
                log.debug("[LINE] {} (avgConf={})", l.getText(), l.averageConfidence())
        );

        // Line 기반 추출
        return extractors.stream()
                .map(extractor ->
                        extractor.extract(lines)
                                .map(result ->
                                        Map.entry(extractor.getFieldType(), result)
                                )
                )
                .flatMap(Optional::stream)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    /**
     * 지나치게 긴 OCRLine을 키워드 기준으로 분리한다.
     * 의미 단위가 명확한 경우에만 분리하며,
     * 그렇지 않으면 원본 Line을 그대로 유지한다.
     */
    private List<OCRLine> normalizeLine(OCRLine line) {
        String text = line.getText();

        if (text.length() <= MAX_LINE_LENGTH) {
            return List.of(line);
        }

        List<OCRLine> result = new ArrayList<>();
        String remaining = text;

        for (String keyword : SPLIT_KEYWORDS) {
            int index = remaining.indexOf(keyword);
            if (index > 0) {
                String before = remaining.substring(0, index).trim();
                if (!before.isEmpty()) {
                    result.add(line.withText(before));
                }
                remaining = remaining.substring(index);
            }
        }

        if (!remaining.isBlank()) {
            result.add(line.withText(remaining.trim()));
        }

        return result.isEmpty() ? List.of(line) : result;
    }
}

package com.ssginc.unnie.review.ocr.extractor;

import com.ssginc.unnie.review.ocr.domain.OCRLine;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AmountExtractor implements ReceiptFieldExtractor {

    @Override
    public FieldType getFieldType() {
        return FieldType.AMOUNT;
    }

    // 합계일 확률이 매우 높은 키워드들
    private static final List<String> TOTAL_KEYWORDS =
            List.of("합계", "총액", "결제금액", "승인금액", "받을금액");

    // 금액으로 제외해야 할 키워드 (정확도 향상)
    private static final List<String> SKIP_KEYWORDS =
            List.of("부가세", "VAT", "단가", "수량", "할인");

    @Override
    public Optional<ExtractedField<?>> extract(List<OCRLine> lines) {
        return lines.stream()
                .map(line -> extractFromLine(line, lines)) //전체 라인 문맥 참조
                .flatMap(Optional::stream)
                .max(Comparator.comparing(ExtractedField::getConfidence));
    }

    private Optional<ExtractedField<?>> extractFromLine(OCRLine line, List<OCRLine> allLines) {
        String text = line.getText().replace(" ", ""); //공백 제거 후 분석

        // 1. 숫자가 아예 없으면 탈락
        if (!text.matches(".*\\d.*")) return Optional.empty();

        // ============================
        // 날짜 / 시간 라인은 금액 후보에서 제외
        // ============================

//        // yyyy-MM-dd / yyyy.MM.dd / yyyy/MM/dd 형태
//        if (text.matches(".*\\d{4}[./\\-]\\d{2}[./\\-]\\d{2}.*")) {
//            return Optional.empty();
//        }
//
//        // HH:mm 또는 HH:mm:ss 형태
//        if (text.matches(".*\\d{2}:\\d{2}(:\\d{2})?.*")) {
//            return Optional.empty();
//        }


        // 2. 제외 키워드가 포함된 라인은 점수를 대폭 깎거나 제외
        for (String skip : SKIP_KEYWORDS) {
            if (text.contains(skip)) return Optional.empty();
        }

        int amount = parseAmount(text);
        if (amount < 100) return Optional.empty(); //부가세 등 다른 금액 항목 추출 방지

        float confidence = line.averageConfidence();

        boolean looksLikeDate = text.matches(".*\\d{4}[./\\-]\\d{2}[./\\-]\\d{2}.*");
        boolean looksLikeTime = text.matches(".*\\d{2}:\\d{2}(:\\d{2})?.*");

        if (looksLikeDate || looksLikeTime) {
            confidence -= 0.4f; // 탈락시키지 말고 감점
        }

        // 3. 문맥 보정: 현재 라인이나 바로 윗 라인에 합계 키워드가 있는지 확인
        boolean hasTotalKeyword = TOTAL_KEYWORDS.stream().anyMatch(text::contains);

        // 윗 라인 확인 로직 (구조적 보완)
        if (!hasTotalKeyword) {
            int currentIndex = allLines.indexOf(line);
            if (currentIndex > 0) {
                String prevText = allLines.get(currentIndex - 1).getText();
                if (TOTAL_KEYWORDS.stream().anyMatch(prevText::contains)) {
                    hasTotalKeyword = true;
                    confidence += 0.3f; //윗 줄에 키워드가 있다면 신뢰도 상승
                }
            }
        } else {
            confidence += 0.4f;
        }

        // 키워드 없을 경우 fallback
        if (!hasTotalKeyword) {
            confidence -= 0.3f; // 신뢰도 낮게
        }

        return Optional.of(
                new ExtractedField<>(amount, Math.max(confidence, 0.3f), text)
        );

//        return hasTotalKeyword ?
//                Optional.of(new ExtractedField<>(amount, Math.min(confidence, 1.0f), text)) :
//                Optional.empty();
    }

    // parseAmount는 기존 로직 유지하되 안전 장치 추가
    private int parseAmount(String text) {

        // 숫자와 콤마만 허용
        String cleaned = text.replaceAll("[^0-9,]", "")
                .replace(",", "");

        // 숫자가 없으면 탈락
        if (cleaned.isEmpty()) {
            return 0;
        }

        // 너무 긴 숫자는 금액이 아님 (전화번호/사업자번호/기타)
        // 일반적인 영수증 금액은 1 ~ 9자리 내외
        if (cleaned.length() > 9) {
            return 0;
        }

        try {
            return Integer.parseInt(cleaned);
        } catch (NumberFormatException e) {
            // 금액 파싱 실패 시 조용히 탈락
            return 0;
        }
    }

}

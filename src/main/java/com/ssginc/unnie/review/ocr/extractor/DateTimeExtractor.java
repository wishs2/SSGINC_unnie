package com.ssginc.unnie.review.ocr.extractor;

import com.ssginc.unnie.review.ocr.domain.OCRLine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeExtractor implements ReceiptFieldExtractor {

    private static final Pattern DATE_PATTERN =
            Pattern.compile("(\\d{2,4}[./\\-]?\\d{2}[./\\-]?\\d{2})");

    private static final Pattern TIME_PATTERN =
            Pattern.compile("(\\d{2}:\\d{2}(?::\\d{2})?)");

    @Override
    public FieldType getFieldType() {
        return FieldType.DATE_TIME;
    }

    @Override
    public Optional<ExtractedField<?>> extract(List<OCRLine> lines) {
        return lines.stream()
                .map(line -> extractFromLine(line, lines))
                .flatMap(Optional::stream)
                .max(Comparator.comparing(ExtractedField::getConfidence));
    }

    private Optional<ExtractedField<?>> extractFromLine(OCRLine line, List<OCRLine> allLines) {

        /*
         * 1. 날짜 패턴이 이 라인에 존재하지 않으면
         *    이 라인은 날짜/시간 후보가 아니라고 판단
         */
        Matcher dateMatcher = DATE_PATTERN.matcher(line.getText());
        if (!dateMatcher.find()) return Optional.empty();

        /*
         * 2. 매칭된 날짜 문자열을 정규화
         *    (예: 2025.01.18 -> 2025-01-18)
         */
        String rawDate = dateMatcher.group(1);
        String normalizedDate = normalizeDate(rawDate);

        String time = null;

        /*
         * 3. 같은 라인에서 정상적인 시간 패턴(HH:mm 또는 HH:mm:ss)을 우선 탐색
         */
        Matcher timeMatcher = TIME_PATTERN.matcher(line.getText());
        if (timeMatcher.find()) {
            time = timeMatcher.group(1);
        }

        /*
         * 4. 같은 라인에서 시간이 발견되지 않은 경우
         *    날짜 라인을 기준으로 인접 라인에서 시간 정보를 보조 탐색
         */
        if (time == null) {
            int currentIndex = allLines.indexOf(line);

            // 바로 아래 라인 탐색
            if (currentIndex + 1 < allLines.size()) {
                time = extractTimeFromText(allLines.get(currentIndex + 1).getText());
            }

            // 그래도 없으면 바로 위 라인 탐색
            if (time == null && currentIndex - 1 >= 0) {
                time = extractTimeFromText(allLines.get(currentIndex - 1).getText());
            }
        }

        /*
         * 5. OCR 결과에서 시간이 공백으로 쪼개진 특수 케이스 대응
         *    예: "12: 37: 07" → 공백 제거 후 복원
         */
        if (time == null) {
            String textWithoutSpaces = line.getText().replaceAll("\\s+", "");
            Matcher brokenTimeMatcher =
                    Pattern.compile("(\\d{2}:)(\\d{2}:)?(\\d{2})").matcher(textWithoutSpaces);

            if (brokenTimeMatcher.find()) {
                String hour = brokenTimeMatcher.group(1).replace(":", "");
                String minute = brokenTimeMatcher.group(2).replace(":", "");
                String second = brokenTimeMatcher.group(3);

                time = hour + ":" + minute + ":" + second;
            }
        }

        /*
         * 6. 시간 정보가 끝내 없을 경우 안전한 기본값 사용
         *    초 단위가 없는 경우 HH:mm → HH:mm:00 보정
         */
        if (time == null) {
            time = "00:00:00";
        } else if (time.length() == 5) {
            time += ":00";
        }

        /*
         * 7. 날짜 + 시간을 결합한 문자열을 실제 LocalDateTime 으로 검증
         *    파싱 실패 시 잘못된 OCR 결과로 간주
         */
        try {
            String finalDateTime = normalizedDate + " " + time;
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime.parse(finalDateTime, formatter);

            /*
             * 8. 검증이 끝난 경우 ExtractedField 로 감싸서 반환
             *    confidence 는 날짜가 포함된 기준 라인의 평균 신뢰도 사용
             */
            return Optional.of(
                    new ExtractedField<>(
                            finalDateTime,
                            line.averageConfidence(),
                            line.getText()
                    )
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /*
     * 주어진 텍스트에서 정상적인 시간 패턴을 추출하는 보조 메서드
     */
    private String extractTimeFromText(String text) {
        Matcher matcher = TIME_PATTERN.matcher(text);
        return matcher.find() ? matcher.group(1) : null;
    }

    private String normalizeDate(String date) {
        String clean = date.replaceAll("[^0-9]", "");
        if (clean.length() == 8) {
            return clean.substring(0, 4) + "-"
                    + clean.substring(4, 6) + "-"
                    + clean.substring(6, 8);
        }
        return date.replaceAll("[./]", "-");
    }
}

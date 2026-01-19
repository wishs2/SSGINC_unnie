package com.ssginc.unnie.review.ocr.util;

import com.ssginc.unnie.review.ocr.domain.OCRLine;
import com.ssginc.unnie.review.ocr.domain.OCRToken;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OCRLineGrouper {

    public static List<OCRLine> group(List<OCRToken> tokens) {
        Map<Integer, List<OCRToken>> grouped =
                tokens.stream()
                        .collect(Collectors.groupingBy(OCRToken::getLineIndex));

        return grouped.entrySet().stream()
                .map(e -> new OCRLine(e.getKey(), e.getValue()))
                .sorted((a, b) -> Integer.compare(a.getLineIndex(), b.getLineIndex()))
                .toList();
    }
}
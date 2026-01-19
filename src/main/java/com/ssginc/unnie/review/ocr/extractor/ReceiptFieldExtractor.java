package com.ssginc.unnie.review.ocr.extractor;

import com.ssginc.unnie.review.ocr.domain.OCRLine;
import com.ssginc.unnie.review.ocr.domain.OCRToken;

import java.util.List;
import java.util.Optional;

public interface ReceiptFieldExtractor {

    FieldType getFieldType();

    Optional<ExtractedField<?>> extract(List<OCRLine> lines);
}

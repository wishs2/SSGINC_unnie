package com.ssginc.unnie.review.controller;

import com.ssginc.unnie.common.util.ResponseDto;
import com.ssginc.unnie.review.ocr.ReceiptAssembler;
import com.ssginc.unnie.review.ocr.ReceiptDraft;
import com.ssginc.unnie.review.ocr.domain.OCRToken;
import com.ssginc.unnie.review.ocr.domain.OCRTokenMapper;
import com.ssginc.unnie.review.ocr.dto.ReceiptDraftResponse;
import com.ssginc.unnie.review.ocr.dto.ReceiptDraftResponseMapper;
import com.ssginc.unnie.review.ocr.extractor.ExtractedField;
import com.ssginc.unnie.review.ocr.extractor.FieldType;
import com.ssginc.unnie.review.ocr.util.parser.OCRReceiptParser;
import com.ssginc.unnie.review.service.OCRService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ocr")
@RequiredArgsConstructor
@Slf4j
/**
 * OCR → ReceiptDraft 생성
 * 사용자 확인/수정용 (DB 저장 X)
 */
public class OCRController {

    private final OCRService ocrService;
    private final OCRReceiptParser ocrReceiptParser;
    private final ReceiptAssembler receiptAssembler;

    @PostMapping("/upload")
    public ResponseEntity<ResponseDto<ReceiptDraftResponse>> uploadReceipt(
            @RequestParam("file") MultipartFile file
    ) {
        log.info("[OCR] 파일 업로드 요청: {}", file.getOriginalFilename());

        // 1. OCR 호출
        JSONObject ocrResponse = ocrService.processOCR(file);

        JSONArray fields = ocrResponse
                .getJSONArray("images")
                .getJSONObject(0)
                .getJSONArray("fields");

        // 2. OCR → Token
        List<OCRToken> tokens = OCRTokenMapper.from(fields);
        log.debug("[OCR] Token 변환 완료: {} tokens", tokens.size());

        // 3. Token → 필드 추출
        Map<FieldType, ExtractedField<?>> extractedFields =
                ocrReceiptParser.parse(tokens);

        log.debug("[OCR] 추출 필드 수: {}", extractedFields.size());

        // 4. Confidence 기반 Draft 생성
        ReceiptDraft draft = receiptAssembler.assemble(extractedFields);

        log.info("OCR ReceiptDraft 생성 완료: confirmed={}, uncertain={}",
                draft.getConfirmed().size(),
                draft.getUncertain().size()
        );

        ReceiptDraftResponse response =
                ReceiptDraftResponseMapper.from(draft);

        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "OCR 분석 성공",
                        response
                )
        );

    }
}

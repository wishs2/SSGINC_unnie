package com.ssginc.unnie.review.controller;

import com.ssginc.unnie.common.util.ResponseDto;
import com.ssginc.unnie.common.util.validation.ReceiptSaveValidator;
import com.ssginc.unnie.common.util.validation.Validator;
import com.ssginc.unnie.review.dto.ReceiptRequest;
import com.ssginc.unnie.review.dto.ReceiptResponse;
import com.ssginc.unnie.review.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receipt")
@RequiredArgsConstructor
@Slf4j
public class ReceiptController {


    private final ReceiptService receiptService;
    private final ReceiptSaveValidator receiptSaveValidator;

    /**
     * 검증을 다 거친 OCR 데이터를 DB에 저장
     *
     * @param receiptRequest
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<ResponseDto<ReceiptResponse>> saveReceipt
    (@RequestBody ReceiptRequest receiptRequest) {
        log.info("영수증 데이터: {}", receiptRequest); //데이터가 잘 들어오는지 로그 확인

        ReceiptResponse savedReceipt = receiptService.saveReceipt(receiptRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(HttpStatus.CREATED.value(), "저장 성공", savedReceipt));
    }


    /**
     * 특정 영수증 조회
     */
    @GetMapping("/{receiptId}")
    public ResponseEntity<ResponseDto<ReceiptResponse>> getReceipt(@PathVariable long receiptId) {
        log.info("영수증 조회 요청 (ID: {})", receiptId);
        ReceiptResponse receipt = receiptService.getReceiptById(receiptId);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "영수증 조회 성공", receipt));
    }
}

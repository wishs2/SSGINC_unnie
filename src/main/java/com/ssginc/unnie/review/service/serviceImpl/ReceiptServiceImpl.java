package com.ssginc.unnie.review.service.serviceImpl;

import com.ssginc.unnie.common.exception.UnnieReviewException;
import com.ssginc.unnie.common.util.ErrorCode;
import com.ssginc.unnie.review.dto.ReceiptRequest;
import com.ssginc.unnie.review.dto.ReceiptResponse;
import com.ssginc.unnie.review.mapper.ReceiptMapper;
import com.ssginc.unnie.review.service.ReceiptService;
import com.ssginc.unnie.common.util.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptMapper receiptMapper;
    private final Validator<ReceiptRequest> receiptSaveValidator;

    /**
     * OCR 데이터를 기반으로 영수증과 품목 데이터를 DB에 저장 (DTO를 통해 처리)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReceiptResponse saveReceipt(ReceiptRequest receiptRequest) {

        // 1. 데이터 검증 (주입받은 Validator 활용)
        if (!receiptSaveValidator.validate(receiptRequest)) {
            throw new UnnieReviewException(ErrorCode.INVALID_RECEIPT);
        }

        // 2. shop 이름 정규화 (전처리)
        String normalizedShopName = receiptRequest.getReceiptShopName().trim().replaceAll("\\s+", " ");
        receiptRequest.setReceiptShopName(normalizedShopName);

        // 3. shopID 매핑 로직 개선
        Integer shopId = receiptMapper.findShopIdByName(normalizedShopName);
        if (shopId != null && shopId > 0) {
            receiptRequest.setReceiptShopId(shopId);
        } else {
            log.warn("해당 이름의 업체를 찾을 수 없음: {}", normalizedShopName);
        }

        // 4. 영수증 저장
        try {
            receiptMapper.insertReceipt(receiptRequest);
        } catch (DataIntegrityViolationException e) {
            log.warn("중복 영수증 저장 시도", e);
            throw new UnnieReviewException(ErrorCode.DUPLICATE_RECEIPT);
        } catch (Exception e) {
            log.error("영수증 저장 실패", e);
            throw new UnnieReviewException(ErrorCode.RECEIPT_SAVE_FAILED);
        }

        // 5. 응답 생성
        return ReceiptResponse.from(receiptRequest);
    }


    /**
     * 특정 영수증을 조회하여 응답 DTO로 변환
     */
    @Override
    public ReceiptResponse getReceiptById(long receiptId) {
        ReceiptResponse receiptResponse = receiptMapper.findReceiptById(receiptId);
        if (receiptResponse == null) {
            log.error("영수증을 찾을 수 없음 (ID: {})", receiptId);
            throw new UnnieReviewException(ErrorCode.RECEIPT_NOT_FOUND);
        }

        return new ReceiptResponse(receiptResponse.getReceiptId(), receiptResponse.getReceiptDate(), receiptResponse.getReceiptAmount(), receiptResponse.getReceiptBusinessNumber(), receiptResponse.getReceiptApprovalNumber(), receiptResponse.getReceiptShopName());
    }


    @Override
    public long getShopIdByReceiptId(long receiptId) {
        log.info("리뷰 EVENT shop: {}", receiptId);
        return receiptMapper.getShopIdByReceiptId(receiptId);
    }
}

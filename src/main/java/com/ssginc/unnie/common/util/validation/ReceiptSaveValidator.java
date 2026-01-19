package com.ssginc.unnie.common.util.validation;

import com.ssginc.unnie.review.dto.ReceiptRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ReceiptSaveValidator implements Validator<ReceiptRequest> {

    @Override
    public boolean validate(ReceiptRequest receiptRequest) {
        if (receiptRequest == null) return false;

        return receiptRequest.getReceiptDate() != null
                && receiptRequest.getReceiptAmount() > 0
                && receiptRequest.getReceiptShopName() != null
                && !receiptRequest.getReceiptShopName().isBlank();
    }
}
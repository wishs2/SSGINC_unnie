package com.ssginc.unnie.review.ocr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReceiptDraftResponse {

    private List<ConfirmedFieldResponse> confirmed;
    private List<UncertainFieldResponse> uncertain;
}

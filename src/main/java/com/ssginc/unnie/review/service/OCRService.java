package com.ssginc.unnie.review.service;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * OCR API 호출
 */
public interface OCRService {
    JSONObject processOCR(MultipartFile file);
}

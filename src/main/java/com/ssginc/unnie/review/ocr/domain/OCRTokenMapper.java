package com.ssginc.unnie.review.ocr.domain;

import com.ssginc.unnie.review.ocr.domain.OCRToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OCRTokenMapper {

    public static List<OCRToken> from(JSONArray fields) {
        List<OCRToken> tokens = new ArrayList<>();

        for (int i = 0; i < fields.length(); i++) {
            JSONObject field = fields.getJSONObject(i);

            String text = field.optString("inferText", "");
            float confidence = (float) field.optDouble("inferConfidence", 0.0);
            int lineIndex = field.optInt("lineIndex", -1);

            JSONObject boundingBox = field.optJSONObject("boundingPoly");
            JSONArray vertices = boundingBox.optJSONArray("vertices");

            float xMin = vertices.getJSONObject(0).optFloat("x");
            float yMin = vertices.getJSONObject(0).optFloat("y");
            float xMax = vertices.getJSONObject(2).optFloat("x");
            float yMax = vertices.getJSONObject(2).optFloat("y");

            tokens.add(new OCRToken(
                    text, confidence, lineIndex,
                    xMin, yMin, xMax, yMax
            ));
        }

        return tokens;
    }
}

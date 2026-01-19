package com.ssginc.unnie.reservation.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservationResponse {
    private Long reservationId;
    private Long memberId;
    private Long shopId;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}

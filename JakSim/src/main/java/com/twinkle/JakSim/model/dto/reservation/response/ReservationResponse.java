package com.twinkle.JakSim.model.dto.reservation.response;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private int rIdx;

    private int pIdx;

    private String tStartT;

    private String tEndT;

    private int tPeople;

    private int tType;
}

package com.twinkle.JakSim.model.dto.reservation.response;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class IsReservationDto {

    private int rIdx;

    private int tIdx;

    private int pIdx;

    private String userId;

    private LocalDate rCDt;
}

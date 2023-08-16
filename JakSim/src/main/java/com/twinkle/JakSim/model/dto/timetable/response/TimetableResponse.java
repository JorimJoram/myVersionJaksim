package com.twinkle.JakSim.model.dto.timetable.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimetableResponse {

    private int tIdx;

    private String userId;

    private LocalDate tDate;

    private int tType;

    private LocalTime tStartT;

    private LocalTime tEndT;

    private int tPeople;
}

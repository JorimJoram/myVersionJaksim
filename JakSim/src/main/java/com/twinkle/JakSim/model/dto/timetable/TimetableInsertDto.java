package com.twinkle.JakSim.model.dto.timetable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimetableInsertDto {

    private int tIdx;

    private String userId;

    private String tDate;

    private String tStartT;

    private String tEndT;

    private int tPeople;

    private int tType;

}

package com.twinkle.JakSim.model.dto.timetable.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
public class TimetableRequest {

    @NotBlank
    @Size(max = 30)
    private String trainerId;

    @NotBlank
    private String dt;

    @NotNull
    private int type;
}

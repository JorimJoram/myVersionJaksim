package com.twinkle.JakSim.model.dto.reservation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class IsReservationRequest {

//    @NotBlank
    private LocalDate dt;

    @NotBlank
    private String trainerId;
}

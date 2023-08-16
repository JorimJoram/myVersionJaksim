package com.twinkle.JakSim.model.dto.reservation.request;

import lombok.*;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

//    @NotNull
//    @Max(8)
    private int p_idx;

//    @NotNull
//    @Max(8)
    private int t_idx;

//    @NotBlank
    private String trainerId;

    private int ptCnt;

//    @NotBlank
    private LocalDate date;
}
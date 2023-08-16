package com.twinkle.JakSim.model.dto.product.response;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ValidPtResponse {

    private String trainerId;

    private String trainerName;

    private int pIdx;

    public int tType;

    private int pPtCnt;
}
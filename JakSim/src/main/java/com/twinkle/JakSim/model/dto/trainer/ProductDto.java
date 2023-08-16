package com.twinkle.JakSim.model.dto.trainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int ptId;
    private int trainerId;
    private String tid;
    private int ptTimes;
    private int ptPrice;
    private int ptType; // 0:개인 1:단체
    private String ptTitle;
    private int ptPeriod; //만료를 위한 PT 기간
}

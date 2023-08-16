package com.twinkle.JakSim.model.dto.payment.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@Getter
public class PaymentRequest {

    @NotEmpty
    @Size(max = 100)
    private String ptTitle;

    @NotNull
    @Max(100)
    private int tpIdx;

    @NotNull
    private int ptPrice;

    @NotNull
    private int ptTimes;

    @NotNull
    private int ptPeriod;
}

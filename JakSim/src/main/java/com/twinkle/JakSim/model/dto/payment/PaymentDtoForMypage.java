package com.twinkle.JakSim.model.dto.payment;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDtoForMypage {
    private int idx;
    private String tid;
    private LocalDate p_c_dt;
    private LocalDate p_f_dt;
    private int period;
    private int pt_cnt;
    private String title;
    private int type;
    private int total_cnt;
    private int price;
}

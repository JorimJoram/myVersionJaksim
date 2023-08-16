package com.twinkle.JakSim.model.dto.account;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserStat {
    private String id;
    private int gender;
    private LocalDate c_dt;
    private int user_role;
    private int amount;
}

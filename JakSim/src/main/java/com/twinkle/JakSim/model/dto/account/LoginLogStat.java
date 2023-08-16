package com.twinkle.JakSim.model.dto.account;

import lombok.Data;

import java.time.LocalDate;

/**
 * 필요한 정보가 있을 때마다 더 추가하시면 됩니다.
 * 꾸준히 새로 dto 만드는게 싫어서리
 */
@Data
public class LoginLogStat {
    private String user_id;
    private LocalDate date;
    private int amount;
}

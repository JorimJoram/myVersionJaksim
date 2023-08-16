package com.twinkle.JakSim.model.dto.account;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserImage {
    private int id;
    private String user_id;
    private String path;
    private LocalDateTime dt;
}

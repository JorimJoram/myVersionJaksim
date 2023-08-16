package com.twinkle.JakSim.model.dto.review;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDto {
    private int idx;
    private String userId;
    private String tid;
    private String content;
    private int star;
    private String c_dt;
    private String m_dt;

    private String trainerName;
    private double avgRstar;
    private int reviewCnt;
}

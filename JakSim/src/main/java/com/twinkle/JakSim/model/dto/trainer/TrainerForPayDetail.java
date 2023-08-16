package com.twinkle.JakSim.model.dto.trainer;

import lombok.Data;

@Data
public class TrainerForPayDetail {
    private int ut_idx;
    private String userId;
    private String userName;
    private int gender;
    private String insta;
    private String gym;
    private int expert1;
    private int expert2;
    private String imagePath;
}

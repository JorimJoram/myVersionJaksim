package com.twinkle.JakSim.model.dto.trainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerPageDto {

    private int trainerId;

    private String userName;
    private String userId;

    private String insta;
    private String introduce;
    private String gym;
    private int expert1;
    private int expert2;
    private String address;
    private String profile;

    private int ptTimes;
    private int ptPrice;
    private String ptTitle;

    private String careerContent;

    private String certName;

    private String imagePath;

}

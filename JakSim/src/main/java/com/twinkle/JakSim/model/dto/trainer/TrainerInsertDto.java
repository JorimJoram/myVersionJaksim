package com.twinkle.JakSim.model.dto.trainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerInsertDto {

    private int trainerId;
    private String insta;
    private String introduce;
    private String gym;
    private int expert1;
    private int expert2;
    private String address;
    private String profileImg;

    private int[] ptId;
    private int[] ptTimes;
    private int[] ptPrice;
    private int[] ptType; // 0:개인 1:단체
    private String[] ptTitle;
    private int[] ptPeriod; //만료를 위한 PT 기간

    private int[] careerId;
    private String[] careerContent;

    private int[] certId;
    private String[] certName;

    private int[] imageId;
    private String[] imagePath;

}

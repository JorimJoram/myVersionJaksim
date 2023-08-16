package com.twinkle.JakSim.model.dto.trainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDto {
    private int trainerId;
    private String userId;
    private String insta;
    private String introduce;
    private String gym;
    private int expert1;
    private int expert2;
}

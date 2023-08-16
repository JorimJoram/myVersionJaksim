package com.twinkle.JakSim.model.dto.trainer.response;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDetailResponse {
    private String userId;

    private String userName;

    private int gender;

    private String insta;

    private String gym;

    private int expert1;

    private int expert2;

    private String imagePath;
}

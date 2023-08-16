package com.twinkle.JakSim.model.dto.trainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PtUserDto {

    private String userName;
    private String userId;
    private int gender;
    private String tel;

    private int ptType;
    private int ptCnt;

}

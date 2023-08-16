package com.twinkle.JakSim.model.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String pw;
    private String name;
    private String email;
    private int gender;
    private String tel;
    private String birth;
    private String c_dt;
    private String m_dt;
    private int role;
}

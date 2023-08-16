package com.twinkle.JakSim.model.dto.inbody;

import lombok.Data;

@Data
public class InbodyDto {
    private int id;
    private String user_id;
    private double height;
    private double weight;
    private double score;
    private double fat;
    private double muscle;
    private String c_dt;
}

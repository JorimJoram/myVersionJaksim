package com.twinkle.JakSim.model.dto.qna;

import lombok.Data;

@Data
public class QnADto {
    private int idx;
    private int parent_idx;
    private String user_id;
    private String content;
    private String c_dt;
    private boolean adminCheck;
}

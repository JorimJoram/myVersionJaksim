package com.twinkle.JakSim.model.dao.trainer;

import com.twinkle.JakSim.model.dto.trainer.response.TrainerDetailResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainerDetailRowMapper implements RowMapper<TrainerDetailResponse> {

    public TrainerDetailResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        TrainerDetailResponse trainerDetailResponse = new TrainerDetailResponse(rs.getString("USER_ID"),
                                                                    rs.getString("USER_NAME"),
                                                                    rs.getInt("USER_GENDER"),
                                                                    rs.getString("UT_INSTA"),
                                                                    rs.getString("UT_GYM"),
                                                                    rs.getInt("UT_EXPERT_1"),
                                                                    rs.getInt("UT_EXPERT_2"),
                                                                    rs.getString("TI_PATH"));


        return trainerDetailResponse;
    }
}

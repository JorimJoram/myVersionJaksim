package com.twinkle.JakSim.model.dao.trainer;

import com.twinkle.JakSim.model.dto.trainer.TrainerSearchDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainerSearchRowMapper implements RowMapper<TrainerSearchDto> {
    @Override
    public TrainerSearchDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        TrainerSearchDto trainerSearchDto = new TrainerSearchDto();

        trainerSearchDto.setTrainerId(rs.getInt("UT_IDX"));
        trainerSearchDto.setUserName(rs.getString("USER_NAME"));
        trainerSearchDto.setUserId(rs.getString("USER_ID"));
        trainerSearchDto.setAddress(rs.getString("UT_ADDRESS"));
        trainerSearchDto.setProfile(rs.getString("UT_PROFILE_IMG"));

        trainerSearchDto.setGym(rs.getString("UT_GYM"));
        trainerSearchDto.setExpert1(rs.getInt("UT_EXPERT_1"));
        trainerSearchDto.setExpert2(rs.getInt("UT_EXPERT_2"));

        trainerSearchDto.setCertName(rs.getString("TC_NAME"));
        trainerSearchDto.setAvgRstar(rs.getDouble("R_AVG_STAR"));

        return trainerSearchDto;
    }
}

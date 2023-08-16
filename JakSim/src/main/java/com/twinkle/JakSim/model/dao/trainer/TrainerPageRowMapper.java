package com.twinkle.JakSim.model.dao.trainer;

import com.twinkle.JakSim.model.dto.trainer.TrainerPageDto;
import com.twinkle.JakSim.model.dto.trainer.TrainerSearchDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class TrainerPageRowMapper implements RowMapper<TrainerPageDto> {
    @Override
    public TrainerPageDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        TrainerPageDto trainerPageDto = new TrainerPageDto();

        trainerPageDto.setTrainerId(rs.getInt("UT_IDX"));
        trainerPageDto.setUserName(rs.getString("USER_NAME"));
        trainerPageDto.setUserId(rs.getString("USER_ID"));
        trainerPageDto.setInsta(rs.getString("UT_INSTA"));
        trainerPageDto.setIntroduce(rs.getString("UT_INTRO"));
        trainerPageDto.setGym(rs.getString("UT_GYM"));
        trainerPageDto.setExpert1(rs.getInt("UT_EXPERT_1"));
        trainerPageDto.setExpert2(rs.getInt("UT_EXPERT_2"));
        trainerPageDto.setAddress(rs.getString("UT_ADDRESS"));
        trainerPageDto.setProfile(rs.getString("UT_PROFILE_IMG"));

        return trainerPageDto;
    }
}

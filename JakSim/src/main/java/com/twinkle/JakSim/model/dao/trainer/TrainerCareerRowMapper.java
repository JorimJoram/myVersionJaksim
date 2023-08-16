package com.twinkle.JakSim.model.dao.trainer;

import com.twinkle.JakSim.model.dto.trainer.TrainerCareerDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainerCareerRowMapper implements RowMapper<TrainerCareerDto>{

    @Override
    public TrainerCareerDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        TrainerCareerDto trainerCareerDto = new TrainerCareerDto();

        trainerCareerDto.setCareerId(rs.getInt("TCAR_IDX"));
        trainerCareerDto.setTrainerId(rs.getInt("UT_IDX"));
        trainerCareerDto.setCareerContent(rs.getString("TCAR_CONTENT"));

        return trainerCareerDto;
    }

}

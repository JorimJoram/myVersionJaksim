package com.twinkle.JakSim.model.dao.trainer;

import com.twinkle.JakSim.model.dto.trainer.TrainerCertDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainerCertRowMapper implements RowMapper<TrainerCertDto>{
    @Override
    public TrainerCertDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        TrainerCertDto trainerCertDto = new TrainerCertDto();

        trainerCertDto.setCertId(rs.getInt("TC_IDX"));
        trainerCertDto.setTrainerId(rs.getInt("UT_IDX"));
        trainerCertDto.setCertName(rs.getString("TC_NAME"));

        return trainerCertDto;
    }
}

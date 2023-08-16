package com.twinkle.JakSim.model.dao.trainer;

import com.twinkle.JakSim.model.dto.trainer.TrainerImageDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainerImageRowMapper implements RowMapper<TrainerImageDto> {

    @Override
    public TrainerImageDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        TrainerImageDto trainerImageDto = new TrainerImageDto();

        trainerImageDto.setImageId(rs.getInt("TI_IDX"));
        trainerImageDto.setTrainerId(rs.getInt("UT_IDX"));
        trainerImageDto.setImagePath(rs.getString("TI_PATH"));

        return trainerImageDto;
    }
}

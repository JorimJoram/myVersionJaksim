package com.twinkle.JakSim.model.dao.inbody;

import com.twinkle.JakSim.model.dto.inbody.InbodyDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InbodyRowMapper implements RowMapper<InbodyDto> {
    @Override
    public InbodyDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        InbodyDto inbodyDto = new InbodyDto();

        inbodyDto.setId(rs.getInt("IN_ID"));
        inbodyDto.setUser_id(rs.getString("USER_ID"));
        inbodyDto.setHeight(rs.getDouble("IN_HEIGHT"));
        inbodyDto.setWeight(rs.getDouble("IN_WEIGHT"));
        inbodyDto.setScore(rs.getDouble("IN_SCORE"));
        inbodyDto.setFat(rs.getDouble("IN_FAT"));
        inbodyDto.setMuscle(rs.getDouble("IN_MUSCLE"));
        inbodyDto.setC_dt(rs.getString("IN_C_DT"));

        return inbodyDto;
    }
}

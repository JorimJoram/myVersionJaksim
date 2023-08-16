package com.twinkle.JakSim.model.dao.trainer;

import com.twinkle.JakSim.model.dto.trainer.PtUserDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PtUserRowMapper implements RowMapper<PtUserDto> {
    @Override
    public PtUserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        PtUserDto ptUserDto = new PtUserDto();

        ptUserDto.setUserName(rs.getString("USER_NAME"));
        ptUserDto.setUserId(rs.getString("USER_ID"));
        ptUserDto.setGender(rs.getInt("USER_GENDER"));
        ptUserDto.setTel(rs.getString("USER_TEL"));
        ptUserDto.setPtType(rs.getInt("TP_TYPE"));
        ptUserDto.setPtCnt(rs.getInt("P_PT_CNT"));

        return ptUserDto;
    }
}

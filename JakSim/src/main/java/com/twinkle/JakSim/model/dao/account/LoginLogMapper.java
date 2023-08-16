package com.twinkle.JakSim.model.dao.account;

import com.twinkle.JakSim.model.dto.account.LoginLogDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LoginLogMapper implements RowMapper<LoginLogDto> {
    @Override
    public LoginLogDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        LoginLogDto logDto = new LoginLogDto();

        logDto.setIp(rs.getString("L_IP"));
        logDto.setUser_id(rs.getString("USER_ID"));
        logDto.setId(rs.getInt("L_ID"));
        logDto.setDt(rs.getString("L_DT"));

        return logDto;
    }
}

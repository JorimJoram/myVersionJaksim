package com.twinkle.JakSim.model.dao.reservation;

import com.twinkle.JakSim.model.dto.reservation.response.IsReservationDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IsReservationRowMapper implements RowMapper<IsReservationDto> {
    public IsReservationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        IsReservationDto isReservationDto = IsReservationDto.builder()
                                                            .rIdx(rs.getInt("R_IDX"))
                                                            .tIdx(rs.getInt("T_IDX"))
                                                            .pIdx(rs.getInt("P_IDX"))
                                                            .userId(rs.getString("USER_ID"))
                                                            .rCDt(rs.getDate("R_C_DT").toLocalDate())
                                                            .build();

        return isReservationDto;
    }
}
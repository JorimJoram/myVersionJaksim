package com.twinkle.JakSim.model.dao.reservation;

import com.twinkle.JakSim.model.dto.reservation.response.IsReservationDto;
import com.twinkle.JakSim.model.dto.reservation.response.ReservationResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ReservationRowMapper implements RowMapper<ReservationResponse> {
    public ReservationResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        ReservationResponse reservationResponse = ReservationResponse.builder()
                                            .rIdx(rs.getInt("R_IDX"))
                                            .pIdx((rs.getInt("P_IDX")))
                                            .tType(rs.getInt("T_TYPE"))
                                            .tStartT(rs.getTime("T_START_T").toLocalTime().format(formatter))
                                            .tEndT(rs.getTime("T_END_T").toLocalTime().format(formatter))
                                            .tPeople(rs.getInt("T_PEOPLE"))
                                            .build();

        return reservationResponse;
    }
}
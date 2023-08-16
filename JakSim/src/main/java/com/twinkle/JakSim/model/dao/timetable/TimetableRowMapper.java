package com.twinkle.JakSim.model.dao.timetable;

import com.twinkle.JakSim.model.dto.timetable.response.TimetableResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimetableRowMapper implements RowMapper<TimetableResponse> {
    public TimetableResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        TimetableResponse timetableDto = TimetableResponse.builder()
                                                            .tIdx(rs.getInt("T_IDX"))
                                                            .userId(rs.getString("USER_ID"))
                                                            .tDate(rs.getDate("T_DATE").toLocalDate())
                                                            .tType(rs.getInt("T_TYPE"))
                                                            .tStartT(rs.getTime("T_START_T").toLocalTime())
                                                            .tEndT(rs.getTime("T_END_T").toLocalTime())
                                                            .tPeople(rs.getInt("T_PEOPLE"))
                                                            .build();
        return timetableDto;
    }
}

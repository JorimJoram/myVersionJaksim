package com.twinkle.JakSim.model.dao.timetable;

import com.twinkle.JakSim.model.dto.timetable.response.TIdxDo;
import com.twinkle.JakSim.model.dto.timetable.response.TimetableResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TIdxRowMapper implements RowMapper<TIdxDo> {
    public TIdxDo mapRow(ResultSet rs, int rowNum) throws SQLException {
        TIdxDo tIdxDo = TIdxDo.builder()
                .tIdx(rs.getInt("T_IDX"))
                .build();

        return tIdxDo;
    }
}
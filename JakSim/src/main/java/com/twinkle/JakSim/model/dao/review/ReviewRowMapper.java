package com.twinkle.JakSim.model.dao.review;

import com.twinkle.JakSim.model.dto.review.ReviewDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ReviewRowMapper implements RowMapper<ReviewDto> {
    @Override
    public ReviewDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReviewDto ReviewDto = new ReviewDto();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm:ss"));

        ReviewDto.setIdx(rs.getInt("R_IDX"));
        ReviewDto.setUserId(rs.getString("USER_ID"));
        ReviewDto.setTid(rs.getString("TID"));
        ReviewDto.setContent(rs.getString("R_CONTENT"));
        ReviewDto.setStar(rs.getInt("R_STAR"));
        ReviewDto.setC_dt(rs.getTimestamp("R_C_DT").toLocalDateTime().format(formatter));
        if(rs.getString("R_M_DT") != null) {
            ReviewDto.setC_dt(rs.getTimestamp("R_M_DT").toLocalDateTime().format(formatter));
        }

        return ReviewDto;
    }
}

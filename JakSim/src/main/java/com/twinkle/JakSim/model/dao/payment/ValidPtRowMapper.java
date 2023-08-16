package com.twinkle.JakSim.model.dao.payment;

import com.twinkle.JakSim.model.dto.product.response.ValidPtResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidPtRowMapper implements RowMapper<ValidPtResponse> {

    public ValidPtResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        ValidPtResponse validPt = ValidPtResponse.builder()
                                        .trainerId(rs.getString("USER_ID"))
                                        .trainerName(rs.getString("USER_NAME"))
                                        .pIdx(rs.getInt("P_IDX"))
                                        .tType(rs.getInt("TP_TYPE"))
                                        .pPtCnt(rs.getInt("P_PT_CNT"))
                                        .build();

        return validPt;
    }
}

package com.twinkle.JakSim.model.dao.payment;

import com.twinkle.JakSim.model.dto.payment.response.PaymentDo;
import com.twinkle.JakSim.model.dto.payment.response.PtCntDo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ptCntDoRosMapper implements RowMapper<PtCntDo> {
    @Override
    public PtCntDo mapRow(ResultSet rs, int rowNum) throws SQLException {
        PtCntDo ptCntDo = new PtCntDo();

        ptCntDo.setPtCnt(rs.getInt("P_PT_CNT"));

        return ptCntDo;
    }
}
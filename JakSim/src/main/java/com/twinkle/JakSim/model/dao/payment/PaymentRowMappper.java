package com.twinkle.JakSim.model.dao.payment;

import com.twinkle.JakSim.model.dto.payment.response.PtTicketResponse;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentRowMappper implements RowMapper<PtTicketResponse> {

    public PtTicketResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        PtTicketResponse paymentDto = PtTicketResponse.builder()
                .pPtCnt(rs.getInt("P_PT_CNT"))
                .build();

        return paymentDto;
    }
}

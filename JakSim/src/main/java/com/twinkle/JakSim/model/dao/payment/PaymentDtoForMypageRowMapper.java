package com.twinkle.JakSim.model.dao.payment;

import com.twinkle.JakSim.model.dto.payment.PaymentDtoForMypage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDtoForMypageRowMapper implements RowMapper<PaymentDtoForMypage> {
    @Override
    public PaymentDtoForMypage mapRow(ResultSet rs, int rowNum) throws SQLException {
        PaymentDtoForMypage paymentDtoForMypage = new PaymentDtoForMypage();

        paymentDtoForMypage.setIdx(rs.getInt("P_IDX"));
        paymentDtoForMypage.setTid(rs.getString("TID"));
        paymentDtoForMypage.setP_c_dt(rs.getDate("P_A_DT").toLocalDate());
        paymentDtoForMypage.setPeriod(rs.getInt("P_PT_PERIOD"));
        paymentDtoForMypage.setPt_cnt(rs.getInt("P_PT_CNT"));
        paymentDtoForMypage.setTitle(rs.getString("TP_TITLE"));
        paymentDtoForMypage.setType(rs.getInt("TP_TYPE"));
        paymentDtoForMypage.setTotal_cnt(rs.getInt("TP_TIMES"));
        paymentDtoForMypage.setPrice(rs.getInt("TP_PRICE"));

        paymentDtoForMypage.setP_f_dt(
                paymentDtoForMypage.getP_c_dt().plusDays(paymentDtoForMypage.getPeriod())
        );

        return paymentDtoForMypage;
    }
}

package com.twinkle.JakSim.model.dao.reservation;

import com.twinkle.JakSim.model.dto.reservation.response.MyMember;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyMemberRowMapper implements RowMapper<MyMember> {
    public MyMember mapRow(ResultSet rs, int rowNum) throws SQLException {
        MyMember myMember = MyMember.builder()
                                    .id(rs.getString("USER_ID"))
                                    .name(rs.getString("USER_NAME"))
                                    .gender(rs.getInt("USER_GENDER"))
                                    .build();

        return myMember;
    }
}
package com.twinkle.JakSim.model.dao.qna;

import com.twinkle.JakSim.model.dto.qna.QnADto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QnADao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<QnADto> getMyQuestions(String username) {
        final String sql = "SELECT * FROM QNA WHERE USER_ID = ?";
        List<QnADto> qnAList = new ArrayList<>();
        try{
            qnAList = jdbcTemplate.query(sql, new RowMapper<QnADto>() {
                @Override
                public QnADto mapRow(ResultSet rs, int rowNum) throws SQLException {
                    QnADto qna = new QnADto();
                    qna.setIdx(rs.getInt("Q_IDX"));
                    qna.setUser_id(rs.getString("USER_ID"));
                    qna.setParent_idx(rs.getInt("PQ_IDX"));
                    qna.setContent(rs.getString("Q_CONTENT"));
                    qna.setC_dt(rs.getString("Q_C_DT"));
                    return qna;
                }
            }, username);
        }catch (EmptyResultDataAccessException e){
            System.out.println(e.getMessage());
        }
        return qnAList;
    }
}

package com.twinkle.JakSim.model.dao.inbody;

import com.twinkle.JakSim.model.dto.inbody.InbodyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InbodyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<InbodyDto> getInbodiesAsc(String username) {
        String sql = "SELECT * FROM INBODY WHERE USER_ID = ? ORDER BY IN_C_DT ASC";
        List<InbodyDto> inbodyList = new ArrayList<>();
        try{
            inbodyList = jdbcTemplate.query(sql, new InbodyRowMapper() ,username);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return inbodyList;
    }

    /**
     * 페이지 단위로 받아오기
     * @param username 사용자 계정
     * @param pageNum 페이지 번호 -> 1, 2, ... 등의 페이지 번호
     * @param pageSize 페이지 사이즈 -> 페이지 안에 몇 개를 보여줄 것인지
     * @return List로 반환됩니다.
     */
    public List<InbodyDto> getInbodiesByPages(String username, int pageNum, int pageSize){
        int offset = (pageNum - 1) * pageSize;
        String sql = "SELECT * FROM INBODY WHERE USER_ID = ? ORDER BY IN_ID DESC LIMIT ? OFFSET ?";
        List<InbodyDto> result = new ArrayList<>();

        try{
            result = jdbcTemplate.query(sql, new InbodyRowMapper(),username, pageSize, offset);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return result;
    }

    public int getTotalPage(String username, int pageSize){
        String sql = "SELECT COUNT(*) FROM INBODY WHERE USER_ID=?";
        int pageNum = 0;

        try{
            pageNum = jdbcTemplate.queryForObject(sql, Integer.class, username);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return (pageNum /= pageSize) + 1;
    }

    public int create(String username, InbodyDto data) {
        String sql = "INSERT INTO INBODY(USER_ID, IN_HEIGHT, IN_WEIGHT, IN_SCORE, IN_FAT, IN_MUSCLE) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        int result = -1;

        try{
            result = jdbcTemplate.update(sql, username,
                    data.getHeight(), data.getWeight(), data.getScore(), data.getFat(), data.getMuscle());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return result;
    }

    public int delete(int id) {
        String sql = "DELETE FROM INBODY WHERE IN_ID = ?";
        int result = -1;

        try{
            result = jdbcTemplate.update(sql, id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return result;
    }
}

package com.twinkle.JakSim.model.dao.account;

import com.twinkle.JakSim.model.dto.account.LoginLogDto;
import com.twinkle.JakSim.model.dto.account.LoginLogStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class LoginLogDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public int create(LoginLogDto logDto) {
        String sql = "INSERT INTO LOGIN_LOG(USER_ID, L_IP) " +
                "VALUES(?, ?)";
        int result;

        try{
            result = jdbcTemplate.update(sql, logDto.getUser_id(), logDto.getIp());
        }catch (EmptyResultDataAccessException e){
            result = -1;
        }
        return result;
    }

    public LoginLogDto findByUsernameRecent(String userId) {
        String sql = "SELECT * FROM LOGIN_LOG WHERE USER_ID = ? ORDER BY L_DT desc LIMIT 1";

        LoginLogDto logDto = new LoginLogDto();

        try{
            logDto = jdbcTemplate.queryForObject(sql, new LoginLogMapper(), userId);
        }catch (EmptyResultDataAccessException e){
            System.out.println("로그인 기록이 없다는데?");
        }

        return logDto;
    }

    public List<LoginLogDto> findByUsername(String username, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        String sql = "SELECT * FROM LOGIN_LOG WHERE USER_ID = ? ORDER BY L_DT desc LIMIT ? OFFSET ?";
        List<LoginLogDto> resultList = new ArrayList<>();

        try{
            resultList = jdbcTemplate.query(sql, new LoginLogMapper(), username, pageSize, offset);
        }catch (EmptyResultDataAccessException e){
            System.out.println("로그인 기록이 없다는데?");
        }

        return resultList;
    }

    public int getAllAccess() {
        String sql = "SELECT COUNT(*) FROM LOGIN_LOG";
        int result;
        try{
            result = Objects.requireNonNull(jdbcTemplate.queryForObject(sql, Integer.class));
        }catch(EmptyResultDataAccessException e){
            result = 0;
        }

        return result;
    }

    /**
     * GPT 피셜, count(*)가 length() 사용하는 것보다 더 효과적이라고 합니다.
     */
    public List<LoginLogStat> getAmountDate(String start, String end) {
        String sql = "SELECT DATE(L_DT) as DATE, COUNT(*) as AMOUNT FROM LOGIN_LOG " +
                "WHERE DATE(L_DT) BETWEEN ? AND ? " +
                "GROUP BY DATE(L_DT) " +
                "ORDER BY DATE(L_DT) ASC";
        List<LoginLogStat> logList = new ArrayList<>();

        try{
            logList = jdbcTemplate.query(sql, new RowMapper<LoginLogStat>() {
                @Override
                public LoginLogStat mapRow(ResultSet rs, int rowNum) throws SQLException {
                    LoginLogStat stat = new LoginLogStat();
                    stat.setAmount(rs.getInt("AMOUNT"));
                    stat.setDate(rs.getDate("DATE").toLocalDate());
                    return stat;
                }
            }, start, end);
        }catch (EmptyResultDataAccessException e){
            System.out.println(e.getMessage());
        }

        return logList;
    }

    public List<LoginLogStat> getAmountSingleUser(String username, String start, String end) {
        String sql = "SELECT USER_ID, DATE(L_DT) as DATE, COUNT(*) as AMOUNT FROM LOGIN_LOG " +
                "WHERE USER_ID = ?  AND " +
                "DATE(L_DT) BETWEEN ? AND ? " +
                "GROUP BY DATE(L_DT) " +
                "ORDER BY DATE(L_DT) DESC";
        List<LoginLogStat> logList = new ArrayList<>();

        try{
            logList = jdbcTemplate.query(sql, new RowMapper<LoginLogStat>() {
                @Override
                public LoginLogStat mapRow(ResultSet rs, int rowNum) throws SQLException {
                    LoginLogStat stat = new LoginLogStat();
                    stat.setUser_id(rs.getString("USER_ID"));
                    stat.setAmount(rs.getInt("AMOUNT"));
                    stat.setDate(rs.getDate("DATE").toLocalDate());
                    return stat;
                }
            }, username, start, end);
        }catch (EmptyResultDataAccessException e){
            System.out.println(e.getMessage());
        }

        return logList;
    }

    public List<LoginLogStat> getAmountGroupUser(String start, String end, boolean order) {
        String sql = "SELECT USER_ID, COUNT(*) AS AMOUNT FROM LOGIN_LOG " +
                "WHERE DATE(L_DT) BETWEEN ? AND ? " +
                "GROUP BY USER_ID ";
        if(order)
            sql += "ORDER BY AMOUNT DESC";
        List<LoginLogStat> logList = new ArrayList<>();

        try{
            logList = jdbcTemplate.query(sql, new RowMapper<LoginLogStat>() {
                @Override
                public LoginLogStat mapRow(ResultSet rs, int rowNum) throws SQLException {
                    LoginLogStat stat = new LoginLogStat();
                    stat.setUser_id(rs.getString("USER_ID"));
                    stat.setAmount(rs.getInt("AMOUNT"));
                    return stat;
                }
            }, start, end);
        }catch (EmptyResultDataAccessException e){
            System.out.println(e.getMessage());
        }
        return logList;
    }
}

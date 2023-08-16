package com.twinkle.JakSim.model.dao.account;

import com.twinkle.JakSim.model.dto.account.UserDto;
import com.twinkle.JakSim.model.dto.account.UserStat;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertMember(UserDto user){
        String sql = "INSERT INTO USER_INFO(user_id, user_pw, user_name, user_gender, user_tel, user_email, user_birth, user_c_dt, user_role) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, current_timestamp, ?)";
        int result = -1;
        try{
            result = jdbcTemplate.update(sql,
                    user.getId(), user.getPw(), user.getName(), user.getGender(), user.getTel(), user.getEmail(), user.getBirth(), user.getRole());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return result;
    }

    public Optional<UserDto> findByUserId(String userId){
        String sql = "SELECT * FROM USER_INFO WHERE USER_ID = ?";
        UserDto userDto = null;
        try{
            userDto = jdbcTemplate.queryForObject(sql, new UserRowMapper(), userId);
        } catch(EmptyResultDataAccessException e){
            System.out.println("데이터가 없다는디?");
        }

        return Optional.ofNullable(userDto);
    }

    public UserDto findByTel(String userTel){
        String sql = "SELECT * FROM USER_INFO WHERE USER_TEL = ?";
        UserDto userDto = null;

        try{
            userDto = jdbcTemplate.queryForObject(sql, new UserRowMapper(), userTel);
        }catch (EmptyResultDataAccessException e){
            System.out.println("데이터 없음");
        }

        return userDto;
    }

    public int updatePassword(String user_id, String pw) {
        String sql = "update user_info " +
                "set user_pw = ? " +
                "where user_id = ?";
        int result = -1;

        try{
            result = jdbcTemplate.update(sql, pw, user_id);
        }catch (Exception e){
            System.out.println("패스워드 수정 에러");
        }

        return result;
    }

    public int deleteUser(String id) {
        String sql = "DELETE FROM USER_INFO WHERE USER_ID = ?";
        int result = -1;

        try{
            result = jdbcTemplate.update(sql, id);
        }catch (Exception e){
            System.out.println("회원삭제 실패");
        }
        return result;
    }

    public Optional<UserDto> findByEmail(String email) {
        String sql = "SELECT * FROM USER_INFO WHERE USER_EMAIL = ?";
        UserDto user = null;
        try{
            user = jdbcTemplate.queryForObject(sql, new UserRowMapper() ,email);
        }catch (EmptyResultDataAccessException e){
            System.out.println("데이터가 존재하지 않습니다.");
        }
        return Optional.ofNullable(user);
    }

    public int updateEmail(String email, String username) {
        String sql = "UPDATE USER_INFO " +
                "SET USER_EMAIL = ? ," +
                "USER_M_DT = CURRENT_TIMESTAMP " +
                "WHERE USER_ID = ?";
        int result = -1;
        try{
            result = jdbcTemplate.update(sql, email, username);
        }catch (Exception e){
            System.out.println("이메일 수정 에러");
        }

        return result;
    }

    public int updateName(String name, String username) {
        String sql = "UPDATE USER_INFO " +
                "SET USER_NAME = ? ," +
                "USER_M_DT = CURRENT_TIMESTAMP " +
                "WHERE USER_ID = ?";
        int result = -1;
        try{
            result = jdbcTemplate.update(sql, name, username);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int updateTel(String tel, String username) {
        String sql = "UPDATE USER_INFO " +
                "SET USER_TEL = ? ," +
                "USER_M_DT = CURRENT_TIMESTAMP " +
                "WHERE USER_ID = ?";
        int result = -1;
        try{
            result = jdbcTemplate.update(sql, tel, username);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public List<UserStat> getAmountAccounts(String start, String end) {
        String sql = "SELECT COUNT(*) AS AMOUNT, DATE(USER_C_DT) AS DATE FROM USER_INFO " +
                "WHERE DATE(USER_C_DT) BETWEEN ? AND ? " +
                "GROUP BY DATE(USER_C_DT) " +
                "ORDER BY DATE(USER_C_DT)";
        List<UserStat> logList = new ArrayList<>();
        try{
            logList = jdbcTemplate.query(sql, new RowMapper<UserStat>() {
                @Override
                public UserStat mapRow(ResultSet rs, int rowNum) throws SQLException {
                    UserStat stat = new UserStat();
                    stat.setC_dt(rs.getDate("DATE").toLocalDate());
                    stat.setAmount(rs.getInt("AMOUNT"));
                    return stat;
                }
            }, start, end);
        }catch (EmptyResultDataAccessException e){
            System.out.println(e.getMessage());
        }

        return logList;
    }

    public List<UserStat> getAmountByRole() {
        String sql = "SELECT COUNT(*) AS AMOUNT, USER_ROLE FROM USER_INFO " +
                "GROUP BY USER_ROLE " +
                "ORDER BY DATE(USER_C_DT)";
        List<UserStat> logList = new ArrayList<>();
        try{
            logList = jdbcTemplate.query(sql, new RowMapper<UserStat>() {
                @Override
                public UserStat mapRow(ResultSet rs, int rowNum) throws SQLException {
                    UserStat stat = new UserStat();
                    stat.setUser_role(rs.getInt("USER_ROLE"));
                    stat.setAmount(rs.getInt("AMOUNT"));
                    return stat;
                }
            });
        }catch (EmptyResultDataAccessException e){
            System.out.println(e.getMessage());
        }
        return logList;
    }
}

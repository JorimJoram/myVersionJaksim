package com.twinkle.JakSim.model.dao.timetable;

import com.twinkle.JakSim.model.dto.timetable.response.TIdxDo;
import com.twinkle.JakSim.model.dto.timetable.response.TimetableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TimetableDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String sql;

    public Boolean isTimetable(int tIdx) {
        Boolean result = true;

        this.sql = "select * from timetable where t_idx = ?";

        try {
            jdbcTemplate.queryForObject(this.sql, new TimetableRowMapper(), tIdx);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("there isn't any timetables");
            System.out.println(e);

            result = false;
        } catch (Exception e){
            System.out.println(e);
        }

        return result;
    }

    public TIdxDo findTIdx(int rIdx) {
        this.sql = "select * from reservation where r_idx = ?";

       return jdbcTemplate.queryForObject(sql, new TIdxRowMapper(), rIdx);
    }

    public void increaseTPeople(int rIdx) {
        try {
            this.sql = "update timetable set t_people = t_people + 1 where t_idx = ?";

            jdbcTemplate.update(this.sql, findTIdx(rIdx).getTIdx());
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
        }
    }
    public void decreaseTPeople(int tIdx, int type) {
        try {
            this.sql = "update timetable set t_people = t_people - 1 where t_idx = ?";

            if(type == 0) {
                jdbcTemplate.update(this.sql, tIdx);
            } else {
                jdbcTemplate.update(this.sql, findTIdx(tIdx).getTIdx());
            }

        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
        }
    }

    public Optional<TimetableResponse> findMyTimetableRecent(String username) {
        String sql = "SELECT * FROM TIMETABLE WHERE USER_ID = ? LIMIT 1";
        TimetableResponse timetableDto = null;

        try{
            timetableDto = jdbcTemplate.queryForObject(sql, new TimetableRowMapper(), username);
        }catch (Exception e){
            System.out.println("Dao: " + e.getMessage());
        }

        return Optional.ofNullable(timetableDto);
    }

    public Optional<List<TimetableResponse>> findMyTimetableSoon(String username) {
        String sql = "select T_IDX, USER_ID, T_DATE, T_START_T, T_END_T, T_PEOPLE, T_TYPE, (T_DATE - CURRENT_DATE) AS DIFFDATE, (T_START_T - CURRENT_TIME) AS DIFFTIME " +
                "from timetable " +
                "where (t_date - current_date) >=0 and user_id = ? " +
                "order by DIFFDATE asc, DIFFTIME desc " +
                "LIMIT 5";
        List<TimetableResponse> timeList = new ArrayList<>();

        try{
            timeList = jdbcTemplate.query(sql, new TimetableRowMapper(), username);
        }catch (Exception e){
            System.out.println("Soon dao: " + e.getMessage());
        }

        return Optional.ofNullable(timeList);
    }
}

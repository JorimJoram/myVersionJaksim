package com.twinkle.JakSim.model.dao.scheduleList;


import com.twinkle.JakSim.model.dao.timetable.TimetableRowMapper;
import com.twinkle.JakSim.model.dto.timetable.response.TimetableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ScheduleListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String sql;

    public List<TimetableResponse> findSchedule(String userId, LocalDate firstDate, LocalDate lastDate, String trainerId) {
        List<TimetableResponse> timetableList = new ArrayList<>();

        this.sql = "select * from timetable as tt inner join reservation res on tt.t_idx = res.t_idx " +
                "where res.user_id = ? and tt.user_id = ? and t_date >= ? and t_date <= ?";

        try {
            timetableList = jdbcTemplate.query(this.sql, new TimetableRowMapper(),
                                                                            userId, trainerId, firstDate, lastDate);
        } catch (Exception e) {
            System.out.println(e);
        }

        return timetableList;
    }

    public List<TimetableResponse> findMySchedule(String trainerId, LocalDate firstDate, LocalDate lastDate) {
        List<TimetableResponse> timetableList = new ArrayList<>();

        this.sql = "select * from timetable as tt inner join reservation res on tt.t_idx = res.t_idx " +
                "where tt.user_id = ? and t_date >= ? and t_date <= ?";

        try {
            timetableList = jdbcTemplate.query(this.sql, new TimetableRowMapper(), trainerId, firstDate, lastDate);
        } catch (Exception e) {
            System.out.println(e);
        }

        return timetableList;
    }

    public List<TimetableResponse> findTrainerTimetable(String trainerId, String date, int tType) {
        List<TimetableResponse> timetableList = new ArrayList<>();
        Object[] params;

        this.sql = "select * from timetable where user_id = ? and t_date = ?";

        if(tType == 3) {
            params = new Object[]{trainerId, date};
        } else {
            sql += " and t_type = ?";
            params = new Object[]{trainerId, date, tType};
        }

        sql += " order by t_start_t";

        try {
            timetableList = jdbcTemplate.query(this.sql, new TimetableRowMapper(), params);
        } catch (Exception e) {
            System.out.println(e);
        }

        return timetableList;
    }

    public List<TimetableResponse> findMyTimetable(String trainerId, String date) {
        List<TimetableResponse> timetableList = new ArrayList<>();

        this.sql = "select * from timetable where user_id = ? and t_date = ? order by t_start_t";

        try {
            timetableList = jdbcTemplate.query(this.sql, new TimetableRowMapper(), trainerId, date);
        } catch (Exception e) {
            System.out.println(e);
        }

        return timetableList;
    }
}

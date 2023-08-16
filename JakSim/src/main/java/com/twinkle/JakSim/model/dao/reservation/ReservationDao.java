package com.twinkle.JakSim.model.dao.reservation;

import com.twinkle.JakSim.model.dto.reservation.response.MyMember;
import com.twinkle.JakSim.model.dto.reservation.response.ReservationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String sql;


    public Boolean register(String userId, int tIdx, int pIdx) {
        Boolean result = true;

        this.sql = "insert into reservation(t_idx, user_id, p_idx) " +
                "values(?, ?, ?)";

        try {
            jdbcTemplate.update(this.sql, tIdx, userId, pIdx);

        } catch (EmptyResultDataAccessException e) {
            System.out.println("예약이 올바르게 되지 않았습니다.");

            return false;
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }

    public Boolean delete(int rIdx) {
        this.sql = "delete from reservation where r_idx = ?";

        try {
            int rowsAffected = jdbcTemplate.update(this.sql, rIdx);

            if (rowsAffected != 0) {
                return true;
            }
            throw new RuntimeException("No rows were affected by the update operation");
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    public ReservationResponse findReservation(String userId, String trainerId, LocalDate tDate) {
        ReservationResponse reservationResponse = new ReservationResponse();

        this.sql = "select * from reservation as res inner join timetable as tt on res.t_idx = tt.t_idx " +
                "where res.user_id = ? and tt.user_id = ? and tt.t_date = ?";

        try {
            reservationResponse = jdbcTemplate.queryForObject(this.sql, new ReservationRowMapper(), userId, trainerId, tDate);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("예약이 없습니다.");
        } catch(Exception e) {
            System.out.println(e);
        }

        return reservationResponse;
    }

    public List<MyMember> findMyReservation(int tIdx) {
        List<MyMember> myMember = new ArrayList<>();

        this.sql = "select * from reservation as res inner join user_info as ui on res.user_id = ui.user_id " +
                    "where t_idx = ?";

        try {
            myMember = jdbcTemplate.query(this.sql, new MyMemberRowMapper(), tIdx);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("예약이 없습니다.");
        } catch(Exception e) {
            System.out.println(e);
        }

        return myMember;
    }

//    public Boolean isReservate(String userId, LocalDate tDate) {
//        Boolean result = true;
//
//        this.sql = "select * from reservation where user_id = ? and r_c_dt = ? ";
//
//        try {
//            jdbcTemplate.queryForObject(this.sql, new IsReservationRowMapper(), userId, tDate);
//        } catch(EmptyResultDataAccessException e) {
//            System.out.println("You have already booked");
//            System.out.println(e);
//            result = false;
//        }
//
//        return result;
//    }

}
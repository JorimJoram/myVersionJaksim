package com.twinkle.JakSim.model.dao.payment;

import com.twinkle.JakSim.model.dto.payment.response.ApproveResponse;
import com.twinkle.JakSim.model.dto.payment.response.PtCntDo;
import com.twinkle.JakSim.model.dto.payment.response.PtTicketResponse;
import com.twinkle.JakSim.model.dto.product.response.ValidPtResponse;

import com.twinkle.JakSim.model.dao.trainer.ProductRowMapper;
import com.twinkle.JakSim.model.dto.payment.PaymentDtoForMypage;
import com.twinkle.JakSim.model.dto.payment.response.PaymentDo;
import com.twinkle.JakSim.model.dto.trainer.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class PaymentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String sql;

    public Boolean isPtTicket(int pIdx) {
        boolean result = false;

        this.sql = "select p_pt_cnt from payment where p_idx = ?";

        try {
            PtTicketResponse paymentDto = jdbcTemplate.queryForObject(this.sql, new PaymentRowMappper(), pIdx);

            if(paymentDto.getPPtCnt() > 0) {
                result = true;
            }
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
            System.out.println("Thers isn't any information");
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }

    public Boolean savePaymentDetails(String userId, ApproveResponse paymentDetails) {
        Boolean result = true;

        this.sql = "insert into payment (user_id, tp_idx, tid, p_a_dt, p_status, p_pt_cnt, p_pt_period) " +
                "values (?, ?, ?, ?, 0, ?, ?)";
        try {
            jdbcTemplate.update(this.sql, userId, paymentDetails.getItem_code(), paymentDetails.getTid(),
                            paymentDetails.getCreated_at(), paymentDetails.getPtTimes(), paymentDetails.getPtPeriod());
        } catch (EmptyResultDataAccessException e) {
            result = false;
            System.out.println(e);
        }

        return result;
    }

    public Optional<PaymentDo> refund(String tid, String today) {
        PaymentDo paymentDo = new PaymentDo();

        String refundSql = "update payment set p_status = ?, p_m_dt = ? where tid = ?";
        String selectSql = "select * from payment where tid = ?";
        try {
            jdbcTemplate.update(refundSql, 1, today, tid);

            paymentDo = jdbcTemplate.queryForObject(selectSql, new PaymentDoRowMapper(), tid);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }

        return Optional.of(paymentDo);
    }

    public PtCntDo decreasePt(int ptCnt, int pIdx) {
        PtCntDo ptCntDo = new PtCntDo();
        this.sql = "update payment set p_pt_cnt = p_pt_cnt - 1 where p_idx = ?";

        try {
            jdbcTemplate.update(this.sql, pIdx);

            ptCntDo = findPtCnt(pIdx);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
        }

        return ptCntDo;
    }

    public PtCntDo increaseCnt(int pIdx) {
        PtCntDo ptCntDo = new PtCntDo();
        this.sql = "update payment set p_pt_cnt = p_pt_cnt + 1 where p_idx = ?";

        try {
            jdbcTemplate.update(this.sql, pIdx);

            ptCntDo= findPtCnt(pIdx);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return ptCntDo;
    }

    public PtCntDo findPtCnt(int pIdx) {
        this.sql = "select p_pt_cnt from payment where p_idx = ?";

        return jdbcTemplate.queryForObject(this.sql, new ptCntDoRosMapper(), pIdx);
    }

    public List<ValidPtResponse> findAllValidPt(String userId, LocalDate today) {
        List<ValidPtResponse> list = new ArrayList<>();

        this.sql = "select pro.user_id, pro.tp_type, u.user_name, pay.p_idx, pay.p_pt_cnt " +
                "from payment as pay inner join product as pro on pay.tp_idx = pro.tp_idx " +
                "inner join user_info as u on pro.user_id = u.user_id " +
                "where pay.user_id = ? and p_status = '0' and p_pt_cnt > '0' and p_pt_period >= datediff(?, p_a_dt)";

        list = jdbcTemplate.query(this.sql, new ValidPtRowMapper(), userId, today);

        return list;
    }

    public Optional<List<PaymentDtoForMypage>> findRecentByUsernameBy3(String username) {
        String sql = "SELECT P.P_IDX, P.TID, P.P_A_DT, P.P_PT_PERIOD, P.P_PT_CNT, T.TP_TITLE, T.TP_TYPE, T.TP_TIMES, T.TP_PRICE " +
                "FROM PAYMENT P, PRODUCT T " +
                "WHERE P.USER_ID = ? " +
                "AND T.TP_IDX = P.TP_IDX " +
                "ORDER BY P_IDX DESC " +
                "LIMIT 3";
        List<PaymentDtoForMypage> paymentList = new ArrayList<>();

        try{
            paymentList = jdbcTemplate.query(sql, new PaymentDtoForMypageRowMapper(), username);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        return Optional.of(paymentList);
    }

    public int getTotalPage(String username) {
        String sql = "SELECT COUNT(*) FROM PAYMENT WHERE USER_ID = ?";
        int result =  0;

        try{
            result = Objects.requireNonNull(jdbcTemplate.queryForObject(sql, Integer.class, username)).intValue();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return result;
    }

    public List<PaymentDo> getRecentByPage(String username, int page, int pageSize){
        String sql = "SELECT * FROM PAYMENT WHERE USER_ID = ? ORDER BY P_IDX DESC LIMIT ? OFFSET ?";
        int offset = (page - 1) * pageSize;
        List<PaymentDo> payList = new ArrayList<>();

        try{
            payList = jdbcTemplate.query(sql, new PaymentDoRowMapper(), username, pageSize, offset);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return payList;
    }

    public Optional<PaymentDo> getPaymentByIdx(String tid) {
        String sql = "SELECT * FROM PAYMENT WHERE TID = ?";
        PaymentDo paymentDo = null;

        try{
            paymentDo = jdbcTemplate.queryForObject(sql, new PaymentDoRowMapper(), tid);
        }catch (Exception e){
            System.out.println(tid);
            System.out.println(e.getMessage());
        }

        return Optional.ofNullable(paymentDo);
    }

    public ProductDto getProductByIdx(int tpIdx) {
        String sql = "SELECT * FROM PRODUCT WHERE TP_IDX = ?";
        ProductDto productDto = null;

        try{
            productDto = jdbcTemplate.queryForObject(sql, new ProductRowMapper(), tpIdx);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return productDto;
    }
}

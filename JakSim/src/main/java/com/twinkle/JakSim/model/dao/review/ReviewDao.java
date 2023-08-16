package com.twinkle.JakSim.model.dao.review;

import com.twinkle.JakSim.model.dto.review.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ReviewDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String sql;

    // 리뷰 등록
    public int insertReview(String tid, ReviewDto dto, String userId) {
        this.sql = "INSERT INTO REVIEW(USER_ID, TID, R_CONTENT, R_STAR, R_C_DT) " +
                "VALUES(?, ?, ?, ?, CURRENT_TIMESTAMP)";
        int result = -1;

        try{
            result = jdbcTemplate.update(this.sql, userId, tid, dto.getContent(), dto.getStar());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int updateReview(int r_idx, ReviewDto dto) {
        this.sql = "UPDATE REVIEW " +
                "SET " +
                "R_CONTENT = ?, " +
                "R_STAR = ?, " +
                "R_M_DT = CURRENT_TIMESTAMP " +
                "WHERE R_IDX = ?";
        int result = -1;

        try {
            result = jdbcTemplate.update(sql, dto.getContent(), dto.getStar(), r_idx);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return result;
    }

    public int deleteReview(int r_idx) {
        this.sql = "DELETE FROM REVIEW WHERE R_IDX = ?";
        int result = -1;

        try{
            result = jdbcTemplate.update(sql, r_idx);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return result;
    }

    public ReviewDto getReviewByTid(String tid) {
        this.sql = "SELECT * FROM REVIEW " +
                "WHERE TID = ?";
        ReviewDto dto = new ReviewDto();
        try{
            dto = jdbcTemplate.queryForObject(sql, new ReviewRowMapper(), tid);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return dto;
    }

    public List<ReviewDto> getReviewListByTpIdx(int tp_idx, boolean sort, int star) {
        this.sql = "SELECT R.R_IDX, R.USER_ID, R.R_CONTENT, R.R_STAR, R.R_C_DT, R.R_M_DT, R.TID " +
                "FROM PAYMENT P, REVIEW R " +
                "WHERE " +
                "P.TID = R.TID " +
                "AND " +
                "P.TP_IDX = ? " +
                checkStar(star) +
                checkSort(sort);
        List<ReviewDto> dtoList = new ArrayList<>();
        try{
            dtoList = (checkStar(star).isEmpty()) ? jdbcTemplate.query(sql, new ReviewRowMapper(), tp_idx) : jdbcTemplate.query(sql, new ReviewRowMapper(), tp_idx, star);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return dtoList;
    }

    public List<ReviewDto> getReviewListByUtIdx(int ut_idx, boolean sort, int star) {
        this.sql = "SELECT R.R_IDX, R.USER_ID, R.R_CONTENT, R.R_STAR, R.R_C_DT, R.R_M_DT, R.TID " +
                "FROM PAYMENT P, REVIEW R, TRAINER_DETAILS T, PRODUCT PR " +
                "WHERE " +
                "T.UT_IDX = PR.UT_IDX and " +
                "PR.TP_IDX = P.TP_IDX and " +
                "P.TID = R.TID and " +
                "T.UT_IDX = ? " +
                checkStar(star) +
                checkSort(sort);
        List<ReviewDto> dtoList = new ArrayList<>();
        try{
            dtoList = (checkStar(star).isEmpty()) ? jdbcTemplate.query(sql, new ReviewRowMapper(), ut_idx) : jdbcTemplate.query(sql, new ReviewRowMapper(), ut_idx, star);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return dtoList;
    }

    public List<ReviewDto> getReviewListByUsername(String username, boolean myPage, boolean sort, int star) {
        this.sql = "SELECT R.R_IDX, R.USER_ID, R.R_CONTENT, R.R_STAR, R.R_C_DT, R.R_M_DT, R.TID " +
                "FROM REVIEW R " +
                "WHERE R.USER_ID = ? " +
                checkStar(star) +
                checkSort(sort) +
                checkMyPage(myPage);
        List<ReviewDto> dtoList = new ArrayList<>();
        try{
            dtoList = (checkStar(star).isEmpty()) ? jdbcTemplate.query(sql, new ReviewRowMapper(), username) : jdbcTemplate.query(sql, new ReviewRowMapper(), username, star);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return dtoList;
    }

    private String checkMyPage(boolean myPage){
        return myPage ? "LIMIT 3 " : "";
    }

    private String checkSort(boolean sort) {
        return sort ? " ORDER BY R.R_STAR DESC, R.R_C_DT DESC " : "";
    }

    private String checkStar(int star) {
        return ((1 <= star) && (star <= 5)) ? "AND R.R_STAR = ? " : "";
    }

    public double getAvgStarByUtIdx(int ut_idx) {
        this.sql = commonAvgStar() +
                    "AND " +
                    "T.UT_IDX = ? " +
                    "GROUP BY T.UT_IDX";
        double result;
        try{
            result = Objects.requireNonNull(jdbcTemplate.queryForObject(sql, Double.class, ut_idx));
        }catch (Exception e){
            System.out.println("null");
            result = 0;
        }
        return result;
    }

    public double getAvgStarByTpIdx(int tp_idx) {
        this.sql = commonAvgStar() +
                "AND " +
                "T.UT_IDX = ? " +
                "GROUP BY T.UT_IDX";
        double result;
        try{
            result = Objects.requireNonNull(jdbcTemplate.queryForObject(sql, Double.class, tp_idx));
        }catch (Exception e){
            System.out.println("null");
            result = 0;
        }
        return result;
    }

    private String commonAvgStar() {
        return "SELECT COALESCE(ROUND(AVG(R.R_STAR), 1),0) AS R_AVG_STAR " +
                "FROM TRAINER_DETAILS T, PRODUCT PR, PAYMENT P, REVIEW R " +
                "WHERE " +
                "T.UT_IDX = PR.UT_IDX " +
                "AND " +
                "PR.TP_IDX = P.TP_IDX " +
                "AND " +
                "P.TID = R.TID ";
    }


    // 트레이너 리뷰 전체보기
    public List<ReviewDto> getTrainerReviewAll(int page, int pageSize, int filter, int trainerId) {
        //1. 최신순 (기본)
        //2. 별점 높은순
        //3. 별점 낮은순

        int offset = (page - 1) * pageSize;

        if(filter == 0) {
            String sql = "SELECT *" +
                    "FROM REVIEW " +
                    "WHERE UT_IDX = ? "+
                    " ORDER BY R_IDX DESC" +
                    " LIMIT ?, ?";

            return jdbcTemplate.query(sql, new Object[]{trainerId, offset, pageSize}, new ReviewRowMapper());

        }
        else if(filter == 1) {
            String sql = "SELECT *" +
                    "FROM REVIEW " +
                    "WHERE UT_IDX = ? "+
                    " ORDER BY R_STAR DESC" +
                    " LIMIT ?, ?";

            return jdbcTemplate.query(sql,  new Object[]{trainerId, offset, pageSize}, new ReviewRowMapper());
        }
        else {
            String sql = "SELECT *" +
                    "FROM REVIEW " +
                    "WHERE UT_IDX = ? "+
                    " ORDER BY R_STAR ASC" +
                    " LIMIT ?, ?";

            return jdbcTemplate.query(sql,  new Object[]{trainerId, offset, pageSize}, new ReviewRowMapper());
        }

    }

    // 리뷰 별점 및 전체수 count
    public ReviewDto getStarAvgAndCnt(int utIdx) {
        this.sql = "SELECT *, COUNT(*) AS REVIEW_CNT, ROUND(AVG(R_STAR), 1) AS AVG_R_STAR " +
                "FROM REVIEW WHERE UT_IDX = ?";

        //return jdbcTemplate.queryForObject(this.sql, new ReviewRowMapper2(), utIdx);
        return null;
    }

    // 리뷰 정보 가져오기 (리뷰 인덱스별)
    public List<ReviewDto> getMyReview(String userId, int reviewIdx) {
        this.sql = "SELECT * FROM REVIEW " +
                "WHERE USER_ID = ? AND R_IDX = ?";

        return jdbcTemplate.query(this.sql, new ReviewRowMapper(), userId, reviewIdx);
    }

    // 나의 리뷰 전체 가져오기 (마이페이지용)
    public Optional<List<ReviewDto>> getMyReviewForMyPage(String userId) {
        this.sql = "SELECT * FROM REVIEW R " +
                "WHERE USER_ID = ? " +
                "ORDER BY R_IDX DESC " +
                "LIMIT 3";
        List<ReviewDto> reviewList = null;
        try {
            reviewList = jdbcTemplate.query(this.sql, new ReviewRowMapper(), userId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(reviewList);
    }

    // 리뷰 수정
    public void editReview(ReviewDto review, String userId) {
        this.sql = "UPDATE REVIEW SET R_CONTENT = ?, R_STAR = ?, R_M_DT = current_timestamp " +
                "WHERE USER_ID = ?";

        jdbcTemplate.update(this.sql, review.getContent(),
                review.getStar(), userId);

    }

}

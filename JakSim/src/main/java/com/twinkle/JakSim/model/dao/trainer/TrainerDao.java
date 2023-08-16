package com.twinkle.JakSim.model.dao.trainer;

import com.twinkle.JakSim.model.dao.timetable.TimetableRowMapper;
import com.twinkle.JakSim.model.dto.timetable.response.TimetableResponse;
import com.twinkle.JakSim.model.dto.trainer.TrainerInsertDto;
import com.twinkle.JakSim.model.dto.trainer.*;
import com.twinkle.JakSim.model.dto.trainer.response.TrainerDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrainerDao {
    private final JdbcTemplate jdbcTemplate;
    private String sql;

    // 트레이너 등록
    public void insertTrainer(TrainerInsertDto trainer, String userId) {
        try {
            // Insert into TRAINER_DETAILS table
            this.sql = "INSERT INTO TRAINER_DETAILS(UT_INTRO, UT_INSTA, UT_GYM, USER_ID, UT_EXPERT_1, UT_EXPERT_2, UT_ADDRESS, UT_PROFILE_IMG) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, trainer.getIntroduce(), trainer.getInsta(),
                    trainer.getGym(), userId, trainer.getExpert1(), trainer.getExpert2(), trainer.getAddress(), trainer.getProfileImg());

            // Get the inserted UT_IDX
            Integer utIdx = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

            // Insert into PRODUCT table
            this.sql = "INSERT INTO PRODUCT(UT_IDX, TP_TIMES, TP_PRICE, TP_TYPE, TP_TITLE, TP_PERIOD) " +
                    "VALUES(?, ?, ?, ?, ?, ?)";
            for (int i = 0; i < trainer.getPtTimes().length; i++) {
                jdbcTemplate.update(sql, utIdx, trainer.getPtTimes()[i],
                        trainer.getPtPrice()[i], trainer.getPtType()[i], trainer.getPtTitle()[i],
                        trainer.getPtPeriod()[i]);
            }

            // Insert into TRAINER_CAREER table
            this.sql = "INSERT INTO TRAINER_CAREER VALUES(NULL, ?, ?)";
            for (String content : trainer.getCareerContent()) {
                jdbcTemplate.update(sql, utIdx, content);
            }

            // Insert into TRAINER_CERT table
            this.sql = "INSERT INTO TRAINER_CERT VALUES(NULL, ?, ?)";
            for (String cert : trainer.getCertName()) {
                jdbcTemplate.update(sql, utIdx, cert);
            }

            // Insert into TRAINER_IMAGE table
            this.sql = "INSERT INTO TRAINER_IMAGE VALUES(NULL, ?, ?)";
            for (String image : trainer.getImagePath()) {
                jdbcTemplate.update(sql, utIdx, image);
            }

            // 트레이너로 USER_ROLE 변경
            this.sql = "UPDATE USER_INFO SET USER_ROLE = 2 WHERE USER_ID = ?";
            jdbcTemplate.update(sql, userId);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 트레이너 찾기
    // 수정 필요 -> 허락 후 진행
    public List<TrainerSearchDto> getAllTrainerForSearch(int page, int pageSize, int filter, String address) {
        int offset = (page - 1) * pageSize;
        //1. 주소만 있을 때, 필터링은 없음
        //2. 필터링만 할 때, 주소는 없음
        //3. 주소와 필터링을 둘다 할 때
        //4. 주소와 필터링을 둘다 안할때 (else)

        List<TrainerSearchDto> dtoList = new ArrayList<>();

        if((!address.equals("-")) && (filter == -1)){

            String sql = "SELECT DISTINCT td.user_id, td.UT_IDX, td.UT_PROFILE_IMG, ti.TI_PATH, td.UT_GYM, ui.user_name, " +
                    "td.UT_EXPERT_1, td.UT_EXPERT_2, td.UT_ADDRESS, tc.TC_NAME, ROUND(AVG(R.R_STAR), 1) as AVG_R_STAR " +
                    " FROM trainer_details td" +
                    " JOIN product p ON td.UT_IDX = p.UT_IDX" +
                    " JOIN trainer_career tca ON td.UT_IDX = tca.UT_IDX" +
                    " JOIN trainer_cert tc ON td.UT_IDX = tc.UT_IDX" +
                    " JOIN trainer_image ti ON td.UT_IDX = ti.UT_IDX" +
                    " JOIN user_info ui ON td.user_id = ui.user_id" +
                    " LEFT JOIN PAYMENT PA ON P.TP_IDX = PA.TP_IDX" +
                    " LEFT JOIN REVIEW R ON PA.TID = R.TID" +
                    " where ut_address LIKE ?" +
                    " GROUP BY td.user_id" +
                    " ORDER BY AVG_R_STAR DESC, td.UT_IDX DESC" +
                    " LIMIT ?, ?";

            dtoList = jdbcTemplate.query(sql, new Object[]{"%" + address + "%", offset, pageSize}, new TrainerSearchRowMapper());

        }
        else if((address.equals("-")) && (filter != -1)) {

            String sql = "SELECT DISTINCT td.user_id, td.UT_IDX, td.UT_PROFILE_IMG, ti.TI_PATH, td.UT_GYM, ui.user_name, " +
                    "td.UT_EXPERT_1, td.UT_EXPERT_2, td.UT_ADDRESS, tc.TC_NAME, ROUND(AVG(R.R_STAR), 1) as R_AVG_STAR  " +
                    " FROM trainer_details td" +
                    " JOIN product p ON td.UT_IDX = p.UT_IDX" +
                    " JOIN trainer_career tca ON td.UT_IDX = tca.UT_IDX" +
                    " JOIN trainer_cert tc ON td.UT_IDX = tc.UT_IDX" +
                    " JOIN trainer_image ti ON td.UT_IDX = ti.UT_IDX" +
                    " JOIN user_info ui ON td.user_id = ui.user_id" +
                    " LEFT JOIN PAYMENT PA ON P.TP_IDX = PA.TP_IDX" +
                    " LEFT JOIN REVIEW R ON PA.TID = R.TID" +
                    " where ut_expert_1 = ? or ut_expert_2 = ? " +
                    " GROUP BY td.user_id" +
                    " ORDER BY R_AVG_STAR DESC, td.UT_IDX DESC" +
                    " LIMIT ?, ?";

            dtoList = jdbcTemplate.query(sql, new Object[]{filter, filter, offset, pageSize}, new TrainerSearchRowMapper());

        }
        else if((!address.equals("-")) && (filter != -1)) {
            String sql = "SELECT DISTINCT td.user_id, td.UT_IDX, td.UT_PROFILE_IMG, ti.TI_PATH, td.UT_GYM, ui.user_name, " +
                    "td.UT_EXPERT_1, td.UT_EXPERT_2, td.UT_ADDRESS, tc.TC_NAME, ROUND(AVG(R.R_STAR), 1) as R_AVG_STAR  " +
                    " FROM trainer_details td" +
                    " JOIN product p ON td.UT_IDX = p.UT_IDX" +
                    " JOIN trainer_career tca ON td.UT_IDX = tca.UT_IDX" +
                    " JOIN trainer_cert tc ON td.UT_IDX = tc.UT_IDX" +
                    " JOIN trainer_image ti ON td.UT_IDX = ti.UT_IDX" +
                    " JOIN user_info ui ON td.user_id = ui.user_id" +
                    " LEFT JOIN PAYMENT PA ON P.TP_IDX = PA.TP_IDX" +
                    " LEFT JOIN REVIEW R ON PA.TID = R.TID" +
                    " where ut_address LIKE ?" +
                    " and ut_expert_1 = ? or ut_expert_2 = ? " +
                    " GROUP BY td.user_id" +
                    " ORDER BY R_AVG_STAR DESC, td.UT_IDX DESC" +
                    " LIMIT ?, ?";

            dtoList =  jdbcTemplate.query(sql, new Object[]{"%" + address + "%", filter, filter, offset, pageSize}, new TrainerSearchRowMapper());

        }
        else {

            String sql = "SELECT DISTINCT td.user_id, td.UT_IDX, td.UT_PROFILE_IMG, ti.TI_PATH, td.UT_GYM, ui.user_name, " +
                    "td.UT_EXPERT_1, td.UT_EXPERT_2, td.UT_ADDRESS, tc.TC_NAME, ROUND(AVG(R.R_STAR), 1) as R_AVG_STAR " +
                    " FROM trainer_details td" +
                    " JOIN product p ON td.UT_IDX = p.UT_IDX" +
                    " JOIN trainer_career tca ON td.UT_IDX = tca.UT_IDX" +
                    " JOIN trainer_cert tc ON td.UT_IDX = tc.UT_IDX" +
                    " JOIN trainer_image ti ON td.UT_IDX = ti.UT_IDX" +
                    " JOIN user_info ui ON td.user_id = ui.user_id" +
                    " LEFT JOIN PAYMENT PA ON P.TP_IDX = PA.TP_IDX" +
                    " LEFT JOIN REVIEW R ON PA.TID = R.TID" +
                    " GROUP BY td.user_id" +
                    " ORDER BY R_AVG_STAR DESC, td.UT_IDX DESC" +
                    " LIMIT ?, ?";

            dtoList = jdbcTemplate.query(sql, new Object[]{offset, pageSize}, new TrainerSearchRowMapper());

        }

        return dtoList;
    }

    // 2-1. 트레이너 count
    public int getTrainerCount(int filter, String address) {
        //1. 주소만 있을 때, 필터링은 없음
        //2. 필터링만 할 때, 주소는 없음
        //3. 주소와 필터링을 둘다 할 때
        //4. 주소와 필터링을 둘다 안할때 (else)

        if((!address.equals("-")) && (filter == -1)){

            String sql = "SELECT COUNT(*) FROM trainer_details " +
                    "WHERE ut_address LIKE ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{"%" + address + "%"}, Integer.class);

        }
        else if((address.equals("-")) && (filter != -1)) {

            String sql = "SELECT COUNT(*) FROM trainer_details " +
                    "WHERE ut_expert_1 = ? or ut_expert_2 = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{filter, filter}, Integer.class);

        }
        else if((!address.equals("-")) && (filter != -1)) {
            String sql = "SELECT COUNT(*) FROM trainer_details " +
                    " where ut_address LIKE ?" +
                    " and ut_expert_1 = ? or ut_expert_2 = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{"%" + address + "%" , filter, filter}, Integer.class);

        }
        else {
            String sql = "SELECT COUNT(*) FROM trainer_details";
            return jdbcTemplate.queryForObject(sql, Integer.class);

        }

    }

    // 메인 페이지
    public List<TrainerSearchDto> getAllTrainerForMainPage() {
        String sql = "SELECT DISTINCT td.user_id, td.UT_IDX, td.UT_PROFILE_IMG, ti.TI_PATH, td.UT_GYM, ui.user_name, " +
                "td.UT_EXPERT_1, td.UT_EXPERT_2, td.UT_ADDRESS, tc.TC_NAME, ROUND(AVG(R.R_STAR), 1) as R_AVG_STAR " +
                " FROM trainer_details td" +
                " JOIN product p ON td.UT_IDX = p.UT_IDX" +
                " JOIN trainer_career tca ON td.UT_IDX = tca.UT_IDX" +
                " JOIN trainer_cert tc ON td.UT_IDX = tc.UT_IDX" +
                " JOIN trainer_image ti ON td.UT_IDX = ti.UT_IDX" +
                " JOIN user_info ui ON td.user_id = ui.user_id" +
                " LEFT JOIN PAYMENT PA ON P.TP_IDX = PA.TP_IDX" +
                " LEFT JOIN REVIEW R ON PA.TID = R.TID" +
                " GROUP BY td.user_id" +
                " ORDER BY td.UT_IDX DESC" +
                " LIMIT 3";

        return jdbcTemplate.query(sql, new TrainerSearchRowMapper());
    }

    // 트레이너 이름, 인덱스 가져오기
    // 메서드 이름을 변경하면 좋을 거 같습니다. name은 겹치는 컬럼명이 있어서 userId 혹은 username 으로 수정해주시길 권합니다.
    public TrainerPageDto getTrainerName(String userId) {
        String sql = "SELECT DISTINCT * " +
                "FROM trainer_details td " +
                "JOIN user_info ui ON td.user_id = ui.user_id " +
                "WHERE td.user_id = ?";

        List<TrainerPageDto> resultList = jdbcTemplate.query(sql, new TrainerPageRowMapper(), userId);

        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }


    // 트레이너 상세페이지
    public List<TrainerPageDto> getTrainerPage(int utIdx) {
        String sql = "SELECT DISTINCT td.UT_IDX, td.user_id, td.UT_EXPERT_1, td.UT_EXPERT_2, " +
                "ui.user_name, td.UT_GYM, td.UT_INSTA, td.UT_INTRO, td.UT_ADDRESS, td.UT_PROFILE_IMG " +
                "FROM trainer_details td " +
                "JOIN user_info ui ON td.user_id = ui.user_id " +
                "JOIN trainer_cert tc ON td.UT_IDX = tc.UT_IDX " +
                "WHERE td.UT_IDX = ?";

        return jdbcTemplate.query(sql, new TrainerPageRowMapper(), utIdx);
    }

    public List<ProductDto> getProduct(int utIdx) {
        this.sql = "select * from product where UT_IDX = ?";

        return jdbcTemplate.query(sql, new ProductRowMapper(), utIdx);
    }
    public List<TrainerCareerDto> getCareer(int utIdx) {
        this.sql = "select * from trainer_career where UT_IDX = ?";

        return jdbcTemplate.query(sql, new TrainerCareerRowMapper(), utIdx);
    }

    public List<TrainerCertDto> getCert(int utIdx) {
        this.sql = "select * from trainer_cert where UT_IDX = ?";

        return jdbcTemplate.query(sql, new TrainerCertRowMapper(), utIdx);
    }

    public List<TrainerImageDto> getTrainerImage(int utIdx) {
        this.sql = "select * from trainer_image where UT_IDX = ?";

        return jdbcTemplate.query(sql, new TrainerImageRowMapper(), utIdx);
    }

    // 트레이너 정보 수정
    public void upDateTrainer(TrainerInsertDto trainer, String userId, MultipartFile[] imagePath){
        if (trainer.getProfileImg() != null) {
            this.sql = "UPDATE TRAINER_DETAILS " +
                    "SET UT_INTRO = ?," +
                    "UT_INSTA = ?," +
                    "UT_GYM = ?," +
                    "UT_EXPERT_1 = ?," +
                    "UT_EXPERT_2 = ? ," +
                    "UT_ADDRESS = ? ," +
                    "UT_PROFILE_IMG = ? " +
                    "WHERE USER_ID = ?";

            jdbcTemplate.update(sql, trainer.getIntroduce(), trainer.getInsta(),
                    trainer.getGym(), trainer.getExpert1(), trainer.getExpert2(), trainer.getAddress(), trainer.getProfileImg(), userId);

        }
        else {
            this.sql = "UPDATE TRAINER_DETAILS " +
                    "SET UT_INTRO = ?," +
                    "UT_INSTA = ?," +
                    "UT_GYM = ?," +
                    "UT_EXPERT_1 = ?," +
                    "UT_EXPERT_2 = ? ," +
                    "UT_ADDRESS = ? " +
                    "WHERE USER_ID = ?";

            jdbcTemplate.update(sql, trainer.getIntroduce(), trainer.getInsta(),
                    trainer.getGym(), trainer.getExpert1(), trainer.getExpert2(), trainer.getAddress(), userId);

        }



        for(int i=0; i<trainer.getPtTimes().length; i++) {
            this.sql = "UPDATE PRODUCT " +
                "SET TP_TIMES = ?," +
                "TP_PRICE = ?," +
                "TP_TYPE = ?," +
                "TP_TITLE = ?," +
                "TP_PERIOD = ? " +
                "WHERE TP_IDX = ?";

            jdbcTemplate.update(sql, trainer.getPtTimes()[i],
                    trainer.getPtPrice()[i], trainer.getPtType()[i], trainer.getPtTitle()[i],
                    trainer.getPtPeriod()[i], trainer.getPtId()[i]);
        }

        for(int i=0; i<trainer.getCareerContent().length; i++) {
            this.sql = "UPDATE TRAINER_CAREER " +
                "SET TCAR_CONTENT = ? " +
                "WHERE TCAR_IDX = ?";

            jdbcTemplate.update(sql, trainer.getCareerContent()[i], trainer.getCareerId()[i]);
        }

        for(int i=0; i<trainer.getCertName().length; i++) {
            this.sql = "UPDATE TRAINER_CERT " +
                    "SET TC_NAME = ? " +
                    "WHERE TC_IDX = ?";

            jdbcTemplate.update(sql, trainer.getCertName()[i], trainer.getCertId()[i]);
        }

        if (!imagePath[0].isEmpty()) {

            this.sql = "DELETE FROM TRAINER_IMAGE WHERE USER_ID = ?";
            jdbcTemplate.update(sql, userId);

            this.sql = "INSERT INTO TRAINER_IMAGE VALUES(NULL, ?, ?)";
            for(String image : trainer.getImagePath()) {
                jdbcTemplate.update(sql, userId, image);
            }
        }
        else {
            System.out.println("ImagePath null!");
        }

    }

    // 트레이너 탈퇴
    public void deleteTrainer(String userId, int utIdx){
        this.sql = "DELETE FROM TRAINER_DETAILS WHERE USER_ID = ?";
        jdbcTemplate.update(sql, userId);

        this.sql = "DELETE FROM PRODUCT WHERE UT_IDX = ?";
        jdbcTemplate.update(sql, utIdx);

        this.sql = "DELETE FROM TRAINER_CAREER WHERE UT_IDX = ?";
        jdbcTemplate.update(sql, utIdx);

        this.sql = "DELETE FROM TRAINER_CERT WHERE UT_IDX = ?";
        jdbcTemplate.update(sql, utIdx);

        this.sql = "DELETE FROM TRAINER_IMAGE WHERE UT_IDX = ?";
        jdbcTemplate.update(sql, utIdx);

        this.sql = "UPDATE USER_INFO SET USER_ROLE = 1 WHERE UT_IDX = ?";
        jdbcTemplate.update(sql, utIdx);

        this.sql = "DELETE FROM TIMETABLE WHERE USER_ID = ?";
        jdbcTemplate.update(sql, userId);

    }

    public TrainerDetailResponse findTrainerBreif(String trainerId) {
        TrainerDetailResponse trainerDetailResponse = new TrainerDetailResponse();

        this.sql = "select * from trainer_details as t inner join user_info as u on t.user_id = u.user_id " +
                "inner join trainer_image as img on u.user_id = img.user_id " +
                "where t.user_id = ? group by t.user_id";

        try {
            trainerDetailResponse = jdbcTemplate.queryForObject(this.sql, new TrainerDetailRowMapper(), trainerId);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
        }

        return trainerDetailResponse;
    }

    // 트레이너 PT 시간표 가져오기
    public List<TimetableResponse> getTimetable(String userId) {
        this.sql = "SELECT * FROM TIMETABLE WHERE USER_ID = ?";

        return jdbcTemplate.query(this.sql, new TimetableRowMapper(), userId);
    }

    // 트레이너 시간표 등록
    public void registerTimetable(TimetableResponse timetable) {
        try {
            this.sql = "INSERT INTO TIMETABLE VALUES(NULL, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate.update(this.sql, timetable.getUserId(), timetable.getTDate(), timetable.getTType(),
                    timetable.getTStartT(), timetable.getTEndT(),
                    timetable.getTPeople());
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteTimetable(int tIdx) {
        this.sql = "DELETE FROM TIMETABLE WHERE T_IDX = ?";
        jdbcTemplate.update(this.sql, tIdx);
    }

    // 트레이너 결제 회원 정보 조회
    public List<PtUserDto> getPtUserInfo(int page, int pageSize, int utIdx, String ptUserName) {
        int offset = (page - 1) * pageSize;

        if(ptUserName.equals("-")) {
            String sql = "SELECT UI.USER_ID, UI.USER_NAME, UI.USER_TEL, UI.USER_GENDER, PD.TP_TYPE, P.P_PT_CNT " +
                    "FROM USER_INFO UI JOIN PAYMENT P ON UI.USER_ID = P.USER_ID " +
                    "JOIN PRODUCT PD ON P.TP_IDX = PD.TP_IDX WHERE PD.UT_IDX = ?" +
                    " LIMIT ?, ?";

            return jdbcTemplate.query(sql, new Object[]{utIdx, offset, pageSize}, new PtUserRowMapper());

        }
        else {
            String sql = "SELECT UI.USER_ID, UI.USER_NAME, UI.USER_TEL, UI.USER_GENDER, PD.TP_TYPE, P.P_PT_CNT " +
                    "FROM USER_INFO UI JOIN PAYMENT P ON UI.USER_ID = P.USER_ID " +
                    "JOIN PRODUCT PD ON P.TP_IDX = PD.TP_IDX WHERE PD.UT_IDX = ? AND UI.USER_NAME = ?" +
                    " LIMIT ?, ?";

            return jdbcTemplate.query(sql,  new Object[]{utIdx, ptUserName, offset, pageSize}, new PtUserRowMapper());
        }

    }

    // 트레이너 결제 회원 count
    public int getPtUserCnt(String userId, String ptUserName) {

        if (ptUserName.equals("-")) {
            String sql = "select count(*)" +
                    " from user_info ui join payment p on ui.user_id = p.user_id" +
                    " join product pd on p.tp_idx = pd.tp_idx where pd.UT_IDX = ?";

            return jdbcTemplate.queryForObject(sql, Integer.class, userId);
        }
        else {
            String sql = "select count(*)" +
                    " from user_info ui join payment p on ui.user_id = p.user_id" +
                    " join product pd on p.tp_idx = pd.tp_idx where pd.UT_IDX = ? and ui.user_name = ?";

            return jdbcTemplate.queryForObject(sql, Integer.class, userId, ptUserName);
        }
    }

    // 확인 필요
    public TrainerForPayDetail searchByUsername(int trainerId) {
        this.sql = "SELECT T.UT_IDX, T.USER_ID, U.USER_NAME, U.USER_GENDER, T.UT_INSTA, T.UT_GYM, T.UT_EXPERT_1, T.UT_EXPERT_2, I.TI_PATH " +
                "FROM TRAINER_DETAILS T, USER_INFO U, TRAINER_IMAGE I " +
                "WHERE T.UT_IDX = ? " +
                "AND T.USER_ID = U.USER_ID AND T.UT_IDX = I.UT_IDX "; //변경 필요
        TrainerForPayDetail trainer = new TrainerForPayDetail();
        try{
            trainer = jdbcTemplate.queryForObject(sql, new RowMapper<TrainerForPayDetail>() {
                @Override
                public TrainerForPayDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
                    TrainerForPayDetail dto = new TrainerForPayDetail();
                    dto.setGym(rs.getString("UT_GYM"));
                    dto.setGender(rs.getInt("USER_GENDER"));
                    dto.setInsta(rs.getString("UT_INSTA"));
                    dto.setExpert1(rs.getInt("UT_EXPERT_1"));
                    dto.setExpert2(rs.getInt("UT_EXPERT_2"));
                    dto.setImagePath(rs.getString("TI_PATH"));
                    dto.setUt_idx(rs.getInt("UT_IDX"));
                    dto.setUserId(rs.getString("USER_ID"));
                    dto.setUserName(rs.getString("USER_NAME"));
                    return dto;
                }
            }, trainerId);
        }catch (EmptyResultDataAccessException e){
            System.out.println(e.getMessage());
        }
        return trainer;
    }


}

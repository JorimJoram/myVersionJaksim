package com.twinkle.JakSim.model.service.trainer;

import com.twinkle.JakSim.model.dao.trainer.*;
import com.twinkle.JakSim.model.dto.account.UserDto;
import com.twinkle.JakSim.model.dto.timetable.TimetableInsertDto;
import com.twinkle.JakSim.model.dto.timetable.response.TimetableResponse;
import com.twinkle.JakSim.model.dto.trainer.TrainerInsertDto;
import com.twinkle.JakSim.model.dto.trainer.*;
import com.twinkle.JakSim.model.dto.trainer.response.TrainerDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerDao trainerDao;

    //트레이너 등록
    @Transactional
    public void TrainerSignUp(TrainerInsertDto requestTrainer, String userId, MultipartFile profileImage, MultipartFile[] imagePath) throws IOException {
        if(!profileImage.isEmpty() || profileImage != null) {
            String projectPath = System.getProperty("user.dir") +
                    "/JakSim/src/main/resources/static/image/trainer";  // 자신의 폴더에 맞게 수정!!
            System.out.println(projectPath);

            UUID uuid = UUID.randomUUID();
            String profile = uuid + "_" + profileImage.getOriginalFilename();
            File saveFile = new File(projectPath, profile);
            requestTrainer.setProfileImg("/image/trainer/" + profile);
            profileImage.transferTo(saveFile);
        }
        else {
            System.out.println("프로필 이미지없음");
        }

        if(imagePath.length != 0  || imagePath != null) {
            String projectPath = System.getProperty("user.dir") +
                    "/JakSim/src/main/resources/static/image/trainer";  // 자신의 폴더에 맞게 수정!!
            UUID uuid = UUID.randomUUID();
            List<String> imagePaths = new ArrayList<>();

            for(int i = 0; i < imagePath.length; i++) {
                String imageName = uuid + "_" + imagePath[i].getOriginalFilename();
                File saveFile2 = new File(projectPath, imageName);
                imagePath[i].transferTo(saveFile2);
                imagePaths.add("/image/trainer/" + imageName);
            }
            requestTrainer.setImagePath(imagePaths.toArray(new String[0]));
        }
        else {
            System.out.println("이미지패스없음");
        }
        trainerDao.insertTrainer(requestTrainer, userId);

    }

    // 트레이너 정보 수정
    @Transactional
    public void updateTrainer(TrainerInsertDto requestTrainer, String userId, MultipartFile profileImage, MultipartFile[] imagePath) throws IOException {
        if(!profileImage.isEmpty()) {
            String projectPath = System.getProperty("user.dir") +
                    "/JakSim/src/main/resources/static";

            if(requestTrainer.getProfileImg() != null) {
                String path = projectPath + requestTrainer.getProfileImg();

                File file = new File(path);

                if (file.exists()) {
                    file.delete();
                }

                String projectPath2 = System.getProperty("user.dir") +
                        "/JakSim/src/main/resources/static/image/trainer";
                UUID uuid = UUID.randomUUID();
                String certName = uuid + "_" + profileImage.getOriginalFilename();
                File saveFile = new File(projectPath2, certName);
                requestTrainer.setProfileImg("/image/trainer/" + certName);
                profileImage.transferTo(saveFile);
            }
        }
        else {
            System.out.println("새로운 프로필 이미지없음");
        }

        if(!imagePath[0].isEmpty()) {
            String projectPath = System.getProperty("user.dir") +
                    "/JakSim/src/main/resources/static";

            if(requestTrainer.getImagePath().length!=0 || requestTrainer.getImagePath() != null) {
                for (String image : requestTrainer.getImagePath()) {
                    String path = projectPath + image;

                    File file = new File(path);

                    if(file.exists()) {
                        file.delete();
                    }
                }
            }
            String projectPath2 = System.getProperty("user.dir") +
                    "/JakSim/src/main/resources/static/image/trainer";
            UUID uuid = UUID.randomUUID();
            List<String> imagePaths = new ArrayList<>();

            for(int i = 0; i < imagePath.length; i++) {
                String imageName = uuid + "_" + imagePath[i].getOriginalFilename();
                File saveFile2 = new File(projectPath2, imageName);
                imagePath[i].transferTo(saveFile2);
                imagePaths.add("/image/trainer/" + imageName);
            }
            requestTrainer.setImagePath(imagePaths.toArray(new String[0]));
        }
        else if(requestTrainer.getImagePath() != null) {
            System.out.println("새로운 이미지 없음");
        }

        trainerDao.upDateTrainer(requestTrainer, userId, imagePath);
    }

    // 트레이너 찾기
    @Transactional
    public List<TrainerSearchDto> searchAllTrainer(int page, int pageSize, int filter, String address) {
        return trainerDao.getAllTrainerForSearch(page, pageSize, filter, address);
    }

    // 메인페이지 트레이너 (최신등록순)
    @Transactional
    public List<TrainerSearchDto> searchTrainerForMainPage() {
        return trainerDao.getAllTrainerForMainPage();
    }

    private void setMapping(TrainerSearchDto item, TrainerSearchDto dto) {
        item.setUserName(dto.getUserName());
        item.setUserId(dto.getUserId());
        item.setAddress(dto.getAddress());
        item.setProfile(dto.getProfile());
        item.setGym(dto.getGym());
        item.setExpert1(dto.getExpert1());
        item.setExpert2(dto.getExpert2());
        item.setCertName(dto.getCertName());
        item.setImagePath(dto.getImagePath());
    }

    // 트레이너 전체 수 count
    @Transactional
    public int getTrainerCnt(int filter, String address) {
        return trainerDao.getTrainerCount(filter, address);
    }


    // 트레이너 이름 및 인덱스 값 가져오기
    @Transactional
    public TrainerPageDto searchTrainerName(String userId) {
        return trainerDao.getTrainerName(userId);
    }

    //트레이너 상세페이지에 표시될 정보 가져오기
    @Transactional
    public List<TrainerPageDto> searchTrainer(int utIdx) {
        return trainerDao.getTrainerPage(utIdx);
    }

    @Transactional
    public List<ProductDto> getProduct(int utIdx) {
        return trainerDao.getProduct(utIdx);
    }

    @Transactional
    public List<TrainerCareerDto> getCareer(int utIdx) {
        return trainerDao.getCareer(utIdx);
    }

    @Transactional
    public List<TrainerCertDto> getCert(int utIdx) {
        return trainerDao.getCert(utIdx);
    }

    @Transactional
    public List<TrainerImageDto> getTrainerImage(int utIdx) {
        return trainerDao.getTrainerImage(utIdx);
    }


    // 트레이너 정보 삭제
    @Transactional
    public void deleteTrainer(TrainerInsertDto trainerDto, String userId) {

        String projectPath = System.getProperty("user.dir") +
                "/JakSim/src/main/resources/static";        // 자신의 폴더에 맞게 수정 !!

        if(trainerDto.getProfileImg() != null) {
            String path = projectPath + trainerDto.getProfileImg();

            File file = new File(path);

            if(file.exists()) {     // 정보 삭제 시 사진 또한 폴더에서 삭제
                file.delete();
            }
        }

        if(trainerDto.getImagePath().length==0 || trainerDto.getImagePath() != null) {
            for (String image : trainerDto.getImagePath()) {
                String path = projectPath + image;

                File file = new File(path);

                if(file.exists()) {
                    file.delete();
                }
            }

        }

        trainerDao.deleteTrainer(userId, trainerDto.getTrainerId());

    }

    // 트레이너 PT 시간표 가져오기
    @Transactional
    public List<TimetableResponse> getTimetable(String userId) {
        return trainerDao.getTimetable(userId);
    }

    // 트레이너 PT 시간표 등록
    @Transactional
    public void registerTimetable(TimetableInsertDto timetable, String userId) {
        TimetableResponse timetableDto = new TimetableResponse(timetable.getTIdx(), userId, LocalDate.parse(timetable.getTDate()),
                timetable.getTType(), LocalTime.parse(timetable.getTStartT()), LocalTime.parse(timetable.getTEndT()),
                timetable.getTPeople());

        trainerDao.registerTimetable(timetableDto);

    }

    // 트레이너 시간표 삭제
    @Transactional
    public void deleteTimetable(int tIdx) {
        trainerDao.deleteTimetable(tIdx);
    }

    // 트레이너별 결제 회원 정보 가져오기
    @Transactional
    public List<PtUserDto> getMyPtUserInfo(int page, int pageSize, int utIdx, String ptUserName) {
        return trainerDao.getPtUserInfo(page, pageSize, utIdx, ptUserName);
    }

    // 트레이너별 결제 회원 count
    @Transactional
    public int getPtUserCnt(String userId, String ptUserName) {
        return trainerDao.getPtUserCnt(userId, ptUserName);
    }


    public TrainerDetailResponse findTrainerBreif(String trainerId) {
        TrainerDetailResponse trainerDetailResponse = new TrainerDetailResponse();

        try {
            trainerDetailResponse = trainerDao.findTrainerBreif(trainerId);
        } catch (Exception e) {
            System.out.println(e);
        }

        return trainerDetailResponse;
    }

    // 확인 필요
    public TrainerForPayDetail searchByUsername(int trainerId) {
        return trainerDao.searchByUsername(trainerId);
    }
}

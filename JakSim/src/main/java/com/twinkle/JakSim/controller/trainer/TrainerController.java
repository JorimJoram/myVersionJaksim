package com.twinkle.JakSim.controller.trainer;

import com.twinkle.JakSim.model.dto.review.ReviewDto;
import com.twinkle.JakSim.model.dto.timetable.TimetableInsertDto;
import com.twinkle.JakSim.model.dto.trainer.TrainerInsertDto;
import com.twinkle.JakSim.model.dto.trainer.*;
import com.twinkle.JakSim.model.service.account.FileService;
import com.twinkle.JakSim.model.service.review.ReviewService;
import com.twinkle.JakSim.model.service.trainer.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;
    private final ReviewService reviewService;
    private final FileService fileService;

    // 트레이너 등록
    @GetMapping("/trainer/trainerRegister")
    public String trainerSignUp(Model model,  @AuthenticationPrincipal User info) {
        if(info != null) {
            model.addAttribute("profile_image", fileService.getSingeProfile(info.getUsername()));
        }
        model.addAttribute("head_title", "트레이너 등록");

        return "content/trainer/trainerRegister";
    }

    @PostMapping("/trainerRegister")
    public String trainerSignUp(TrainerInsertDto trainerDto, @AuthenticationPrincipal User info,
                                @RequestParam("profileImage1") MultipartFile profileImage,
                                @RequestParam("imagePath1") MultipartFile[] imagePath) throws IOException {
        trainerService.TrainerSignUp(trainerDto, info.getUsername(), profileImage, imagePath);

        return "redirect:/logout";
    }

    // 트레이너 수정
    @GetMapping("/trainer/trainerUpdate/{trainerId}")
    public String trainerUpdate(Model model, @PathVariable("trainerId") int trainerId, @AuthenticationPrincipal User info) {

        if(info != null) {
            model.addAttribute("profile_image", fileService.getSingeProfile(info.getUsername()));
        }

        model.addAttribute("head_title", "트레이너 정보수정");
        model.addAttribute("userId", info);
        model.addAttribute("trainer", trainerService.searchTrainer(trainerId));
        model.addAttribute("product", trainerService.getProduct(trainerId));
        model.addAttribute("cert", trainerService.getCert(trainerId));
        model.addAttribute("career", trainerService.getCareer(trainerId));
        model.addAttribute("imageList", trainerService.getTrainerImage(trainerId));
        model.addAttribute("name", trainerService.searchTrainerName(info.getUsername()));

        return "content/trainer/trainerPage";
    }

    @PostMapping("/trainerUpdate")
    public String trainerUpdate(TrainerInsertDto trainerDto, @AuthenticationPrincipal User info,
                                @RequestParam("profileImage1") MultipartFile profileImage,
                                @RequestParam("imagePath1") MultipartFile[] imagePath) throws IOException {

        trainerService.updateTrainer(trainerDto, info.getUsername(), profileImage, imagePath);
        TrainerPageDto trainer = trainerService.searchTrainerName(info.getUsername());

        return "redirect:/trainer/trainerUpdate/" + trainer.getTrainerId();
    }

    // 트레이너 탈퇴
    @PostMapping("/trainerDelete")
    public String trainerDelete(TrainerInsertDto trainerDto, @AuthenticationPrincipal User info) {
        trainerService.deleteTrainer(trainerDto, info.getUsername());

        return "redirect:/logout";
    }

    // 트레이너 상세페이지
    @GetMapping("/trainer/{trainerId}")
    public String viewTrainer(@PathVariable("trainerId") int trainerId, @AuthenticationPrincipal User info, Model model) {
        if(info != null) {
            model.addAttribute("profile_image", fileService.getSingeProfile(info.getUsername()));
        }

        model.addAttribute("head_title", "트레이너 상세페이지");
        model.addAttribute("session", info);
        model.addAttribute("trainer", trainerService.searchTrainer(trainerId));
        //상세페이지 안보이면 여기임
        model.addAttribute("review", reviewService.showReview(trainerId));
        model.addAttribute("stars", reviewService.getStarAvgAndCnt(trainerId));

        model.addAttribute("product", trainerService.getProduct(trainerId));
        model.addAttribute("cert", trainerService.getCert(trainerId));
        model.addAttribute("career", trainerService.getCareer(trainerId));
        model.addAttribute("imageList", trainerService.getTrainerImage(trainerId));
        model.addAttribute("name", trainerService.searchTrainerName(info.getUsername()));

        return "content/trainer/trainerDetailPage";
    }

    // 트레이너별 리뷰 상세페이지
    @GetMapping("/trainer/review/{trainerId}")
    public String viewTrainerReview(@PathVariable("trainerId") int trainerId, @AuthenticationPrincipal User info, Model model,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                    @RequestParam(value = "filter", defaultValue = "0") int filter) {
        if(info != null) {
            model.addAttribute("profile_image", fileService.getSingeProfile(info.getUsername()));
        }

        model.addAttribute("head_title", "트레이너 리뷰페이지");
        model.addAttribute("session", info);
        model.addAttribute("name", trainerService.searchTrainerName(info.getUsername()));

        model.addAttribute("stars", reviewService.getStarAvgAndCnt(trainerId));
        model.addAttribute("trainer", trainerService.searchTrainer(trainerId));

        // 페이징을 위한 데이터 조회
        List<ReviewDto> review = reviewService.showReviewAll(page, pageSize, filter, trainerId);
        model.addAttribute("review", review);
        int totalReview = reviewService.getStarAvgAndCnt(trainerId).getReviewCnt();

        model.addAttribute("currentPage", page);
        model.addAttribute("reviewPageSize", pageSize);
        model.addAttribute("filter", filter);


        int totalPages = (int) Math.ceil((double) totalReview / pageSize);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("pageNumbers", pageNumbers);

        // Pre-calculate previous and next page numbers
        int prevPage = (page > 1) ? page - 1 : 1;
        int nextPage = (page < totalPages) ? page + 1 : totalPages;

        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage", nextPage);


        return "content/trainer/trainerReviewPage";
    }
    
    // 트레이너 찾기
    @GetMapping("/trainer/trainerSearch")
    public String viewTrainerSearch(Model model, @AuthenticationPrincipal User info,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "pageSize", defaultValue = "6") int pageSize,
                                    @RequestParam(value = "filter", defaultValue = "-1") int filter,
                                    @RequestParam(value = "address", defaultValue = "주소를 입력하세요") String address,
                                    @RequestParam(value = "secondWord", defaultValue = "-") String secondWord) {

        if (info != null) {
            model.addAttribute("profile_image", fileService.getSingeProfile(info.getUsername()));
            model.addAttribute("name", trainerService.searchTrainerName(info.getUsername()));
        }
        model.addAttribute("head_title", "트레이너 찾기");
        model.addAttribute("userId", info);


        if((!address.equals("주소를 입력하세요"))) {
            // Separate the second word from the address using space as the delimiter
            String[] addressParts = address.split(" ");
            secondWord = addressParts[1];

        }
        model.addAttribute("secondWord", secondWord);

        // 페이징을 위한 데이터 조회
        List<TrainerSearchDto> trainers = trainerService.searchAllTrainer(page, pageSize, filter, secondWord);
        int totalTrainers = trainerService.getTrainerCnt(filter, secondWord);

        model.addAttribute("trainer", trainers);
        model.addAttribute("currentPage", page);
        model.addAttribute("trainersPerPage", pageSize);
        model.addAttribute("filter", filter);

        // Set the address and secondWord variables in the model
        model.addAttribute("address", address);

        int totalPages = (int) Math.ceil((double) totalTrainers / pageSize);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("pageNumbers", pageNumbers);

        // Pre-calculate previous and next page numbers
        int prevPage = (page > 1) ? page - 1 : 1;
        int nextPage = (page < totalPages) ? page + 1 : totalPages;

        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage", nextPage);

        return "content/trainer/trainerSearch";
    }

    // 트레이너 관리페이지
    @GetMapping("/trainer/trainerControl")
    public String trainerControl(Model model, @AuthenticationPrincipal User info){

        if(info != null) {
            model.addAttribute("profile_image", fileService.getSingeProfile(info.getUsername()));
        }

        model.addAttribute("head_title", "트레이너 관리페이지");
        model.addAttribute("userId", info);
        model.addAttribute("timetable", trainerService.getTimetable(info.getUsername()));
        model.addAttribute("name", trainerService.searchTrainerName(info.getUsername()));

        return "content/trainer/trainerPage2";
    }
    @PostMapping("/trainer/ptTimetableRegister")
    public String timetableRegister(Model model, @AuthenticationPrincipal User info, TimetableInsertDto timetable){
        //model.addAttribute("userId", info);

        System.out.println("안녕하세요"+timetable.getTType());
        trainerService.registerTimetable(timetable, info.getUsername());

        return "redirect:/trainer/trainerControl";
    }
    @PostMapping("/trainer/ptTimetableDelete")
    public String timetableDelete(@RequestParam("tIdx") int tIdx){
        trainerService.deleteTimetable(tIdx);

        return "redirect:/trainer/trainerControl";
    }

    // 트레이너별 결제 회원 조회
    @GetMapping("/trainer/ptUserInfo")
    public String ptUserInfo(Model model, @AuthenticationPrincipal User info,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                             @RequestParam(value = "ptUserName", defaultValue = "-") String ptUserName){

        if(info != null) {
            model.addAttribute("profile_image", fileService.getSingeProfile(info.getUsername()));
        }
        TrainerPageDto trainerId = trainerService.searchTrainerName(info.getUsername());

        model.addAttribute("head_title", "트레이너 관리페이지");
        model.addAttribute("userId", info);
        model.addAttribute("ptUser", trainerService.getMyPtUserInfo(page, pageSize, trainerId.getTrainerId(), ptUserName));
        model.addAttribute("name", trainerId);
        model.addAttribute("ptUserName", ptUserName);

        // 페이징을 위한 데이터 조회
        int totalPtUsers = trainerService.getPtUserCnt(info.getUsername(), ptUserName);

        model.addAttribute("currentPage", page);
        model.addAttribute("trainersPerPage", pageSize);

        int totalPages = (int) Math.ceil((double) totalPtUsers / pageSize);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("pageNumbers", pageNumbers);

        // Pre-calculate previous and next page numbers
        int prevPage = (page > 1) ? page - 1 : 1;
        int nextPage = (page < totalPages) ? page + 1 : totalPages;

        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage", nextPage);

        return "content/trainer/trainerPage3";
    }

    // 트레이너 찾기 페이지 주소 모달
    @GetMapping("/address-search")
    public String addressTest() {
        return "content/trainer/addressModal";
    }

    // 트레이너 등록, 수정 페이지 주소 모달
    @GetMapping("/address-search2")
    public String addressTestForRegister() {
        return "content/trainer/addressModalForRegister";
    }

}
package com.twinkle.JakSim.controller.review;

import com.twinkle.JakSim.model.dto.review.ReviewDto;
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

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final FileService fileService;
    private final TrainerService trainerService;

    @GetMapping("/registerReview/{tid}")
    public String registerMyReview(Model model, @PathVariable("tid") String tid, @AuthenticationPrincipal User info) {
        model.addAttribute("head_title", "리뷰 등록");

        return "content/review/registerReview";
    }

    // 리뷰 수정
    @GetMapping("/review/editReview/{reviewIdx}")
    public String editReview(@PathVariable("reviewIdx") int reviewIdx, Model model, @AuthenticationPrincipal User info,
                             ReviewDto ReviewDto) {
        if(info != null) {
            model.addAttribute("profile_image", fileService.getSingeProfile(info.getUsername()));
        }

        model.addAttribute("head_title", "리뷰 수정");
        model.addAttribute("review", reviewService.showMyReview(info.getUsername(), reviewIdx));

        return "content/review/editReview";
    }

    @PostMapping("/editMyReview")
    public String editMyReview(@AuthenticationPrincipal User info, ReviewDto ReviewDto) {
        reviewService.editReview(ReviewDto, info.getUsername());

        return "redirect:/trainer/trainerSearch";
    }

//    @PostMapping("/deleteReview")
//    public String deleteMyReview(@AuthenticationPrincipal User info) {
//        reviewService.deleteReview(info.getUsername());
//
//        return "redirect:/trainer/trainerSearch";
//    }

    @GetMapping("/review/list")
    public String getList(){
        return "content/review/review_list";
    }

}

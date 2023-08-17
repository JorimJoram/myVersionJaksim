package com.twinkle.JakSim.controller.review;

import com.twinkle.JakSim.model.dto.review.ReviewDto;
import com.twinkle.JakSim.model.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review/api")
public class ReviewRestApi {
    private final ReviewService reviewService;
    @PostMapping("/register/{tid}")
    public int registerReview(@PathVariable("tid") String tid, @AuthenticationPrincipal User user, @RequestBody ReviewDto dto){
        return reviewService.insertReview(tid, dto, user.getUsername());
    }

    @DeleteMapping("/delete/{r_idx}")
    public int deleteReview(@PathVariable("r_idx") int r_idx){
        return reviewService.deleteReview(r_idx);
    }

    @PutMapping("/modify/{r_idx}")
    public int modifyReview(@PathVariable("r_idx") int r_idx, @RequestBody ReviewDto dto){
        return reviewService.updateReview(r_idx, dto);
    }

    @GetMapping("/get/{tid}")
    public ReviewDto getSingleReviewByTid(@PathVariable("tid") String tid){
        return reviewService.getReviewByTid(tid);
    }

    @GetMapping("/get/all")
    public List<ReviewDto> getReviewList(@RequestParam(defaultValue = "0") int tp_idx,
                                         @RequestParam(defaultValue = "0") int ut_idx,
                                         @RequestParam(defaultValue = "false") boolean sort,
                                         @RequestParam(defaultValue = "0") int star){
        List<ReviewDto> reviewList = new ArrayList<>();
        if(((tp_idx == 0) ^ (ut_idx == 0))){
            reviewList = (tp_idx == 0) ? reviewService.getReviewListByUtIdx(ut_idx, sort, star) : reviewService.getReviewListByTpIdx(tp_idx, sort, star);
        }
        return reviewList;
    }

    @GetMapping("/get/user")
    public List<ReviewDto> getReviewListByUsername(@RequestParam(defaultValue = "false") boolean myPage,
                                                   @RequestParam(defaultValue = "false") boolean sort,
                                                   @RequestParam(defaultValue = "0") int star,
                                                   @AuthenticationPrincipal User user){
        return reviewService.getReviewListByUsername(user.getUsername(), myPage, sort, star);
    }

    @GetMapping("/get/avg")
    public double getAvgReview(@RequestParam(defaultValue = "0") int ut_idx,
                               @RequestParam(defaultValue = "0") int tp_idx, @AuthenticationPrincipal User user){
        return reviewService.getAvgReview(ut_idx, tp_idx);
    }

    @GetMapping("/get/count")
    public int getReviewSize(@RequestParam(defaultValue = "0") int ut_idx,
                             @RequestParam(defaultValue = "0") int tp_idx, @AuthenticationPrincipal User user){
        return 0;
    }
}

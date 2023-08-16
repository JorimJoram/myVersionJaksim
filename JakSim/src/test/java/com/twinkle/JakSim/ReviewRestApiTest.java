package com.twinkle.JakSim;

import com.twinkle.JakSim.model.dto.review.ReviewDto;
import com.twinkle.JakSim.model.service.review.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Review 관련 CUD 단위 테스트입니다.
 */
@SpringBootTest
public class ReviewRestApiTest {
    @Autowired
    private ReviewService reviewService;

    @Test
    void Create(){
        ReviewDto dto = new ReviewDto();
        dto.setContent("Test at Spring");
        dto.setStar(3);
        dto.setUserId("west1");
        dto.setTid("T4d69e2b76b90283a2d4");

        String result = (reviewService.insertReview(dto.getTid(), dto, dto.getUserId()) > 0) ? "success" : "fail";
        System.out.println(result);
    }

    @Test
    void Update(){
        ReviewDto dto = new ReviewDto();
        dto.setIdx(8);
        dto.setContent("TEster!@");
        dto.setStar(3);

        System.out.println(reviewService.updateReview(dto.getIdx(), dto));
    }

    @Test
    void Delete(){
        String result = (reviewService.deleteReview(5) > 0) ? "success" : "fail";
        System.out.println(result);
    }

    /**
     *  <h3>Optional 사용에 관련한 자료조사 결과</h3>
     *  <p>자료출처: https://homoefficio.github.io/2019/10/03/Java-Optional-바르게-쓰기/</p>
     *  <span>
     *      optional 인터페이스의 목적은 null값이 발생할 때 문제가 발생할 경우에 사용할 것을 권하고 있습니다. 하지만, RestApi의 결과는 null 값이라도 기본값이 들어있는채로 출력되기 때문에 optional 사용을 하지 않도록 합니다.
     *  </span>
     */
    @Test
    void getSingleReviewByTid(){
        ReviewDto dto = reviewService.getReviewByTid("T4d69e2b76b90283a2d4");
        System.out.println(dto.toString());
    }

    @Test
    void getReviewListByTpIdx(){
        List<ReviewDto> list = reviewService.getReviewListByTpIdx(11, false, 2);
        list.forEach((item) -> System.out.println(item.toString()));
    }

    @Test
    void getReviewListByUtIdx(){
        List<ReviewDto> list = reviewService.getReviewListByUtIdx(11, false, 0);
        list.forEach((item) -> System.out.println(item.toString()));
    }

    @Test
    void getReviewListByUsername(){
        List<ReviewDto> list = reviewService.getReviewListByUsername("west1", true, true, 3);
        list.forEach((item) -> System.out.println(item.toString()));
    }

    @Test
    void getReviewAvg(){
        System.out.println(reviewService.getAvgReview(12, 0));
        System.out.println(reviewService.getAvgReview(0, 11));
        System.out.println(reviewService.getAvgReview(11, 11));
    }
}
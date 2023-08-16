package com.twinkle.JakSim.controller.qna;

import com.twinkle.JakSim.model.dto.qna.QnADto;
import com.twinkle.JakSim.model.service.qna.QnAService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna/api")
public class QnARestApi {
    private final QnAService qnAService;

    @GetMapping("/get/myquestion")
    public List<QnADto> myQuestions(@AuthenticationPrincipal User user){
        return qnAService.getMyQuestions(user.getUsername());
    }
}

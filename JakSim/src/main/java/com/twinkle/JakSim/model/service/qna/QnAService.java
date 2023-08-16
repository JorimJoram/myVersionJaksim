package com.twinkle.JakSim.model.service.qna;

import com.twinkle.JakSim.model.dao.qna.QnADao;
import com.twinkle.JakSim.model.dto.qna.QnADto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnAService {
    private final QnADao qnADao;
    public List<QnADto> getMyQuestions(String username) {
        return qnADao.getMyQuestions(username);
    }
}

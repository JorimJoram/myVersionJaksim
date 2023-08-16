package com.twinkle.JakSim.model.service.timetable;

import com.twinkle.JakSim.model.dao.timetable.TimetableDao;
import com.twinkle.JakSim.model.dto.timetable.response.TimetableResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimetableService {

    private final TimetableDao timetableDao;

    public Optional<TimetableResponse> findMyTimetableRecent(String username){
        return timetableDao.findMyTimetableRecent(username);
    }
}

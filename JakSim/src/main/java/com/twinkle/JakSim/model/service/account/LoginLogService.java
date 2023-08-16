package com.twinkle.JakSim.model.service.account;

import com.twinkle.JakSim.model.dao.account.LoginLogDao;
import com.twinkle.JakSim.model.dto.account.LoginLogDto;
import com.twinkle.JakSim.model.dto.account.LoginLogStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginLogService {
    private final LoginLogDao loginLogDao;
    public int create(String userId, String ip) {
        LoginLogDto logDto = new LoginLogDto();

        logDto.setUser_id(userId);
        logDto.setIp(ip);

        return loginLogDao.create(logDto);
    }

    public LoginLogDto findByUsernameRecent(String username) {
        return loginLogDao.findByUsernameRecent(username);
    }

    public List<LoginLogDto> findByUsername(String username, int pageNum) {
        int pageSize = 20;
        return loginLogDao.findByUsername(username, pageNum, pageSize);
    }

    public int getAllAccess() {
        return loginLogDao.getAllAccess();
    }

    private String getDefaultStart(String start){
        if(start == null || start.isEmpty()){
            start = LocalDate.now().plusDays(-6).toString();
        }
        return start;
    }

    private String getDefaultEnd(String end){
        if(end == null || end.isEmpty()){
            end = LocalDate.now().plusDays(1).toString();
        }
        return end;
    }

    public List<LoginLogStat> getAmountDate(String start, String end) {
        return loginLogDao.getAmountDate(getDefaultStart(start), getDefaultEnd(end));
    }

    public List<LoginLogStat> getAmountSingleUser(String username, String start, String end) {
        return loginLogDao.getAmountSingleUser(username, getDefaultStart(start), getDefaultEnd(end));
    }

    public List<LoginLogStat> getAmountGroupUser(String start, String end, boolean order) {
        return loginLogDao.getAmountGroupUser(getDefaultStart(start), getDefaultEnd(end), order);
    }
}

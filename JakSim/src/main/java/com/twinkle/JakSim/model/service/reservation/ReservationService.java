package com.twinkle.JakSim.model.service.reservation;

import com.twinkle.JakSim.model.dao.payment.PaymentDao;
import com.twinkle.JakSim.model.dao.reservation.ReservationDao;
import com.twinkle.JakSim.model.dao.timetable.TimetableDao;
import com.twinkle.JakSim.model.dto.payment.response.PtCntDo;
import com.twinkle.JakSim.model.dto.reservation.request.ReservationRequest;
import com.twinkle.JakSim.model.dto.reservation.response.MyMember;
import com.twinkle.JakSim.model.dto.reservation.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimetableDao timetableDao;
    private final PaymentDao paymentDao;

    public PtCntDo register(String userId, ReservationRequest reservationDto) {
        PtCntDo ptCntDo = new PtCntDo();
        ReservationResponse reservationResponse = reservationDao.findReservation(userId, reservationDto.getTrainerId(),
                                                                                            reservationDto.getDate());

        boolean isPtTicket = paymentDao.isPtTicket(reservationDto.getP_idx());

        boolean isTimetable = timetableDao.isTimetable(reservationDto.getT_idx());

        if(reservationResponse.getRIdx() != 0) {
            ptCntDo.setReservationStatus(1);
            return ptCntDo;
//            return 1;
        } else if(!isPtTicket) {
            ptCntDo.setReservationStatus(2);
            return ptCntDo;
//            return 2;
        }
        else if(!isTimetable) {
            ptCntDo.setReservationStatus(3);
            return ptCntDo;
//            return 3;
        }

        try {
            boolean register = reservationDao.register(userId, reservationDto.getT_idx(), reservationDto.getP_idx());

            if(register)
                ptCntDo = paymentDao.decreasePt(reservationDto.getPtCnt(), reservationDto.getP_idx());
            else {
                ptCntDo.setReservationStatus(4);
                return ptCntDo;
//                return 4;
            }
        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }

        timetableDao.decreaseTPeople(reservationDto.getT_idx(), 0);

        ptCntDo.setReservationStatus(5);

        return ptCntDo;
    }

    public PtCntDo delete(int pIdx, int rIdx) {
        timetableDao.increaseTPeople(rIdx);

        if(reservationDao.delete(rIdx)) {
            return paymentDao.increaseCnt(pIdx);
        }

        timetableDao.decreaseTPeople(rIdx, 1);

        return null;
    }

    public ReservationResponse findReservation(String userId, String trainerId, LocalDate tDate) {
        ReservationResponse reservationResponse = new ReservationResponse();

        reservationResponse = reservationDao.findReservation(userId, trainerId, tDate);

        return reservationResponse;
    }

    public List<MyMember> findMyReservation(int tIdx) {
        List<MyMember> reservationResponse = reservationDao.findMyReservation(tIdx);

        return reservationResponse;
    }
}

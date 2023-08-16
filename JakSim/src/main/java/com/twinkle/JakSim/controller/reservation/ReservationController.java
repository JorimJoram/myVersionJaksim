package com.twinkle.JakSim.controller.reservation;

import com.twinkle.JakSim.model.dto.payment.response.PtCntDo;
import com.twinkle.JakSim.model.dto.reservation.request.IsReservationRequest;
import com.twinkle.JakSim.model.dto.reservation.request.ReservationRequest;
import com.twinkle.JakSim.model.dto.reservation.response.MyMember;
import com.twinkle.JakSim.model.dto.reservation.response.ReservationResponse;
import com.twinkle.JakSim.model.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/register")
    public ResponseEntity<PtCntDo> register(@AuthenticationPrincipal User user,
                                                        @Valid @RequestBody ReservationRequest reservationRequest) {
        PtCntDo response = new PtCntDo();
        LocalDate today = LocalDate.now();

        if(reservationRequest.getDate().compareTo(today) < 0) {
            System.out.println("입력된 날이 더 과거입니다.");
        } else {
            response = reservationService.register(user.getUsername(), reservationRequest);
        }

        if(response.getReservationStatus() == 0) {
            System.out.println("지난 날에 대한 예약은 할 수 없습니다.");
        } else if(response.getReservationStatus() == 1) {
            System.out.println("이미 등록된 예약이 있습니다.");
        } else if (response.getReservationStatus() == 2) {
            System.out.println("이미 등록된 예약이 있습니다.");
        } else if (response.getReservationStatus() == 3) {
            System.out.println("해당 시간표는 존재하지 않습니다.");
        } else if (response.getReservationStatus() == 4) {
            System.out.println("예약이 올바르게 되지 않았습니다.");
        }

        // 0: 지난 날, 1: 등록된 예약 O, 2: 사용가능한 PT권 X, 일정 존재 X, 4: 예기치 못한 에러 발생, 5: 예약 완료
        return ResponseEntity.ok().body(response);
    }

    // 5
    @PostMapping("/search")
    public ResponseEntity<ReservationResponse> reservation(@AuthenticationPrincipal User user,
                                                           @Valid @RequestBody IsReservationRequest resCheckRequest) {
        ReservationResponse response = reservationService.findReservation(user.getUsername(), resCheckRequest.getTrainerId(), resCheckRequest.getDt());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/cancle/{pIdx}/{rIdx}")
    public ResponseEntity<PtCntDo> resCancle(@AuthenticationPrincipal User user, @PathVariable("pIdx") int pIdx,
                              @PathVariable("rIdx") int rIdx) {
        PtCntDo response = reservationService.delete(pIdx, rIdx);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search/{tIdx}")
    public ResponseEntity<List<MyMember>> findMyReservation(@PathVariable("tIdx") int tIdx) {
        List<MyMember> response = reservationService.findMyReservation(tIdx);

        return ResponseEntity.ok().body(response);
    }
}

package com.twinkle.JakSim.controller.trainer;

import com.twinkle.JakSim.model.dto.trainer.response.TrainerDetailResponse;
import com.twinkle.JakSim.model.service.trainer.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainer")
@RequiredArgsConstructor
public class TrainerRestController {

    private final TrainerService trainerService;

    // 2
    @GetMapping("/briefInfo/{trainerId}")
    public ResponseEntity<TrainerDetailResponse> TrainerBreif(@PathVariable("trainerId") String trainerId) {
        TrainerDetailResponse response = trainerService.findTrainerBreif(trainerId);

        return ResponseEntity.ok(response);
    }
}

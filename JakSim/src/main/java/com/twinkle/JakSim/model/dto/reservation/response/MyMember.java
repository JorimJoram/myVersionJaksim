package com.twinkle.JakSim.model.dto.reservation.response;

import lombok.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyMember {

    private String id;

    private String name;

    private int gender;
}

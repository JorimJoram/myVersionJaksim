package com.twinkle.JakSim.model.dto.account;

import lombok.Data;

@Data
public class OAuthResponseDto {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private Boolean isMember;
}

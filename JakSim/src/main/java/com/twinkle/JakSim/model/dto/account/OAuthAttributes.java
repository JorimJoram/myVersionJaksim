package com.twinkle.JakSim.model.dto.account;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@Builder
@RequiredArgsConstructor
public class OAuthAttributes {
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String picture;
    private final String gender;
    private final String gage;

    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes){
        OAuthAttributes result = ("naver".equals(registrationId)) ? ofNaver("id", attributes) : ofKakao("id", attributes);
        return result;
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String)response.get("nickname"))
                .email((String)response.get("email"))
                .picture((String)response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String id, Map<String, Object> attributes) {
        OAuthAttributes o = null;
        return o;
    }

    public UserDto toEntity(){
        UserDto user = new UserDto();
        user.setName(name);
        user.setEmail(email);
        //user.setGender(gender);
        return user;
    }

}

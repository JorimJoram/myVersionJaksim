package com.twinkle.JakSim.config.auth;

import com.twinkle.JakSim.model.service.account.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomUserDetailService customUserDetailService;
    private final FileService fileService;
    private final HttpSession session;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String)authentication.getCredentials(); //내가 적은 값이 들어오는 거임

        UserDetails user = customUserDetailService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Invalid user Password");
        }

        session.setAttribute("session_user", user);
        session.setAttribute("session_profile", fileService.getSingeProfile(username));
        session.setAttribute("isTrainer", user.getAuthorities().toString().equals("[TRAINER]") ? user.getAuthorities() : null);
        session.setAttribute("isAdmin", user.getAuthorities().toString().equals("[ADMIN]") ? user.getAuthorities() : null);

        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class); //요기있네?!
    }
}

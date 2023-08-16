package com.twinkle.JakSim.config.auth;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException{

        if(exception instanceof UsernameNotFoundException){
            redirectStrategy.sendRedirect(request, response, "/login?errorCode=not_found");
        } else if (exception instanceof BadCredentialsException) {
            redirectStrategy.sendRedirect(request, response, "/login?errorCode=bad_credentials");
        }else if (exception instanceof AccountExpiredException) {
            redirectStrategy.sendRedirect(request, response, "/login?errorCode=account_expired");
        }else{
            redirectStrategy.sendRedirect(request, response, "/login?errorCode=unknown");
        }
    }
}

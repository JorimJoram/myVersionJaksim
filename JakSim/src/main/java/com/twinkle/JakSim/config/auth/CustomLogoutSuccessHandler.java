package com.twinkle.JakSim.config.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try{
            request.getSession().invalidate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/");
    }
}

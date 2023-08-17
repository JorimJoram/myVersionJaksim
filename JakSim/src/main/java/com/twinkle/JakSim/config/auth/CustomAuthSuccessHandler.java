package com.twinkle.JakSim.config.auth;

import com.twinkle.JakSim.model.service.account.LoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final LoginLogService loginLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        clearSession(request);

        String prevPage = (String) request.getSession().getAttribute("prevPage");
        if(prevPage == null || prevPage.isEmpty() || prevPage.contains("/account") || prevPage.contains("/find")){
            prevPage = "/";
        }

        setDefaultTargetUrl(prevPage);

        loginLogService.create(authentication.getName(), request.getRemoteAddr());
        request.getSession().removeAttribute("prevPage");

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private void clearSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
package com.twinkle.JakSim.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private CustomAuthSuccessHandler customAuthSuccessHandler;
    @Autowired
    private CustomAuthFailureHandler customAuthFailureHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeHttpRequests().antMatchers( "/",
                        "/reservation/**",
                         "/trainer/trainerSearch", "/scheduler/**",
                        "/javascript/**", "/css/**", "/image/**", "/email/**", "/account/api/**", "/ws/chat/**", "/chat/**").permitAll()
                .antMatchers("/login/**", "/find/**", "/account/**").hasAnyRole("ANONYMOUS")
                .antMatchers("/trainer/trainerRegister/**").hasAuthority("USER")
                .antMatchers("/trainer/trainerUpdate/**", "/trainer/trainerControl", "/trainer/ptUserInfo", "/trainerUpdate/**", "/trainerDelete").hasAnyAuthority("TRAINER")
                .antMatchers("/session/**", "/info/**").hasAuthority("ADMIN")
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/action")
                .successHandler(customAuthSuccessHandler)
                .failureHandler(customAuthFailureHandler);
        http.logout()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/");
        http.sessionManagement(
                s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS) //STATELESS -> JWT 와 같은 클라이언트 토큰, ALWAYS -> 우리같은 세션 방식에서 사용
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::changeSessionId) //로그인 될 때마다 새로운 SessionID 생성
                        .maximumSessions(1) //최대 몇 개의 계정을 접속시킬 것인지 -> 1 잘 됨
                        .maxSessionsPreventsLogin(false) //false -> 초과된 사용자에게 로그인 허용하지 않음, true -> 초과된 사용자 로그인 허용 -> 이전 사용자 세션을 만료시킴
                        .expiredUrl("/")//세션 만료 시 보낼 페이지
        );
        return http.build();
    }
}
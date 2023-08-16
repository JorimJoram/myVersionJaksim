package com.twinkle.JakSim.config.auth;

import com.twinkle.JakSim.model.dto.account.UserDto;
import com.twinkle.JakSim.model.dao.account.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<UserDto> siteUser = userDao.findByUserId(username);

        if(siteUser.isEmpty()){
            throw new UsernameNotFoundException("user not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        switch (siteUser.get().getRole()){
            case 0:
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
                break;
            case 1:
                authorities.add(new SimpleGrantedAuthority("USER"));
                break;
            case 2:
                authorities.add(new SimpleGrantedAuthority("TRAINER"));
                break;
        }

        return new User(siteUser.get().getId(), siteUser.get().getPw(), authorities);
    }

}

package ru.myagkiy.SpringSecurity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.myagkiy.SpringSecurity.services.PersonDetailService;

import java.util.Collections;


@RequiredArgsConstructor
@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private final PersonDetailService personDetailService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails userDetails = personDetailService.loadUserByUsername(username);
        String password = authentication.getCredentials().toString();
        if(!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("Неверный пароль");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

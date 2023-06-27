package ru.myagkiy.SpringSecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.myagkiy.SpringSecurity.services.PersonDetailService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PersonDetailService personDetailService;

    //Аутентификация пользователя
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(personDetailService);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //отключаем защиту межсайтовой подделки запросов
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        //Разрешаем не авторизованному пользователю доступ на страницу login
                        auth -> auth.requestMatchers("/auth/login")
                                .permitAll()
                                // для всех остальных страниц нужна аутентификация
                                .anyRequest()
                                .authenticated())
                //указываем адрес кастомной страницы аутентификации
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        //указываем адрес страницы на которую будет отправлена форма с login.html
                        .loginProcessingUrl("/process_login")
                        //если пользовательские данные были введены верно, то перенаправляем на страницу
                        .defaultSuccessUrl("/hello", true)
                        //если пользовательские данные были введены неверно, то перенаправляем на страницу
                        .failureUrl("/auth/login?error"))
                .build();
    }

    //Указываем способ шифрования пароля пользователя(сейчас нет шифрования)
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}


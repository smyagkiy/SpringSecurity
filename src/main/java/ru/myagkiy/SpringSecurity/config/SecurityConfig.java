package ru.myagkiy.SpringSecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.myagkiy.SpringSecurity.services.PersonDetailService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity // нужна для работы @PreAuthorize
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
                //.csrf(AbstractHttpConfigurer::disable)
                //Разрешаем не авторизованному пользователю доступ на страницу login
                .authorizeHttpRequests(auth -> auth
                        //ограничение доступа по роли на уровне метода в коде
                        //.requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/auth/login", "/auth/registration")
                        .permitAll()
                        .anyRequest().authenticated())
                        //.anyRequest().hasAnyRole("USER","ADMIN"))
                        // для всех остальных страниц нужна аутентификация
                //указываем адрес кастомной страницы аутентификации
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        //указываем адрес страницы на которую будет отправлена форма с login.html
                        .loginProcessingUrl("/process_login")
                        //если пользовательские данные были введены верно, то перенаправляем на страницу
                        .defaultSuccessUrl("/hello", true)
                        //если пользовательские данные были введены неверно, то перенаправляем на страницу
                        .failureUrl("/auth/login?error"))
                //выход пользователя из профиля и перенаправление на страницу с авторизацией
                .logout(log -> log
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login"))
                .build();
    }

    //Указываем способ шифрования пароля пользователя
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


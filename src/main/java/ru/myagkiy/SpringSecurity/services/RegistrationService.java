package ru.myagkiy.SpringSecurity.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.myagkiy.SpringSecurity.model.Person;
import ru.myagkiy.SpringSecurity.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final PersonRepository personRepository;
    //шифруем пароль при регистарции пользователя
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registration(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }
}

package ru.myagkiy.SpringSecurity.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.myagkiy.SpringSecurity.model.Person;
import ru.myagkiy.SpringSecurity.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final PersonRepository personRepository;

    @Transactional
    public void registration(Person person){
        personRepository.save(person);
    }
}

package ru.myagkiy.SpringSecurity.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.myagkiy.SpringSecurity.model.Person;
import ru.myagkiy.SpringSecurity.services.PersonDetailService;
@RequiredArgsConstructor
@Component
public class PersonValidator implements Validator {
    private final PersonDetailService personDetailService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try{
            personDetailService.loadUserByUsername(person.getName());
        }catch (UsernameNotFoundException ignored){
            return; // пользователь не найден
        }
        errors.rejectValue("username", "Человек с таким именем пользователя существует");
    }
}

package ru.myagkiy.SpringSecurity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.myagkiy.SpringSecurity.model.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person,Integer> {
    Optional<Person> findByName(String name);
}

package ru.myagkiy.SpringSecurity.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Person")
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int yearOfBeard;
}

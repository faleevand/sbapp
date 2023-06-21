package com.example.myapp.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Field cant be empty")
    @Column(name = "name")
    private String name;

    @Min(value = 0, message = "It must be number more than 0")
    @Max(value = 100, message = "It must be number less than 100")
    @Column(name = "age")
    private byte age;

    @Column(name = "surname")
    @NotBlank(message = "Field cant be empty")
    private String surname;


}

package com.example.myapp.model;


import jakarta.persistence.*;
import lombok.Data;

//import javax.persistence.*;
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

   @NotBlank(message = "One more time")
    @Column(name = "name")
    private String name;

   @Min(value = 0, message = "One more time")
    @Max(value = 130, message = "One more time")
    @Column(name = "age")
    private int age;

    @Column(name = "surname")
   @NotBlank(message = "One more time")
    private String surname;

}

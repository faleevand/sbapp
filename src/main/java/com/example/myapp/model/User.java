package com.example.myapp.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;


@Data
@Entity


@Table(name = "u_user", uniqueConstraints= @UniqueConstraint(columnNames={"username"}))

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 6, message = "Не меньше 6 знаков")
    private String username;

    @Size(min = 6, message = "Не меньше 6 знаков")
    private String password;

    private String surname;

    @Min(value = 1)
    @Max(value = 100)
    private byte age;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "roles=" + roles +
                '}';
    }


//    public User() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
//}

//@Entity
//@Table(name = "user")
//public class User {
//
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @NotBlank(message = "Field cant be empty")
//    @Column(name = "name")
//    private String name;
//
//    @Min(value = 0, message = "It must be number more than 0")
//    @Max(value = 100, message = "It must be number less than 100")
//    @Column(name = "age")
//    private byte age;
//
//    @Column(name = "surname")
//    @NotBlank(message = "Field cant be empty")
//    private String surname;
//
//
//}
}
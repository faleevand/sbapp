package com.myapp.sprbootapp.repository;

import com.myapp.sprbootapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

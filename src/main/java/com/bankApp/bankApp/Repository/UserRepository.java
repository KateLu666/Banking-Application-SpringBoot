package com.bankApp.bankApp.Repository;

import com.bankApp.bankApp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findByEmail(String email);
}

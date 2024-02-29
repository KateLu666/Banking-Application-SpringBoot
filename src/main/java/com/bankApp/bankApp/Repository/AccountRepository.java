package com.bankApp.bankApp.Repository;

import com.bankApp.bankApp.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer>{
    Optional<Account> findByUser_UserId(int userId);
}

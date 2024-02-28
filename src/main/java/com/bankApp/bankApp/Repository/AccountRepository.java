package com.bankApp.bankApp.Repository;

import com.bankApp.bankApp.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer>{
}

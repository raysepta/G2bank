package com.example.g2bank.repository;

import com.example.g2bank.model.User;
import com.example.g2bank.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {
    public UserDetail getBalanceByUser(User user);
}

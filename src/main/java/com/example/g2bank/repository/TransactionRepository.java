package com.example.g2bank.repository;

import com.example.g2bank.model.Transaction;
import com.example.g2bank.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByUserDetail(UserDetail userDetail);
}

package com.example.g2bank.service;

import com.example.g2bank.dto.*;
import com.example.g2bank.model.*;
import com.example.g2bank.repository.TransactionRepository;
import com.example.g2bank.repository.UserDetailRepository;
import com.example.g2bank.util.CodeGenerator;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<TransactionResponseDto> getTransactionListByUserDetail(UserDetail userDetail) {
       List<Transaction> transactionList = transactionRepository.findAllByUserDetail(userDetail);
        List<TransactionResponseDto> transactionResponseDtoList = new ArrayList<>();
        for (Transaction transaction: transactionList) {
            TransactionResponseDto temp = TransactionResponseDto.builder()
                    .id(transaction.getId())
                    .transactionType(transaction.getTransactionType())
                    .trxDate(transaction.getTrxDate())
                    .total(transaction.getTotal())
                    .build();

            transactionResponseDtoList.add(temp);
        }
        return transactionResponseDtoList;
    }
    @Transactional
    public WithdrawResponseDto withdraw(User logginUser, WithdrawRequestDto withdrawRequestDto) {
        /**
         * 1. get balance tidak boleh kurang dari amount
         * 2. Bikin transaction withdraw
         * 3. Sum balance(balance = balance - withdraw)
         */

        UserDetail ud = userDetailRepository.getBalanceByUser(logginUser);
        if (ud == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");

        }

        if (ud.getBalance() < withdrawRequestDto.getAmountOfMoney()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }

        ud.setBalance(
                ud.getBalance() - withdrawRequestDto.getAmountOfMoney()
        );

        Transaction trx = new Transaction();
        trx.setUserDetail(ud);
        trx.setTrxDate(new Date());
        trx.setTransactionType(TransactionType.WITHDRAW);
        trx.setTotal(withdrawRequestDto.getAmountOfMoney());
        transactionRepository.save(trx);
        ud = userDetailRepository.save(ud);
        return WithdrawResponseDto.builder()
                .balance(ud.getBalance())
                .build();
    }

    @Transactional
    public DepositResponseDto deposit (User logginUser, DepositRequestDto depositRequestDto) {
        /**
         * 1. get balance boleh kurang dari amount
         * 2. Bikin transaction deposit
         * 3. Sum balance(balance = balance + deposit)
         */

        UserDetail ud = userDetailRepository.getBalanceByUser(logginUser);
        if (ud == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");

        }

        ud.setBalance(
                ud.getBalance() + depositRequestDto.getAmountOfMoney()
        );

        Transaction trx = new Transaction();
        trx.setUserDetail(ud);
        trx.setTrxDate(new Date());
        trx.setTransactionType(TransactionType.DEPOSIT);
        trx.setTotal(depositRequestDto.getAmountOfMoney());
        transactionRepository.save(trx);
        ud = userDetailRepository.save(ud);
        return DepositResponseDto.builder()
                .balance(ud.getBalance())
                .build();
    }
}

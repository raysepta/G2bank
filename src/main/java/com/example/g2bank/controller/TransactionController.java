package com.example.g2bank.controller;

import com.example.g2bank.dto.*;
import com.example.g2bank.model.User;
import com.example.g2bank.model.UserDetail;
import com.example.g2bank.repository.UserDetailRepository;
import com.example.g2bank.repository.UserRepository;
import com.example.g2bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transaction")
    public ResponseEntity<List<TransactionResponseDto>> getTransaction(Principal principal) {
        User user = userRepository.getUserByEmail(principal.getName());
        if (user != null) {
            UserDetail ud = userDetailRepository.getBalanceByUser(user);
            return ResponseEntity.ok(transactionService.getTransactionListByUserDetail(ud));
        }
        return null;
    }

    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawResponseDto> withdraw(@RequestBody WithdrawRequestDto withdrawRequestDto, Principal principal) {
        User user = userRepository.getUserByEmail(principal.getName());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
        return ResponseEntity.ok(transactionService.withdraw(user, withdrawRequestDto));
    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponseDto> deposit(@RequestBody DepositRequestDto depositRequestDto, Principal principal) {
        User user = userRepository.getUserByEmail(principal.getName());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
        return ResponseEntity.ok(transactionService.deposit(user, depositRequestDto));
    }

}

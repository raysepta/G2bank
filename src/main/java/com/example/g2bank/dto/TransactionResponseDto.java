package com.example.g2bank.dto;

import com.example.g2bank.model.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TransactionResponseDto {
    private Integer id;
    private Date trxDate;
    private TransactionType transactionType;
    private Integer total;

}

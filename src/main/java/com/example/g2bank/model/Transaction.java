package com.example.g2bank.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date trxDate = new Date();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


    @Column(nullable = false)
    private Integer total;

    @ManyToOne
    @JoinColumn(name = "id_user_detail", nullable = false)
    private UserDetail userDetail;



}

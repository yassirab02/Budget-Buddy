package com.yassir.budgetbuddy.transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.goal.Goal;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.wallet.Wallet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction extends BaseEntity {

    private String message;
    private String description; // Optional description of the transaction

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "source_wallet_id", nullable = false)
    private Wallet sourceWallet; // The wallet from which the money is transferred

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "destination_wallet_id", nullable = true)
    private Wallet destinationWallet; // The wallet to which the money is transferred (nullable for Goal transfers)


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = true)
    private User receiver;


    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = true) // Nullable if it's a wallet-to-wallet transaction
    private Goal goal; // The goal to which money is transferred

}

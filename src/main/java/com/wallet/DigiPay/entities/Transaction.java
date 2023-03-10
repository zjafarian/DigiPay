package com.wallet.DigiPay.entities;

import com.wallet.DigiPay.base.BaseModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "t_transaction")
public class Transaction extends BaseModel {




    @Column(name = "tra_description")
    private String description;


    @Enumerated(EnumType.STRING)
    @Column(name = "tra_type")
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "tra_status")
    private TransactionStatus transactionStatus;

    @Column(name = "tra_source")
    private String source;

    @Column(name = "tra_destination")
    private String destination;

    @Column(name = "tra_amount")
    @NotNull
    private Double amount;

    @Column(name = "tra_wallet_balance")
    @NotNull
    private Double walletBalance;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tra_user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tra_wallet_id")
    private Wallet wallet;

    public Transaction() {
    }

    public Transaction(String description,
                       TransactionType transactionType,
                       TransactionStatus transactionStatus,
                       String source,
                       String destination,
                       Double amount,
                       Double walletBalance,
                       User user,
                       Wallet wallet) {

        this.description = description;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.walletBalance = walletBalance;
        this.user = user;
        this.wallet = wallet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Double walletBalance) {
        this.walletBalance = walletBalance;
    }
}

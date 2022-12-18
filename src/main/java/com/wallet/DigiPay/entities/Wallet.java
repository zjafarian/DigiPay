package com.wallet.DigiPay.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "t_wallet")
public class Wallet extends BaseModel{

    @Column(name = "wal_title")
    private String title;

    @Column(name = "wal_balance")
    private Double balance;

    @Column(name = "wal_number")
    private String walletNumber;


    @Column(name = "wal_is_active")
    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wal_user_id")
    private User user;





    public Wallet() {
    }

    public Wallet(User user,String title, Double balance, Boolean isActive) {
        walletNumber = UUID.randomUUID().toString();
        this.user = user;
        this.title = title;
        this.balance = balance;
        this.isActive = isActive;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getWalletNumber() {
        return walletNumber;
    }

    public void setWalletNumber(String walletNumber) {
        this.walletNumber = walletNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

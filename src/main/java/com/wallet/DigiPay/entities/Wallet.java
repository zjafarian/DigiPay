package com.wallet.DigiPay.entities;

import javax.persistence.*;

@Entity
@Table(name = "t_wallet")
public class Wallet extends BaseModel{



    @Column(name = "wal_title")
    private String title;

    @Column(name = "wal_balance")
    private Double balance;


    @Column(name = "wal_is_active")
    private Boolean isActive;

    public Wallet() {
    }

    public Wallet(String title, Double balance, Boolean isActive) {
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
}

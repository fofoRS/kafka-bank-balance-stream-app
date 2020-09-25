package org.bank.account.stream;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;

@RegisterForReflection
public class AccountBalance {

    private String name;
    private Integer balance;
    private String latestTransaction;

    public AccountBalance() {
        this.name = "";
        this.balance = 0;
        this.latestTransaction = "";
    }

    public static AccountBalance initialize() {
        return new AccountBalance();
    }

    public AccountBalance updateFrom(AccountTransaction accountTransaction) {
        setBalance(accountTransaction.getAmount());
        setLatestTransaction(accountTransaction.getTime());
        setName(accountTransaction.getName());
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {

        this.balance += balance;
    }

    public String getLatestTransaction() {
        return latestTransaction;
    }

    public void setLatestTransaction(String latestTransaction) {
        this.latestTransaction = latestTransaction;
    }
}

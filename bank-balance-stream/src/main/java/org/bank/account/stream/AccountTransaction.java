package org.bank.account.stream;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class AccountTransaction {
    private String name;
    private String time;
    private Integer amount;

    public AccountTransaction() {}

    public AccountTransaction(String name, Integer amount, String time) {
        this.name = name;
        this.amount = amount;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

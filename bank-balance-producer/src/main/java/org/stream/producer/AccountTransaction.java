package org.stream.producer;

import java.time.Instant;
import java.time.LocalDateTime;

public class AccountTransaction {
    private String name;
    private Integer amount;
    private String time;

    public AccountTransaction(String name, Integer amount, Instant time) {
        this.name = name;
        this.amount = amount;
        this.time = time.toString();
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

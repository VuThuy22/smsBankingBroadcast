package vn.luckybets.broadcastsmsbanking.model;

import java.io.Serializable;

public class Bank implements Serializable {
    private Long id;
    private String name;
    private String phone;
    private double balance;
    private Long timeUpdate;

    public Bank() {
    }

    public Bank(Long id, String name, String phone, double balance) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.balance = balance;
    }

    public Bank(Long id, String name, String phone, double balance, long timeUpdate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.balance = balance;
        this.timeUpdate = timeUpdate;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", balance=" + balance +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Long getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(Long timeUpdate) {
        this.timeUpdate = timeUpdate;
    }
}


package vn.luckybets.broadcastsmsbanking.model;

import java.io.Serializable;

public class SmsBank implements Serializable {
    private long id;
    private String content;
    private String phone;
    private String bankName;
    private int action;
    private int status;
    private String syntax;
    private String fullconte;
    private String account;
    private double balance;
    private double moneytrans;
    private String time;

    public SmsBank() {
    }

    public SmsBank(long id, String content, String phone, int action, int status, String syntax, String fullconte, String account, double balance, double moneytrans) {
        this.id = id;
        this.content = content;
        this.phone = phone;
        this.action = action;
        this.status = status;
        this.syntax = syntax;
        this.fullconte = fullconte;
        this.account = account;
        this.balance = balance;
        this.moneytrans = moneytrans;
    }

    public SmsBank(long id, String content, String phone, int action, int status, String syntax, String fullconte, String account, double balance, double moneytrans, String time) {
        this.id = id;
        this.content = content;
        this.phone = phone;
        this.action = action;
        this.status = status;
        this.syntax = syntax;
        this.fullconte = fullconte;
        this.account = account;
        this.balance = balance;
        this.moneytrans = moneytrans;
        this.time = time;
    }

    @Override
    public String toString() {
        return "SmsBank{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", phone='" + phone + '\'' +
                ", action=" + action +
                ", status=" + status +
                ", syntax='" + syntax + '\'' +
                ", fullconte='" + fullconte + '\'' +
                ", account='" + account + '\'' +
                ", balance=" + balance +
                ", moneytrans=" + moneytrans +
                ", time=" + time +
                '}';
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public String getFullconte() {
        return fullconte;
    }

    public void setFullconte(String fullconte) {
        this.fullconte = fullconte;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getMoneytrans() {
        return moneytrans;
    }

    public void setMoneytrans(double moneytrans) {
        this.moneytrans = moneytrans;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}

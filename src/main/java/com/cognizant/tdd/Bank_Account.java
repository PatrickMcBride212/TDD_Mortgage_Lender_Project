package com.cognizant.tdd;

public class Bank_Account {
    private int balance;

    public Bank_Account(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public int withdraw(int amount) {
        if (amount <= balance) {
            balance -= amount;
            return amount;
        } else {
            return 0;
        }
    }
}

package com.cognizant.tdd;

public class Bank_Account {
    private int balance;
    private int pendingFunds;



    public Bank_Account(int balance) {
        this.balance = balance;
        pendingFunds = 0;
    }

    public void transferToPendingFunds(int amount) {
        balance -= amount;
        pendingFunds += amount;
    }

    public void setPendingFunds(int pendingFunds) {
        this.pendingFunds = pendingFunds;
    }

    public int getPendingFunds() {
        return pendingFunds;
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

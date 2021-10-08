package com.cognizant.tdd;

public class Bank_Account {
    private int balance;
    private int pendingFunds;

    //Standard constructor
    public Bank_Account(int balance) {
        this.balance = balance;
        pendingFunds = 0;
    }

    //removes amount from balance and puts it in pending funds
    public void transferToPendingFunds(int amount) {
        balance -= amount;
        pendingFunds += amount;
    }

    //removes amount from pending funds and puts it back in balance
    public void transferFromPendingFunds(int amount) {
        balance += amount;
        pendingFunds -= amount;
    }

    //withdraws from pending funds, final step of loan application
    public void withdrawFromPendingFunds(int amount) {
        pendingFunds -= amount;
    }

    //setter for pending funds, currently unused
    public void setPendingFunds(int pendingFunds) {
        this.pendingFunds = pendingFunds;
    }

    //getter for pending funds
    public int getPendingFunds() {
        return pendingFunds;
    }

    //getter for balance
    public int getBalance() {
        return balance;
    }

    //method for depositing more money into the account
    public void deposit(int amount) {
        balance += amount;
    }

    //withdrawal method, currently not used as part of the loan process since all such funds first go through the
    //pending funds
    public int withdraw(int amount) {
        if (amount <= balance) {
            balance -= amount;
            return amount;
        } else {
            return 0;
        }
    }
}

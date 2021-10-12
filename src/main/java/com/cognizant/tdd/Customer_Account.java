package com.cognizant.tdd;


//This object represents a customer of the lender, and has information contained within each object that is needed to
//determine the qualification for a loan by the customer.
public class Customer_Account {

    private int dti;
    private int credit_score;
    private int savings;

    public Customer_Account(int dti, int credit_score, int savings) {
        this.dti = dti;
        this.credit_score = credit_score;
        this.savings = savings;
    }

    public Customer_Account() {
    }

    public int getDti() {
        return dti;
    }

    public void setDti(int dti) {
        this.dti = dti;
    }

    public int getCredit_score() {
        return credit_score;
    }

    public void setCredit_score(int credit_score) {
        this.credit_score = credit_score;
    }

    public int getSavings() {
        return savings;
    }

    public void setSavings(int savings) {
        this.savings = savings;
    }

    @Override
    public String toString() {
        return "Customer_Account{" +
                "dti=" + dti +
                ", credit_score=" + credit_score +
                ", savings=" + savings +
                '}';
    }
}

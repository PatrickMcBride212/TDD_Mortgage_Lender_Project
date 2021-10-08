package com.cognizant.tdd;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Loan_Application {
    private final int requestedAmount;
    private int loanAmount;
    private final int dti;
    private final int creditScore;
    private final int savings;
    private final int qualification;
    private Date dateFiled;
    //status is true if loan is approved, false if not
    private final boolean status;
    private int loanNumber;
    //getter for date filed
    public Date getDateFiled() { return dateFiled; }
    //setter for date filed, currently unused
    public void setDateFiled(Date date) { dateFiled = date; }
    //method to set the loan ID number
    public void setLoanNumber(int number) {
        loanNumber = number;
    }
    //method to get the loan ID number
    public int getLoanNumber() {
        return loanNumber;
    }
    //method to get the requested amount
    public int getRequestedAmount() {
        return requestedAmount;
    }
    //method to get the loan qualifications (0 for not qualified, 1 for partially qualified, 2 for fully qualified)
    public int getQualification() {
        return qualification;
    }
    //method to get loan status (whether it will be approved)
    public boolean isStatus() {
        return status;
    }
    //standard constructor
    public Loan_Application(int requestedAmount, int dti, int creditScore, int savings, Date date) {
        this.requestedAmount = requestedAmount;
        this.dti = dti;
        this.creditScore = creditScore;
        this.savings = savings;
        this.qualification = isQualified();
        dateFiled = date;
        status = qualification != 0;
        loanNumber = -1;
    }

    //this function returns 0 if not qualified, 1 if partially qualified, and 2 if fully qualified
    public int isQualified() {
        if (dti < 36 && creditScore > 620 && (4 * savings) >= requestedAmount) {
            //fully qualified case
            loanAmount = requestedAmount;
            return 2;
        } else if (dti < 36 && creditScore > 620) {
            //partially qualified. This means that their loan amount is at most 4x their savings.
            //The loan amount is automatically changed here when the check is done.
            loanAmount = savings * 4;
            return 1;
        } else {
            //here they do not qualify for a loan
            loanAmount = 0;
            return 0;
        }
    }
    //getter for loan amount
    public int getLoanAmount() {
        return loanAmount;
    }
    //getter for DTI
    public int getDti() {
        return dti;
    }
    //getter for credit score
    public int getCreditScore() {
        return creditScore;
    }
    //getter for savings
    public int getSavings() {
        return savings;
    }
    //method to print loan information
    public void printApplication() {
        if (loanNumber != -1) {
            System.out.printf("Loan Number: %d\n", loanNumber);
        }
        System.out.printf("Requested Amount: %d\n", requestedAmount);
        System.out.printf("Loan Amount: %d\n", loanAmount);
        System.out.printf("DTI: %d\n", dti);
        System.out.printf("Credit Score: %d\n", creditScore);
        System.out.printf("Savings: %d\n", savings);
        System.out.printf("Qualification: %d\n", qualification);
        System.out.printf("Status: %b\n", status);
        System.out.println("=====");
    }
    //builtin equals function for loan application class
    public boolean equals(@NotNull Loan_Application comparison) {
        return comparison.getRequestedAmount() == this.requestedAmount
                && comparison.getLoanAmount() == this.loanAmount
                && comparison.getDti() == this.dti
                && comparison.getCreditScore() == this.creditScore
                && comparison.getSavings() == this.savings
                && comparison.qualification == this.qualification
                && comparison.isStatus() == this.status;
    }
}

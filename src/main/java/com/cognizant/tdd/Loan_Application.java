package com.cognizant.tdd;

public class Loan_Application {
    private final int requestedAmount;
    private int loanAmount;
    private final int dti;
    private final int creditScore;
    private final int savings;
    private final int qualification;
    //status is true if loan is approved, false if not
    private final boolean status;
    private int loanNumber;

    public void setLoanNumber(int number) {
        loanNumber = number;
    }

    public int getLoanNumber() {
        return loanNumber;
    }

    public int getRequestedAmount() {
        return requestedAmount;
    }

    public int getQualification() {
        return qualification;
    }

    public boolean isStatus() {
        return status;
    }

    public Loan_Application(int requestedAmount, int dti, int creditScore, int savings) {
        this.requestedAmount = requestedAmount;
        this.dti = dti;
        this.creditScore = creditScore;
        this.savings = savings;
        this.qualification = isQualified();
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

    public int getLoanAmount() {
        return loanAmount;
    }

    public int getDti() {
        return dti;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public int getSavings() {
        return savings;
    }

    public void printApplication() {
        System.out.printf("Requested Amount: %d\n", requestedAmount);
        System.out.printf("Loan Amount: %d\n", loanAmount);
        System.out.printf("DTI: %d\n", dti);
        System.out.printf("Credit Score: %d\n", creditScore);
        System.out.printf("Savings: %d\n", savings);
        System.out.printf("Qualification: %d\n", qualification);
        System.out.printf("Status: %b\n", status);
        System.out.println("=====");
    }

    public boolean equals(Loan_Application comparison) {
        return comparison.getRequestedAmount() == this.requestedAmount
                && comparison.getLoanAmount() == this.loanAmount
                && comparison.getDti() == this.dti
                && comparison.getCreditScore() == this.creditScore
                && comparison.getSavings() == this.savings
                && comparison.qualification == this.qualification
                && comparison.isStatus() == this.status;
    }
}

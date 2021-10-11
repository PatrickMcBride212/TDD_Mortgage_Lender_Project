package com.cognizant.tdd;

public class Loan_Approval {

    private String qualification;
    private int loan_amount;
    private String status;

    public Loan_Approval(String qualification, int loan_amount, String status) {
        this.qualification = qualification;
        this.loan_amount = loan_amount;
        this.status = status;
    }

    public Loan_Approval() {
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(int loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Loan_Approval{" +
                "qualification='" + qualification + '\'' +
                ", loan_amount=" + loan_amount +
                ", status='" + status + '\'' +
                '}';
    }
}

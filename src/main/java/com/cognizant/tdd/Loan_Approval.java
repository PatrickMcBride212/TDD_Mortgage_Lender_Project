package com.cognizant.tdd;

import java.util.Date;

//This Loan_Approval object acts as a data object that signifies the loan between a customer and lender. It is used to keep track of the
//money being offered, as well as the qualification and status of the offer. A date of creation is added upon initialization as well.
public class Loan_Approval {

    private String qualification;
    private int loan_amount;
    private String status;
    private Date dateOfCreation;

    public Loan_Approval(String qualification, int loan_amount, String status) {
        this.qualification = qualification;
        this.loan_amount = loan_amount;
        this.status = status;
    }

    public Loan_Approval(String qualification, int loan_amount, String status, Date dateOfCreation) {
        this.qualification = qualification;
        this.loan_amount = loan_amount;
        this.status = status;
        this.dateOfCreation = dateOfCreation;
    }

    public Loan_Approval() {
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
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

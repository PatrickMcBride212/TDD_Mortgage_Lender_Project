package com.cognizant.tdd;

import java.util.Date;
import java.util.concurrent.TimeUnit;

//The lender object is the predominant object that handles the behavior of money flow and loan creation/qualification.
public class Lender {

    private int available_funds;
    private int pending_funds;
    private Date current_date;

    public Lender(int available_funds, int pending_funds, Date current_date) {
        this.available_funds = available_funds;
        this.pending_funds = pending_funds;
        this.current_date = current_date;
    }

    public Lender(int available_funds, int pending_funds) {
        this.available_funds = available_funds;
        this.pending_funds = pending_funds;
    }

    public Lender(int available_funds) {
        this.available_funds = available_funds;
    }

    public Lender() {
    }

    public int getAvailable_funds() {
        return available_funds;
    }

    public void setAvailable_funds(int available_funds) {
        this.available_funds = available_funds;
    }

    public int getPending_funds() {
        return pending_funds;
    }

    public void setPending_funds(int pending_funds) {
        this.pending_funds = pending_funds;
    }

    //Custom methods

    public void deposit(int amount){
        available_funds += amount;
    }

    public void withdrawal(int amount){
        if(amount > available_funds){
            available_funds = 0;
        }else{
            available_funds -= amount;
        }
    }

    //this is the first of two qualifyLoan Methods being used. The reason for this is to keep the functionality of previous test
    //cases working that do not utilize the handling of date creation
    public Loan_Approval qualifyLoan(Customer_Account ca, int requestedAmount){
        if(ca.getDti() < 36 && ca.getCredit_score() > 620 && ca.getSavings() >= (requestedAmount * .25)){

            if(available_funds >= requestedAmount){
                available_funds -= requestedAmount;
                pending_funds += requestedAmount;

                return new Loan_Approval("qualified", requestedAmount, "qualified");

            }
            else{
                return new Loan_Approval("qualified", requestedAmount, "on hold");
            }


        }else if(ca.getDti() < 36 && ca.getCredit_score() > 620 && ca.getSavings() <= (requestedAmount * .25)){

            if(available_funds >= (ca.getSavings() * 4)){
                available_funds -= (ca.getSavings() * 4);
                pending_funds += (ca.getSavings() * 4);

                return new Loan_Approval("partially qualified", (ca.getSavings() * 4), "qualified");

            }
            else{
                return new Loan_Approval("partially qualified", (ca.getSavings() * 4), "on hold");
            }

        }
        else{
            return new Loan_Approval("not qualified", 0, "denied");
        }
    }

    //This is the second qualifyLoan implementation. It was created to handle added functionality of loan objects that have
    //date of creation objects within them.
    public Loan_Approval qualifyLoan(Customer_Account ca, int requestedAmount, Date dateOfCreation){
        if(ca.getDti() < 36 && ca.getCredit_score() > 620 && ca.getSavings() >= (requestedAmount * .25)){

            if(available_funds >= requestedAmount){
                available_funds -= requestedAmount;
                pending_funds += requestedAmount;

                return new Loan_Approval("qualified", requestedAmount, "qualified", dateOfCreation);

            }
            else{
                return new Loan_Approval("qualified", requestedAmount, "on hold", dateOfCreation);
            }


        }else if(ca.getDti() < 36 && ca.getCredit_score() > 620 && ca.getSavings() <= (requestedAmount * .25)){

            if(available_funds >= (ca.getSavings() * 4)){
                available_funds -= (ca.getSavings() * 4);
                pending_funds += (ca.getSavings() * 4);

                return new Loan_Approval("partially qualified", (ca.getSavings() * 4), "qualified", dateOfCreation);

            }
            else{
                return new Loan_Approval("partially qualified", (ca.getSavings() * 4), "on hold", dateOfCreation);
            }

        }
        else{
            return new Loan_Approval("not qualified", 0, "denied", dateOfCreation);
        }
    }

    //This method handles the acceptance or rejection of a loan based on user input. This method can handle both loan objects
    //with or without date of creation in the object, by checking to see if it has that data.
    public Loan_Approval loanAcceptance(Loan_Approval loan, boolean isAccepted){
        if(loan.getDateOfCreation() != null) {
            checkExpiration(loan);
        }
        if(loan.getStatus().equals("expired")){
            return loan;
        }else {
            if (isAccepted) {
                pending_funds -= loan.getLoan_amount();
                loan.setStatus("accepted");
                return loan;
            } else {
                pending_funds -= loan.getLoan_amount();
                available_funds += loan.getLoan_amount();
                loan.setStatus("rejected");
                return loan;

            }
        }
    }

    //This method is called by the loan acceptance method if a loan object as a date of creation. This checks based off of the data that
    //was injected into this object as current_date to see if 3 days have passed since the creation of the loan object.
    public void checkExpiration(Loan_Approval loan){

        long diffInMillies = Math.abs(current_date.getTime() - loan.getDateOfCreation().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        if(diff > 3){
            loan.setStatus("expired");
        }
    }

    @Override
    public String toString() {
        return "Lender{" +
                "available_funds=" + available_funds +
                ", pending_funds=" + pending_funds +
                '}';
    }
}

package com.cognizant.tdd;

public class Lender {

    private int available_funds;
    private int pending_funds;

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

    public void withdrawl(int amount){
        if(amount > available_funds){
            available_funds = 0;
        }else{
            available_funds -= amount;
        }
    }

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

    @Override
    public String toString() {
        return "Lender{" +
                "available_funds=" + available_funds +
                ", pending_funds=" + pending_funds +
                '}';
    }
}

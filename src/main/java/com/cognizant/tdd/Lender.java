package com.cognizant.tdd;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Lender {
    Bank_Account account;
    private ArrayList<Loan_Application> pendingApplications;
    private HashMap<Integer, Loan_Application> approvedApplications;
    private ArrayList<Loan_Application> onHoldApplications;
    private ArrayList<Loan_Application> acceptedLoans;
    int loanNumber;

    public Lender(Bank_Account account) {
        this.account = account;
        pendingApplications = new ArrayList<>();
        approvedApplications = new HashMap<>();
        onHoldApplications = new ArrayList<>();
        acceptedLoans = new ArrayList<>();
        loanNumber = 0;
    }

    public void processPendingApplications() {
        for (Loan_Application application : pendingApplications) {
            if (account.getBalance() >= application.getLoanAmount()) {
                approvedApplications.put(loanNumber, application);
                account.transferToPendingFunds(application.getLoanAmount());
                application.setLoanNumber(loanNumber);
                loanNumber++;
            } else {
                onHoldApplications.add(application);
            }
        }
        pendingApplications.clear();
    }

    public void addApplication(@NotNull Loan_Application application) {
        if (application.getQualification() != 0) {
            pendingApplications.add(application);
        }
    }

    public void loanAccepted(int loanNumber) {
        Loan_Application loan = approvedApplications.get(loanNumber);
        approvedApplications.remove(loanNumber);
        acceptedLoans.add(loan);
        account.withdrawFromPendingFunds(loan.getLoanAmount());
    }

    public ArrayList<Loan_Application> getPendingApplications() {
        return pendingApplications;
    }

    public HashMap<Integer, Loan_Application> getApprovedApplications() {
        return approvedApplications;
    }

    public ArrayList<Loan_Application> getOnHoldApplications() {
        return onHoldApplications;
    }
}

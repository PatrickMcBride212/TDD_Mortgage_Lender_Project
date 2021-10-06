package com.cognizant.tdd;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Lender {
    Bank_Account account;
    private ArrayList<Loan_Application> pendingApplications;
    private ArrayList<Loan_Application> approvedApplications;
    private ArrayList<Loan_Application> onHoldApplications;

    public Lender(Bank_Account account) {
        this.account = account;
        pendingApplications = new ArrayList<>();
        approvedApplications = new ArrayList<>();
        onHoldApplications = new ArrayList<>();
    }

    public void processPendingApplications() {
        for (Loan_Application application : pendingApplications) {
            System.out.printf("Current Balance: %d\n", account.getBalance());
            System.out.printf("Loan amount: %d\n", application.getLoanAmount());
            if (account.getBalance() >= application.getLoanAmount()) {
                System.out.println("Approved");
                approvedApplications.add(application);
                account.transferToPendingFunds(application.getLoanAmount());
            } else {
                System.out.println("On Hold");
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

    public ArrayList<Loan_Application> getPendingApplications() {
        return pendingApplications;
    }

    public ArrayList<Loan_Application> getApprovedApplications() {
        return approvedApplications;
    }

    public ArrayList<Loan_Application> getOnHoldApplications() {
        return onHoldApplications;
    }
}

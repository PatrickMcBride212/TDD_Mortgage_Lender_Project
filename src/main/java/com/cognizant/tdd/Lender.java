package com.cognizant.tdd;

import java.util.ArrayList;

public class Lender {
    Bank_Account account;
    ArrayList<Loan_Application> pendingApplications;
    ArrayList<Loan_Application> approvedApplications;
    ArrayList<Loan_Application> onHoldApplications;

    public Lender(Bank_Account account) {
        this.account = account;
        pendingApplications = new ArrayList<>();
        approvedApplications = new ArrayList<>();
        onHoldApplications = new ArrayList<>();
    }

    public void addApplication(Loan_Application application) {
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

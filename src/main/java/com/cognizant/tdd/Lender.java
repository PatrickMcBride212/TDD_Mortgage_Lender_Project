package com.cognizant.tdd;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Lender {
    Bank_Account account;
    private ArrayList<Loan_Application> pendingApplications;
    private ArrayList<Loan_Application> deniedApplications;
    private HashMap<Integer, Loan_Application> approvedApplications;
    private ArrayList<Loan_Application> onHoldApplications;
    private ArrayList<Loan_Application> acceptedLoans;
    private ArrayList<Loan_Application> rejectedLoans;
    int loanNumber;

    public Lender(Bank_Account account) {
        this.account = account;
        pendingApplications = new ArrayList<>();
        deniedApplications = new ArrayList<>();
        approvedApplications = new HashMap<>();
        onHoldApplications = new ArrayList<>();
        acceptedLoans = new ArrayList<>();
        rejectedLoans = new ArrayList<>();
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
        } else {
            deniedApplications.add(application);
        }
    }

    public void loanAccepted(int loanNumber) {
        Loan_Application loan = approvedApplications.get(loanNumber);
        approvedApplications.remove(loanNumber);
        acceptedLoans.add(loan);
        account.withdrawFromPendingFunds(loan.getLoanAmount());
    }

    public void loanRejected(int loanNumber) {
        Loan_Application loan = approvedApplications.get(loanNumber);
        approvedApplications.remove(loanNumber);
        rejectedLoans.add(loan);
        account.transferFromPendingFunds(loan.getLoanAmount());
    }

    public ArrayList<Loan_Application> getPendingApplications() {
        return pendingApplications;
    }

    public ArrayList<Loan_Application> getDeniedApplications() {
        return deniedApplications;
    }

    public HashMap<Integer, Loan_Application> getApprovedApplications() {
        return approvedApplications;
    }

    public ArrayList<Loan_Application> getOnHoldApplications() {
        return onHoldApplications;
    }

    public ArrayList<Loan_Application> getAcceptedLoans() {
        return acceptedLoans;
    }

    public ArrayList<Loan_Application> getRejectedLoans() {
        return rejectedLoans;
    }

    public void displayAllLoans() {
        displayPendingLoans();
        displayDeniedLoans();
        displayOnHoldApplications();
        displayApprovedLoans();
        displayAcceptedLoans();
        displayRejectedLoans();
    }

    public void displayPendingLoans() {
        System.out.println("Pending applications:");
        if (pendingApplications.isEmpty()) {
            System.out.println("None");
        } else {
            for (Loan_Application application : pendingApplications) {
                application.printApplication();
            }
        }
    }

    public void displayDeniedLoans() {
        System.out.println("Denied Loans:");
        if (deniedApplications.isEmpty()) {
            System.out.println("None");
        } else {
            for (Loan_Application application : deniedApplications) {
                application.printApplication();
            }
        }
    }

    public void displayApprovedLoans() {
        System.out.println("Approved Loans:");
        if (approvedApplications.isEmpty()) {
            System.out.println("None");
        } else {
            for (Loan_Application application : approvedApplications.values()) {
                application.printApplication();
            }
        }
    }

    public void displayOnHoldApplications() {
        System.out.println("Applications on Hold:");
        if (onHoldApplications.isEmpty()) {
            System.out.println("None");
        } else {
            for (Loan_Application application : onHoldApplications) {
                application.printApplication();
            }
        }
    }

    public void displayAcceptedLoans() {
        System.out.println("Accepted loans:");
        if (acceptedLoans.isEmpty()) {
            System.out.println("None");
        } else {
            for (Loan_Application application : acceptedLoans) {
                application.printApplication();
            }
        }
    }

    public void displayRejectedLoans() {
        System.out.println("Rejected Loans:");
        if (rejectedLoans.isEmpty()) {
            System.out.println("None");
        } else {
            for (Loan_Application application : rejectedLoans) {
                application.printApplication();
            }
        }
    }
}

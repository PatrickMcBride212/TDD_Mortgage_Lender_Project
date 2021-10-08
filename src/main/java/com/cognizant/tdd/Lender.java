package com.cognizant.tdd;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Lender {
    Bank_Account account;
    private ArrayList<Loan_Application> pendingApplications;
    private ArrayList<Loan_Application> deniedApplications;
    private HashMap<Integer, Loan_Application> approvedApplications;
    private ArrayList<Loan_Application> onHoldApplications;
    private ArrayList<Loan_Application> acceptedLoans;
    private ArrayList<Loan_Application> rejectedLoans;
    private ArrayList<Loan_Application> expiredApplications;
    private Date lenderDate;
    int loanNumber;

    public Lender(Bank_Account account, Date date) {
        this.account = account;
        pendingApplications = new ArrayList<>();
        deniedApplications = new ArrayList<>();
        approvedApplications = new HashMap<>();
        onHoldApplications = new ArrayList<>();
        acceptedLoans = new ArrayList<>();
        rejectedLoans = new ArrayList<>();
        expiredApplications = new ArrayList<>();
        lenderDate = date;
        loanNumber = 0;
    }

    //When date is updated, goes through pending, approved, and on hold applications
    // (All applications not fully accepted or rejected)
    // and puts all expired applications in the
    //expiredApplications arraylist
    public void setLenderDate(Date date) {
        lenderDate = date;
        for (Loan_Application application : pendingApplications) {
            long daysDifference = getDateDifference(application.getDateFiled());
            if (daysDifference > 3) {
                expiredApplications.add(application);
                pendingApplications.remove(application);
            }
        }
        for (Loan_Application application : approvedApplications.values()) {
            long daysDifference = getDateDifference(application.getDateFiled());
            if (daysDifference > 3) {
                expiredApplications.add(application);
                approvedApplications.remove(application.getLoanNumber());
            }
        }
        for (Loan_Application application : onHoldApplications) {
            long daysDifference = getDateDifference(application.getDateFiled());
            if (daysDifference > 3) {
                expiredApplications.add(application);
                onHoldApplications.remove(application);
            }
        }
    }

    public long getDateDifference(Date applicationDate) {
        return TimeUnit.DAYS.convert(lenderDate.getTime() - applicationDate.getTime(), TimeUnit.MILLISECONDS);
    }

    public Date getLenderDate() {
        return lenderDate;
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

    public ArrayList<Loan_Application> getExpiredApplications() { return expiredApplications; }

    public void displayAllLoans() {
        displayPendingLoans();
        displayDeniedLoans();
        displayOnHoldApplications();
        displayApprovedLoans();
        displayAcceptedLoans();
        displayRejectedLoans();
        displayExpiredLoans();
    }

    public void displayExpiredLoans() {
        System.out.println("Expired applications:");
        if (expiredApplications.isEmpty()) {
            System.out.println("None");
        } else {
            for (Loan_Application application : expiredApplications) {
                application.printApplication();
            }
        }
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

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

    //helper function for above method
    public long getDateDifference(Date applicationDate) {
        return TimeUnit.DAYS.convert(lenderDate.getTime() - applicationDate.getTime(), TimeUnit.MILLISECONDS);
    }

    //getter for lender date
    public Date getLenderDate() {
        return lenderDate;
    }

    //iterates through all pending applications and either approves them or puts them on hold depending on amount
    //in lender's bank account balance
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

    //method for adding application to lender's queue. Automatically rejects if loan application not qualified.
    public void addApplication(@NotNull Loan_Application application) {
        if (application.getQualification() != 0) {
            pendingApplications.add(application);
        } else {
            deniedApplications.add(application);
        }
    }

    //method for loan applicants to accept their loan. Funds then withdrawn from lender's pending funds
    public void loanAccepted(int loanNumber) {
        Loan_Application loan = approvedApplications.get(loanNumber);
        approvedApplications.remove(loanNumber);
        acceptedLoans.add(loan);
        account.withdrawFromPendingFunds(loan.getLoanAmount());
    }

    //method for loan applicants to reject their loan. Funds then withdrawn from lender's pending funds and placed
    //back into balance
    public void loanRejected(int loanNumber) {
        Loan_Application loan = approvedApplications.get(loanNumber);
        approvedApplications.remove(loanNumber);
        rejectedLoans.add(loan);
        account.transferFromPendingFunds(loan.getLoanAmount());
    }

    //getter for pending applications queue
    public ArrayList<Loan_Application> getPendingApplications() {
        return pendingApplications;
    }
    //getter for denied applications
    public ArrayList<Loan_Application> getDeniedApplications() {
        return deniedApplications;
    }
    //getter for approved applications
    public HashMap<Integer, Loan_Application> getApprovedApplications() {
        return approvedApplications;
    }
    //getter for applications on hold
    public ArrayList<Loan_Application> getOnHoldApplications() {
        return onHoldApplications;
    }
    //getter for accepted loans
    public ArrayList<Loan_Application> getAcceptedLoans() {
        return acceptedLoans;
    }
    //getter for rejected loans
    public ArrayList<Loan_Application> getRejectedLoans() {
        return rejectedLoans;
    }
    //getter for expired applications
    public ArrayList<Loan_Application> getExpiredApplications() { return expiredApplications; }
    //method to print all loans
    public void displayAllLoans() {
        displayPendingLoans();
        displayDeniedLoans();
        displayOnHoldApplications();
        displayApprovedLoans();
        displayAcceptedLoans();
        displayRejectedLoans();
        displayExpiredLoans();
    }
    //method to print expired loans
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
    //method to print pending loans
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
    //method to print denied loans
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
    //method to print approved loans
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
    //method to print loans on hold
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
    //method to print accepted loans
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
    //method to print rejected loans
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

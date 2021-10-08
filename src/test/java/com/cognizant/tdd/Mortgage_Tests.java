package com.cognizant.tdd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class Mortgage_Tests {
    Bank_Account account;
    Lender lender;
    Loan_Application loan1;
    Loan_Application loan2;
    Loan_Application loan3;
    Loan_Application loan4;
    Loan_Application loan5;
    Loan_Application loan6;
    Loan_Application loan7;
    Loan_Application loan8;
    SimpleDateFormat dateFiledFormat;
    Date dateFiled;

    @BeforeEach
    public void setup(){
        account = new Bank_Account(1000000);

        dateFiledFormat = new SimpleDateFormat("MM-dd-yyyy");
        dateFiled = null;
        try {
            dateFiled = dateFiledFormat.parse("10-4-2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lender = new Lender(account, dateFiled);
        loan1 = new Loan_Application(250000, 21, 700, 100000, dateFiled);
        loan2 = new Loan_Application(250000, 37, 700, 100000, dateFiled);
        loan3 = new Loan_Application(250000, 30, 600, 100000, dateFiled);
        loan4 = new Loan_Application(250000, 30, 700, 50000, dateFiled);
        loan5 = new Loan_Application(250000, 21, 700, 100000, dateFiled);
        loan6 = new Loan_Application(500000, 21, 700, 200000, dateFiled);

        dateFiled = null;
        try {
            dateFiled = dateFiledFormat.parse("10-8-2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        loan7 = new Loan_Application(1, 21, 700, 200000, dateFiled);
        loan8 = new Loan_Application(500000, 21, 700, 200000, dateFiled);
    }

    //This test verifies basic functionality of bank account, including deposits, withdrawals, overdraws,
    //and transferring to pending funds

    @Test
    public void testBankAccountWithdrawals() {
        int withdrawal = account.withdraw(500000);

        assertEquals(0, account.getPendingFunds());

        assertEquals(500000, withdrawal);
        assertEquals(500000, account.getBalance());

        int secondWithdrawal = account.withdraw(500000);
        assertEquals(500000, secondWithdrawal);
        assertEquals(0, account.getBalance());

        int overDraw = account.withdraw(1);
        assertEquals(0, overDraw);
        assertEquals(0, account.getBalance());

        account.deposit(1000000);
        assertEquals(1000000, account.getBalance());
    }

    //Separate test to verify deposits

    @Test
    public void testBankAccountDeposits() {
        account.deposit(100000);
        assertEquals(1100000, account.getBalance());
    }

    //Test to verify information in loan application objects, and make sure
    //each application is properly qualified, partially qualified (and the amount they qualify for),
    //or not qualified.

    @Test
    public void testLoanApplicationApproval() {
        assertEquals(250000, loan1.getRequestedAmount());
        assertEquals(21, loan1.getDti());
        assertEquals(700, loan1.getCreditScore());
        assertEquals(100000, loan1.getSavings());
        assertEquals(2, loan1.getQualification());
        assertEquals(250000, loan1.getLoanAmount());
        assertTrue(loan1.isStatus());

        assertEquals(250000, loan2.getRequestedAmount());
        assertEquals(37, loan2.getDti());
        assertEquals(700, loan2.getCreditScore());
        assertEquals(100000, loan2.getSavings());
        assertEquals(0, loan2.getQualification());
        assertEquals(0, loan2.getLoanAmount());
        assertFalse(loan2.isStatus());

        assertEquals(250000, loan3.getRequestedAmount());
        assertEquals(30, loan3.getDti());
        assertEquals(600, loan3.getCreditScore());
        assertEquals(100000, loan3.getSavings());
        assertEquals(0, loan3.getQualification());
        assertEquals(0, loan3.getLoanAmount());
        assertFalse(loan3.isStatus());

        assertEquals(250000, loan4.getRequestedAmount());
        assertEquals(30, loan4.getDti());
        assertEquals(700, loan4.getCreditScore());
        assertEquals(50000, loan4.getSavings());
        assertEquals(1, loan4.getQualification());
        assertEquals(200000, loan4.getLoanAmount());
        assertTrue(loan4.isStatus());
    }

    //This verifies pending applications are queued properly, and not qualified loans are not added to the
    //pending applications queue

    @Test
    public void lenderPendingApplicationQueueTests() {
        lender.addApplication(loan1);
        lender.addApplication(loan2);
        lender.addApplication(loan3);
        lender.addApplication(loan4);

        //since loans 2 and 3 did not qualify, we should not see them in the list of pending loans for the lender

        assertTrue(lender.getPendingApplications().contains(loan1));
        assertFalse(lender.getPendingApplications().contains(loan2));
        assertFalse(lender.getPendingApplications().contains(loan3));
        assertTrue(lender.getPendingApplications().contains(loan4));
    }

    //This verifies that the lender properly processes their pending loans.
    //The lender will approve each loan and transfer the funds to pending given the lender has
    //enough funds. Otherwise, the loan will be on hold.

    @Test
    public void lenderProcessPendingLoansTest() {
        lender.addApplication(loan1);
        lender.addApplication(loan2);
        lender.addApplication(loan3);
        lender.addApplication(loan4);
        lender.addApplication(loan5);
        lender.addApplication(loan6);

        assertTrue(lender.getPendingApplications().contains(loan1));
        assertFalse(lender.getPendingApplications().contains(loan2));
        assertFalse(lender.getPendingApplications().contains(loan3));
        assertTrue(lender.getPendingApplications().contains(loan4));
        assertTrue(lender.getPendingApplications().contains(loan5));
        assertTrue(lender.getPendingApplications().contains(loan6));

        lender.processPendingApplications();

        assertEquals(loan1, lender.getApprovedApplications().get(loan1.getLoanNumber()));
        assertEquals(loan4, lender.getApprovedApplications().get(loan4.getLoanNumber()));
        assertEquals(loan5, lender.getApprovedApplications().get(loan5.getLoanNumber()));
        assertTrue(lender.getOnHoldApplications().get(0).equals(loan6));
    }

    //This test verifies that the loan acceptance works properly. The lender is notified of the acceptance,
    //and then the funds are transferred out of the lender's pending account.
    //This test verifies that happens, and that the accounts reflect the correct balances after each step.

    @Test
    public void loanAcceptanceTest() {
        lender.addApplication(loan1);
        lender.addApplication(loan2);
        lender.addApplication(loan3);
        lender.addApplication(loan4);
        lender.addApplication(loan5);
        lender.addApplication(loan6);

        assertTrue(lender.getPendingApplications().contains(loan1));
        assertFalse(lender.getDeniedApplications().contains(loan1));

        assertFalse(lender.getPendingApplications().contains(loan2));
        assertTrue(lender.getDeniedApplications().contains(loan2));

        assertFalse(lender.getPendingApplications().contains(loan3));
        assertTrue(lender.getDeniedApplications().contains(loan3));

        assertTrue(lender.getPendingApplications().contains(loan4));
        assertFalse(lender.getDeniedApplications().contains(loan4));

        assertTrue(lender.getPendingApplications().contains(loan5));
        assertFalse(lender.getDeniedApplications().contains(loan5));

        assertTrue(lender.getPendingApplications().contains(loan6));
        assertFalse(lender.getDeniedApplications().contains(loan6));

        lender.processPendingApplications();

        assertEquals(loan1, lender.getApprovedApplications().get(loan1.getLoanNumber()));
        assertEquals(loan4, lender.getApprovedApplications().get(loan4.getLoanNumber()));
        assertEquals(loan5, lender.getApprovedApplications().get(loan5.getLoanNumber()));
        assertTrue(lender.getOnHoldApplications().get(0).equals(loan6));

        lender.loanAccepted(loan1.getLoanNumber());
        assertEquals(450000, lender.account.getPendingFunds());
        assertTrue(lender.getAcceptedLoans().contains(loan1));

        lender.loanAccepted(loan4.getLoanNumber());
        assertEquals(250000, lender.account.getPendingFunds());
        assertTrue(lender.getAcceptedLoans().contains(loan4));

        lender.loanAccepted(loan5.getLoanNumber());
        assertEquals(0, lender.account.getPendingFunds());
        assertTrue(lender.getAcceptedLoans().contains(loan5));
    }

    //This test verifies that loan rejection is handled properly. Once the lender is notified of the loan rejection,
    //they transfer the loan's funds from pending back to their balance. This test goes through the process, and checks
    //that pending and balance of the lender's account are correct after each step.

    @Test
    public void loanRejectionTest() {
        lender.addApplication(loan1);
        lender.addApplication(loan2);
        lender.addApplication(loan3);
        lender.addApplication(loan4);
        lender.addApplication(loan5);
        lender.addApplication(loan6);

        assertTrue(lender.getPendingApplications().contains(loan1));
        assertFalse(lender.getPendingApplications().contains(loan2));
        assertFalse(lender.getPendingApplications().contains(loan3));
        assertTrue(lender.getPendingApplications().contains(loan4));
        assertTrue(lender.getPendingApplications().contains(loan5));
        assertTrue(lender.getPendingApplications().contains(loan6));

        lender.processPendingApplications();

        assertEquals(loan1, lender.getApprovedApplications().get(loan1.getLoanNumber()));
        assertEquals(loan4, lender.getApprovedApplications().get(loan4.getLoanNumber()));
        assertEquals(loan5, lender.getApprovedApplications().get(loan5.getLoanNumber()));
        assertTrue(lender.getOnHoldApplications().get(0).equals(loan6));

        lender.loanAccepted(loan1.getLoanNumber());
        assertEquals(450000, lender.account.getPendingFunds());
        assertTrue(lender.getAcceptedLoans().contains(loan1));

        lender.loanAccepted(loan4.getLoanNumber());
        assertEquals(250000, lender.account.getPendingFunds());
        assertTrue(lender.getAcceptedLoans().contains(loan4));

        lender.loanRejected(loan5.getLoanNumber());
        assertEquals(0, lender.account.getPendingFunds());
        assertFalse(lender.getAcceptedLoans().contains(loan5));
        assertTrue(lender.getRejectedLoans().contains(loan5));
        assertEquals(550000, lender.account.getBalance());
    }

    //This test verifies that the expiration of loans is handled properly by the lender. Once the lender's date is
    //updated, they will take all loans filed more than 3 days ago and put them in the expired arraylist.
    //This also tests the loan information display at the very end.

    @Test
    public void applicationExpiresTest() {
        lender.addApplication(loan1);
        lender.addApplication(loan2);
        lender.addApplication(loan3);
        lender.addApplication(loan4);
        lender.addApplication(loan5);

        lender.processPendingApplications();

        lender.loanAccepted(loan1.getLoanNumber());
        lender.loanRejected(loan5.getLoanNumber());

        Date newDate = null;
        try {
            newDate = dateFiledFormat.parse("10-8-2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        lender.setLenderDate(newDate);

        lender.addApplication(loan7);
        lender.addApplication(loan8);

        lender.processPendingApplications();

        lender.addApplication(loan6);

        assertTrue(lender.getAcceptedLoans().contains(loan1));
        assertTrue(lender.getExpiredApplications().contains(loan4));
        assertTrue(lender.getRejectedLoans().contains(loan5));
        assertTrue(lender.getPendingApplications().contains(loan6));
        assertTrue(lender.getDeniedApplications().contains(loan2));
        assertTrue(lender.getDeniedApplications().contains(loan3));

        lender.displayAllLoans();

    }

}

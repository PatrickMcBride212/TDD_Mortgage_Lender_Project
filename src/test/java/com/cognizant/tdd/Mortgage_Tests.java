package com.cognizant.tdd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class Mortgage_Tests {
    Bank_Account account;
    Lender lender;
    @BeforeEach
    public void setup(){
        account = new Bank_Account(1000000);
        lender = new Lender(account);
    }

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
    }

    @Test
    public void testBankAccountDeposits() {
        account.deposit(100000);
        assertEquals(1100000, account.getBalance());
    }

    @Test
    public void testLoanApplicationApproval() {
        Loan_Application loan1 = new Loan_Application(250000, 21, 700, 100000);
        Loan_Application loan2 = new Loan_Application(250000, 37, 700, 100000);
        Loan_Application loan3 = new Loan_Application(250000, 30, 600, 100000);
        Loan_Application loan4 = new Loan_Application(250000, 30, 700, 50000);

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
        assertEquals(0, loan2.getQualification());
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

    @Test
    public void lenderPendingApplicationQueueTests() {
        Lender lender = new Lender(account);

        Loan_Application loan1 = new Loan_Application(250000, 21, 700, 100000);
        Loan_Application loan2 = new Loan_Application(250000, 37, 700, 100000);
        Loan_Application loan3 = new Loan_Application(250000, 30, 600, 100000);
        Loan_Application loan4 = new Loan_Application(250000, 30, 700, 50000);

        lender.addApplication(loan1);
        lender.addApplication(loan2);
        lender.addApplication(loan3);
        lender.addApplication(loan4);

        //since loans 2 and 3 did not qualify, we should not see them in the list of pending loans for the lender

        assertTrue(lender.pendingApplications.contains(loan1));
        assertFalse(lender.pendingApplications.contains(loan2));
        assertFalse(lender.pendingApplications.contains(loan3));
        assertTrue(lender.pendingApplications.contains(loan4));
    }

    /*
    @Test
    public void lenderProcessPendingLoansTest() {

    }

     */
}

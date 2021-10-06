package com.cognizant.tdd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class Mortgage_Tests {
    Bank_Account account;

    @BeforeEach
    public void setup(){ account = new Bank_Account(1000000);}

    @Test
    public void testBankAccountWithdrawals() {
        int withdrawal = account.withdraw(500000);
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

        assertEquals(2, loan1.getQualification());
        assertEquals(250000, loan1.getLoanAmount());

        assertEquals(0, loan2.getQualification());
        assertEquals(0, loan2.getQualification());

        assertEquals(0, loan3.getQualification());
        assertEquals(0, loan3.getLoanAmount());

        assertEquals(1, loan4.getQualification());
        assertEquals(200000, loan4.getLoanAmount());
    }
}

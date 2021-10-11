package com.cognizant.tdd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Bank_Tests {

    //private Customer_Account ca;
    private Lender lender;
    //private Loan_Approval la;

    @BeforeEach
    public void setup(){
        lender = new Lender(100000, 0);
    }

    @Test
    public void bankTransactionsTest(){
        lender.deposit(100000);
        assertEquals(200000, lender.getAvailable_funds());

        lender.withdrawl(50000);
        assertEquals(150000, lender.getAvailable_funds());

        lender.withdrawl(200000);
        assertEquals(0, lender.getAvailable_funds());
    }

    @Test
    public void loanQualificationsTest(){
        Loan_Approval laApproved = lender.qualifyLoan(new Customer_Account(20, 700, 10000), 25000);
        assertEquals("Loan_Approval{qualification='qualified', loan_amount=25000, status='qualified'}", laApproved.toString());

        Loan_Approval laPartApproved = lender.qualifyLoan(new Customer_Account(20, 700, 5000), 25000);
        assertEquals("Loan_Approval{qualification='partially qualified', loan_amount=20000, status='qualified'}", laPartApproved.toString());

        Loan_Approval laDenied1 = lender.qualifyLoan(new Customer_Account(20, 600, 10000), 25000);
        assertEquals("Loan_Approval{qualification='not qualified', loan_amount=0, status='denied'}", laDenied1.toString());

        Loan_Approval laDenied2 = lender.qualifyLoan(new Customer_Account(36, 700, 10000), 25000);
        assertEquals("Loan_Approval{qualification='not qualified', loan_amount=0, status='denied'}", laDenied2.toString());
    }

    @Test
    public void loanQualificationsWithLenderFundingTest(){
        Loan_Approval laOnHold = lender.qualifyLoan(new Customer_Account(20, 700, 100000), 250000);
        assertEquals("Loan_Approval{qualification='qualified', loan_amount=250000, status='on hold'}", laOnHold.toString());

        Customer_Account partLoan = new Customer_Account(20, 700, 50000);
        Loan_Approval laPartOnHold = lender.qualifyLoan(partLoan, 250000);
        assertEquals("Loan_Approval{qualification='partially qualified', loan_amount=200000, status='on hold'}", laPartOnHold.toString());

        lender.deposit(100000);
        assertEquals("Loan_Approval{qualification='partially qualified', loan_amount=200000, status='qualified'}",
                lender.qualifyLoan(partLoan, 250000).toString());

    }








}

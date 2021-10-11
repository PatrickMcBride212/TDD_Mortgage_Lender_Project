package com.cognizant.tdd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Bank_Tests {

    //private Customer_Account ca;
    private Lender lender;
    private Date currentDate;
    //private Loan_Approval la;

    @BeforeEach
    public void setup() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String setDate = "2021/10/10";
        currentDate = sdf.parse(setDate);

        lender = new Lender(100000, 0, currentDate);
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

        assertEquals(45000, lender.getPending_funds());
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

    @Test
    public void loanAcceptTest(){
        Loan_Approval laApproved = lender.qualifyLoan(new Customer_Account(20, 700, 10000), 25000);
        assertEquals(75000, lender.getAvailable_funds());
        assertEquals(25000, lender.getPending_funds());
        Loan_Approval acceptedLoan = lender.loanAcceptance(laApproved, true);
        assertEquals(75000, lender.getAvailable_funds());
        assertEquals(0, lender.getPending_funds());
        assertEquals("Loan_Approval{qualification='qualified', loan_amount=25000, status='accepted'}", acceptedLoan.toString());


    }

    @Test
    public void loanRejectTest(){
        Loan_Approval laReject = lender.qualifyLoan(new Customer_Account(20, 700, 10000), 25000);
        assertEquals(75000, lender.getAvailable_funds());
        assertEquals(25000, lender.getPending_funds());
        Loan_Approval rejectedLoan = lender.loanAcceptance(laReject, false);
        assertEquals(100000, lender.getAvailable_funds());
        assertEquals(0, lender.getPending_funds());
        assertEquals("Loan_Approval{qualification='qualified', loan_amount=25000, status='rejected'}", rejectedLoan.toString());
    }

    @Test
    public void loanExpirationHandlingTest(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try{
           Date testDate =  sdf.parse("2021/10/06");

        Loan_Approval laExpired = lender.qualifyLoan(new Customer_Account(20, 700, 10000), 25000,
                testDate);

        Loan_Approval expiredLoan = lender.loanAcceptance(laExpired, true);
        assertEquals("Loan_Approval{qualification='qualified', loan_amount=25000, status='expired'}", expiredLoan.toString());

        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loanExpirationHand2lingTest(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try{
            Date testDate =  sdf.parse("2021/10/08");

            Loan_Approval laExpired = lender.qualifyLoan(new Customer_Account(20, 700, 10000), 25000,
                    testDate);

            Loan_Approval expiredLoan = lender.loanAcceptance(laExpired, true);
            assertEquals("Loan_Approval{qualification='qualified', loan_amount=25000, status='accepted'}", expiredLoan.toString());

        }catch (ParseException e) {
            e.printStackTrace();
        }
    }












}

package com.cognizant.tdd;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class Mortgage_Tests {
	Bank_Account account;

	@BeforeEach
	public void setup() {
		account = new Bank_Account(1000000);
	}

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
	public void testApproval() {
		Applicant preApp1 = new Applicant(21, 700, 100000, 250000);
		Applicant postApp1 = new Applicant(21, 700, 100000, 250000, "Qualified", 250000, "Qualified");
		assertEquals(postApp1, account.approveLoan(preApp1));
		Applicant preApp2 = new Applicant(37, 700, 100000, 250000);
		Applicant postApp2 = new Applicant(37, 700, 100000, 250000, "Not Qualified", 0, "Denied");
		assertEquals(postApp2, account.approveLoan(preApp2));
		Applicant preApp3 = new Applicant(30, 700, 50000, 250000);
		Applicant postApp3 = new Applicant(30, 700, 50000, 250000, "Partially Qualified", 200000, "Qualified");
		assertEquals(postApp3, account.approveLoan(preApp3));
	}
	
	@Test
	public void testApprovalStatus() {
		Applicant app1 = new Applicant(21, 700, 100000, 2500000);
		boolean status1 = account.loanStatus(app1);
		assertEquals(false, status1);
		Applicant app2 = new Applicant(21, 700, 100000, 250000);
		boolean status2 = account.loanStatus(app2);
		assertEquals(true, status2);
	}
	
	@Test
	public void testPending() {
		Applicant preApp = new Applicant(21, 700, 100000, 250000);
		Applicant postApp = account.approveLoan(preApp);
		account.transferFunds(postApp);
		assertEquals(750000, account.getBalance());
		assertEquals(250000, account.getPending());
	}
	
	@Test
	public void testLoanTransfer() {
		Applicant preApp = new Applicant(21, 700, 100000, 250000);
		Applicant postApp = account.approveLoan(preApp);
		account.transferFunds(postApp);
		assertEquals(250000, account.getPending());
		assertEquals(750000, account.getBalance());
		account.transferLoan(postApp, true);
		assertEquals(0, account.getPending());
		assertEquals(750000, account.getBalance());
		Applicant preApp1 = new Applicant(21, 700, 100000, 250000);
		Applicant postApp1 = account.approveLoan(preApp1);
		account.transferFunds(postApp1);
		assertEquals(250000, account.getPending());
		assertEquals(500000, account.getBalance());
		account.transferLoan(postApp1, false);
		assertEquals(0, account.getPending());
		assertEquals(750000, account.getBalance());
	}
	
	@Test 
	public void testExpired() {
		Applicant preApp = new Applicant(21, 700, 100000, 250000);
		Applicant postApp = account.approveLoan(preApp);
		account.transferFunds(postApp);
		account.expiredLoan(postApp, 2);
		assertEquals(250000, account.getPending());
		assertEquals(750000, account.getBalance());
		account.expiredLoan(postApp, 4);
		assertEquals(0, account.getPending());
		assertEquals(1000000, account.getBalance());
	}
}

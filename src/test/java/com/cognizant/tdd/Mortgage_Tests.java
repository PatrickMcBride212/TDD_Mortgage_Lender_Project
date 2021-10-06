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
}

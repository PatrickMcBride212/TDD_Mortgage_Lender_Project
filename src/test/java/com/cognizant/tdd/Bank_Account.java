package com.cognizant.tdd;

public class Bank_Account {
	private int balance;
	private int pending;

	public Bank_Account(int balance) {
		this.balance = balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getBalance() {
		return balance;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public int getPending() {
		return pending;
	}

	public void deposit(int amount) {
		balance += amount;
	}

	public int withdraw(int amount) {
		if (amount <= balance) {
			balance -= amount;
			return amount;
		} else {
			return 0;
		}
	}
	
	public void addPending(int amount) {
		pending += amount;
	}
	
	public void removePending(int amount) {
		if (amount <= pending) {
			pending -= amount;
		}
	}

	public Applicant approveLoan(Applicant applicant) {
		if ((applicant.getDti() < 36) && (applicant.getCredit_score() > 620)
				&& (applicant.getSavings() > (applicant.getRequested_amount() * .25))) {
			Applicant result = new Applicant(applicant.getDti(), applicant.getCredit_score(), applicant.getSavings(),
					applicant.getRequested_amount(), "Qualified", applicant.getRequested_amount(), "Qualified");
			return result;
		} else if ((applicant.getDti() < 36) && (applicant.getCredit_score() > 620)) {
			Applicant result = new Applicant(applicant.getDti(), applicant.getCredit_score(), applicant.getSavings(),
					applicant.getRequested_amount(), "Partially Qualified", applicant.getSavings() * 4, "Qualified");
			return result;
		}
		return new Applicant(applicant.getDti(), applicant.getCredit_score(), applicant.getSavings(),
				applicant.getRequested_amount(), "Not Qualified", 0, "Denied");
	}

	public boolean loanStatus(Applicant applicant) {
		boolean approved = false;
		if (this.getBalance() >= applicant.getRequested_amount()) {
			approved = true;
			return approved;
		}
		return approved;
	}
	
	public void transferFunds(Applicant applicant) {
		boolean approved = loanStatus(applicant);
		if (approved) {
			int amount = this.withdraw(applicant.getLoan_amount());
			this.addPending(amount);
		}
	}
	
	public void transferLoan(Applicant applicant, boolean accepted) {
		if (accepted) {
			this.removePending(applicant.getLoan_amount());
		}
		else {
			this.deposit(applicant.getLoan_amount());
			this.removePending(applicant.getLoan_amount());
		}
	}
	
	public void expiredLoan(Applicant applicant, int days) {
		if (days > 3) {
			this.deposit(applicant.getLoan_amount());
			this.removePending(applicant.getLoan_amount());
		}
	}
}

package com.cognizant.tdd;

import java.util.ArrayList;

public class Bank_Account {
	private int balance;
	private int pending;

	private ArrayList<Applicant> applicants = new ArrayList<>();
	
	public Bank_Account(int balance) {
		this.balance = balance;
	}

	// Setters and Getters
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

	// Deposit amount to Account Balance
	public void deposit(int amount) {
		balance += amount;
	}

	// Withdraw amount from Account Balance
	public int withdraw(int amount) {
		if (amount <= balance) {
			balance -= amount;
			return amount;
		} else {
			return 0;
		}
	}
	
	// Add amount to Pending Balance
	public void addPending(int amount) {
		pending += amount;
	}
	
	// Remove amount from Pending Balance 
	public void removePending(int amount) {
		if (amount <= pending) {
			pending -= amount;
		}
	}

	// Method to check if loan application is qualified
	public Applicant approveLoan(Applicant applicant) {
		if ((applicant.getDti() < 36) && (applicant.getCredit_score() > 620)
				&& (applicant.getSavings() > (applicant.getRequested_amount() * .25))) {
			Applicant result = new Applicant(applicant.getDti(), applicant.getCredit_score(), applicant.getSavings(),
					applicant.getRequested_amount(), "Qualified", applicant.getRequested_amount(), "Qualified");
			applicants.add(result);
			return result;
		} else if ((applicant.getDti() < 36) && (applicant.getCredit_score() > 620)) {
			Applicant result = new Applicant(applicant.getDti(), applicant.getCredit_score(), applicant.getSavings(),
					applicant.getRequested_amount(), "Partially Qualified", applicant.getSavings() * 4, "Qualified");
			applicants.add(result);
			return result;
		}
		Applicant result = new Applicant(applicant.getDti(), applicant.getCredit_score(), applicant.getSavings(),
				applicant.getRequested_amount(), "Not Qualified", 0, "Denied");
		applicants.add(result);
		return result;
	}

	// Method to approve loan based on Bank Account Balance 
	public boolean loanStatus(Applicant applicant) {
		boolean approved = false;
		if (this.getBalance() >= applicant.getRequested_amount() && applicant.getStatus().equals("Qualified")) {
			applicant.setStatus("Approved");
			approved = true;
			return approved;
		}
		applicant.setStatus("On hold");
		return approved;
	}
	
	// Transfer funds between Account Balance and Pending Balance
	public void transferFunds(Applicant applicant) {
		boolean approved = loanStatus(applicant);
		if (approved) {
			int amount = this.withdraw(applicant.getLoan_amount());
			this.addPending(amount);
		}
	}
	
	// Transfer funds between Account Balance and Pending Balance based on Applicants acceptance/rejection
	public void transferLoan(Applicant applicant, boolean accepted) {
		if (accepted) {
			this.removePending(applicant.getLoan_amount());
			applicant.setStatus("Accepted");
		}
		else {
			this.deposit(applicant.getLoan_amount());
			this.removePending(applicant.getLoan_amount());
			applicant.setStatus("Rejected");
		}
	}
	
	// Check if loan applications are expired, and transfer funds back if so 
	public void expiredLoan(Applicant applicant, int days) {
		if (days > 3) {
			this.deposit(applicant.getLoan_amount());
			this.removePending(applicant.getLoan_amount());
			applicant.setStatus("Expired");
		}
	}
	
	// Search loan applications based on application status
	public String searchByStatus(String status) {
		String result = "";
		for (int i = 0; i < applicants.size(); i++) {
			if (applicants.get(i).getStatus().equals(status)) {
				String temp = applicants.get(i).toString();
				result += temp;
			}
		}
		return result;
	}
}

package com.cognizant.tdd;

public class Applicant {

	private int dti;
	private int credit_score;
	private int savings;
	private int requested_amount;

	private String qualification;
	private int loan_amount;
	private String status;

	public Applicant(int dti, int credit_score, int savings, int requested_amount) {
		this.dti = dti;
		this.credit_score = credit_score;
		this.savings = savings;
		this.requested_amount = requested_amount;
	}

	public Applicant(int dti, int credit_score, int savings, int requested_amount, String qualification,
			int loan_amount, String status) {
		this.dti = dti;
		this.credit_score = credit_score;
		this.savings = savings;
		this.requested_amount = requested_amount;
		this.qualification = qualification;
		this.loan_amount = loan_amount;
		this.status = status;
	}

	// Setters and Getters
	public int getDti() {
		return dti;
	}

	public void setDti(int dti) {
		this.dti = dti;
	}

	public int getCredit_score() {
		return credit_score;
	}

	public void setCredit_score(int credit_score) {
		this.credit_score = credit_score;
	}

	public int getSavings() {
		return savings;
	}

	public void setSavings(int savings) {
		this.savings = savings;
	}

	public int getRequested_amount() {
		return requested_amount;
	}

	public void setRequested_amount(int requested_amount) {
		this.requested_amount = requested_amount;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public int getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(int loan_amount) {
		this.loan_amount = loan_amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Requested Amount: " + this.getRequested_amount() + ", DTI: " + this.getDti() + ", Credit Score: "
				+ this.getCredit_score() + ", Savings: " + this.getSavings() + ", Qualification: "
				+ this.getQualification() + ", Loan Amount: " + this.getLoan_amount() + ", Status: " + this.getStatus() + "\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}

		Applicant app1 = (Applicant) obj;
		return this.dti == app1.dti && this.credit_score == app1.credit_score && this.savings == app1.savings
				&& this.requested_amount == app1.requested_amount && this.qualification.equals(app1.qualification)
				&& this.loan_amount == app1.loan_amount && this.status.equals(app1.status);
	}
}

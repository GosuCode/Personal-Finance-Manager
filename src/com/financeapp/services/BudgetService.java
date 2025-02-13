package com.financeapp.services;

import java.util.List;

import com.financeapp.models.Transaction;

public class BudgetService {
	private double budget;
	
	public BudgetService(double budget) {
		this.budget = budget;
	}
	
	public boolean hasExceededBudget(List<Transaction> transactions) {
		double total = transactions.stream()
				.filter(transaction -> "Expense".equals(transaction.getType()))
				.mapToDouble(Transaction::getAmount)
				.sum();
		return total > budget;
	}
	
	public double calculateRemainingBudget(List<Transaction> transactions) {
		double totalExpense = transactions.stream()
				.filter(transaction -> "Expense".equals(transaction.getType()))
				.mapToDouble(Transaction::getAmount)
				.sum();
		return budget - totalExpense;
	}
}

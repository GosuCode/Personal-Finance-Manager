package com.financeapp.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.financeapp.models.Transaction;
import com.financeapp.services.BudgetService;
import com.financeapp.services.FileService;
import com.financeapp.utils.Validator;

public class FinanceManagerUI {
	private JFrame frame;
	private JTextField amountField;
	private JTextField categoryField;
	private JTextField dateField;
	private JTextField idField;
	private JTextArea transactionDisplay;
	private JLabel budgetStatusLabel;
	private BudgetService budgetService;

	private List<Transaction> transactions;

	public FinanceManagerUI() {
		frame = new JFrame("Personal Finance Manager");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		amountField = new JTextField(10);
		categoryField = new JTextField(10);
		dateField = new JTextField(10);
		idField = new JTextField(10);
		JButton addTransactionButton = new JButton("Add Transaction");
		JButton deleteTransactionButton = new JButton("Delete Transaction");
		transactionDisplay = new JTextArea(10, 30);
		transactionDisplay.setEditable(false);

		budgetStatusLabel = new JLabel("Remaining Budget: 1000");

		frame.add(new JLabel("Amount: "));
		frame.add(amountField);
		frame.add(new JLabel("Category : "));
		frame.add(categoryField);
		frame.add(new JLabel("Date(yyyy-mm-dd) : "));
		frame.add(dateField);
		frame.add(addTransactionButton);
		frame.add(new JScrollPane(transactionDisplay));
		frame.add(new JLabel("Delete Transaction By Id"));
		frame.add(idField);
		frame.add(deleteTransactionButton);
		frame.add(budgetStatusLabel);

		transactions = FileService.loadTransactions();
		if (transactions == null) {
			transactions = new ArrayList<>();
		}

		budgetService = new BudgetService(1000);

		for (Transaction transaction : transactions) {
			transactionDisplay.append(transaction.getId() + " - " + transaction.getDate() + " - "
					+ transaction.getCategory() + ": " + transaction.getAmount() + "\n");
		}

		addTransactionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double amount = Double.parseDouble(amountField.getText());
				String category = categoryField.getText();
				String date = dateField.getText();

				if (Validator.isValidAmount(amount) && Validator.isValidCategory(category)
						&& Validator.isValidDate(date)) {
					Transaction newTransaction = new Transaction("Expense", amount, category, date);

					if (budgetService.hasExceededBudget(transactions)) {
						JOptionPane.showMessageDialog(frame, "Warning! You have exceeded your budget.");
					} else {
						transactions.add(newTransaction);
						FileService.saveTransactions(transactions);
						refreshTransactionDisplay();
						updateBudgetStatus();
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Invalid input. Please check your data.");
				}

			}
		});

		deleteTransactionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(idField.getText());
					boolean isDeleted = FileService.deleteTransactionById(transactions, id);

					if (isDeleted) {
						FileService.saveTransactions(transactions);
						transactionDisplay.setText("");
						for (Transaction transaction : transactions) {
							transactionDisplay.append(transaction.getId() + " - " + transaction.getDate() + " - "
									+ transaction.getCategory() + ": " + transaction.getAmount() + "\n");
						}
						updateBudgetStatus();
					} else {
						JOptionPane.showMessageDialog(frame, "Transaction Id not found.");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "Please enter a valid transaction ID.");
				}

			}
		});
	}

	private void updateBudgetStatus() {
		double remainingBudget = budgetService.calculateRemainingBudget(transactions);
		budgetStatusLabel.setText("Remaining Budget: " + remainingBudget);
	}

	private void refreshTransactionDisplay() {
		transactionDisplay.setText("");
		for (Transaction transaction : transactions) {
			transactionDisplay.append(transaction.getId() + " - " + transaction.getDate() + " - "
					+ transaction.getCategory() + ": " + transaction.getAmount() + "\n");
		}
	}

}

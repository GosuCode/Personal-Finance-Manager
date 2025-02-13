package com.financeapp.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.financeapp.models.Transaction;

public class FileService {
	private static final String TRANSACTION_FILE = "data/transaction.txt";

	private static void createDirectoryIfNotExists() {
		File transactionFile = new File(TRANSACTION_FILE);
		if (!transactionFile.getParentFile().exists()) {
			transactionFile.getParentFile().mkdirs();
		}
	}

	public static void saveTransactions(List<Transaction> transactions) {
		createDirectoryIfNotExists();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_FILE))) {
			for (Transaction transaction : transactions) {
				writer.write(transaction.getId() + "," + transaction.getDate() + "," + transaction.getCategory() + "," + transaction.getAmount() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked") // little bit confident that, function will return List of Transaction
	public static List<Transaction> loadTransactions() {
		createDirectoryIfNotExists();
		List<Transaction> transactions = new ArrayList<Transaction>();
		try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 4) {
					int id = Integer.parseInt(data[0]);
					String date = data[1];
					String category = data[2];
					Double amount = Double.parseDouble(data[3]);
					Transaction transaction = new Transaction("Expense",amount, category, date);
					transaction.setId(id);
					transactions.add(transaction);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return transactions;
	}
	
	public static boolean deleteTransactionById(List<Transaction> transactions, int id) {
		return transactions.removeIf(transaction -> transaction.getId() == id);
	}

}

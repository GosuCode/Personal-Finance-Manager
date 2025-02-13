package com.financeapp.utils;

public class Validator {
	public static boolean isValidAmount(double amount) {
		return amount > 0;
	}
	
	public static boolean isValidCategory(String category) {
		return category != null && !category.trim().isEmpty();
	}
	
	public static boolean isValidDate(String date) {
		return date.matches("\\d{4}-\\d{2}-\\d{2}"); // yyyy-mm-dd
	}

}

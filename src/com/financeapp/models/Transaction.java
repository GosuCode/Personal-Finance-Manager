package com.financeapp.models;

import java.io.Serializable;

public class Transaction implements Serializable{
	private static final long serialVersionUID = 1L; 
	private static int idCounter = 1;
	
	private int id;
	private String type;
	private double amount;
	private String category;
	private String date;
	
	public Transaction(String type, double amount, String category, String date) {
		this.id = idCounter++;
		this.type = type;
		this.amount= amount;
		this.category= category;
		this.date= date;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public double getAmount() {
		return amount;
	}

	public String getCategory() {
		return category;
	}

	public String getDate() {
		return date;
	}

}

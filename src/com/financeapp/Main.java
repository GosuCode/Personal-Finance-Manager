package com.financeapp;

import javax.swing.SwingUtilities;

import com.financeapp.ui.FinanceManagerUI;

public class Main {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(()-> new FinanceManagerUI());

	}

}

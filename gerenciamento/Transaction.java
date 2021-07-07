package gerenciamento;

import java.util.ArrayList;
import java.util.Date;

public class Transaction {
	// Montante da transação
	private double amount;
	
	// Descrição da transação
	private String description;
	
	// Data da transação;	
	private Date timestamp;
	
	// A conta que a transação esta associada
	private Account account;
	
	/**
	 * Gera uma transação
	 * @param amount
	 * @param account
	 */
	public Transaction(double amount, Account account) {
		
		this.amount = amount;
		timestamp = new Date();
		this.account = account;
		
	}
	
	/**
	 * Sobrecarregando o construtor e adicionando uma descrição
	 * à transação
	 * @param amount
	 * @param memo
	 * @param account
	 */	
	public Transaction(double amount, String description, Account account) {
		this(amount, account);
		this.description = description;
	}
	
	public String showSummaryLine() {
		String summaryLine = "";
		if(amount >= 0) {
			summaryLine = " --" + this.timestamp + ", " + this.amount + " : " + this.description;
		}else {
			summaryLine = " --" + this.timestamp + ", -(" + (-this.amount) + ") : " + this.description; 
		}
		
		return summaryLine;
	}
	
	
}

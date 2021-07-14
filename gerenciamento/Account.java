package gerenciamento;

import java.util.ArrayList;

public class Account {
	/**
	 * Tipo da conta bancária (conta corrente, conta poupança, etc)
	 */
	private String type;
	
	/**
	 * Identificador universal da conta (universally unique identifier)
	 */
	private String uuid;
	
	/**
	 * Referencia do usuario associado a conta
	 */
	private User ref;
	
	/**
	 * Armazenamento de transações
	 */
	private ArrayList<Transaction> transactions;
	
	/**
	 * Cria um objeto do tipo Account	
	 * @param type
	 * @param ref
	 * @param set
	 */
	public Account(String type, User ref, General set) {
		
		this.type = type;
		this.ref = ref;
		
		//Gera um uuid para a conta
		uuid = set.getNewAccountUUID();
		
		//Inicializa o vetor de transações
		transactions = new ArrayList<>();
		
		
	}
	
	/**
	 * Retorna o tipo de conta
	 * @return 
	 */
	public String getType() {
		return this.type;
	}	
	
	/**
	 * Retorna o identificador associado a conta
	 * @return UUID
	 */
	public String getUUID() {
		return uuid;
	}
	/**
	 * Retorna a soma de todas as transações da conta
	 * @return
	 */
	public double getAccountBalance() {
		double balance = 0.0;
		for(Transaction t: transactions) {
			balance = balance + t.getAmount();
		}
		return balance;		
	}
	
	/**
	 * Mostra o histórico de todas as transações da conta
	 */
	public void accountHistory() {
		System.out.println("\n Id da conta: " + getUUID());
		int size = transactions.size();
		if(size == 0) {
			System.out.println("--Sem histórico de transações\n");
		}else {
			for(int i = transactions.size() - 1; i >= 0; i--) {
				System.out.println(transactions.get(i).showSummaryLine());
			}
		}		
	}
	/**
	 * Cria uma transação para esta conta
	 * @param amount
	 * @param description
	 */
	public void addTransaction(double amount, String description) {
		
		// Cria um novo objeto do tipo Transaction e adiciona na listra de transações		
		Transaction newTransaction = new Transaction(amount, description, this);
		transactions.add(newTransaction);
		
	}
	
	
	
}

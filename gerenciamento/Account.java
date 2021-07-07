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
	
	public Account(String type, User ref, General set) {
		
		this.type = type;
		this.ref = ref;
		
		//Gera um uuid para a conta
		uuid = set.getNewAccountUUID();
		
		//Inicializa o vetor de transações
		transactions = new ArrayList<>();
		
		
	}
	public String getType() {
		return this.type;
	}
	
	
	/**
	 * Retorna o identificador associado a conta
	 * @return
	 */
	public String getUUID() {
		return uuid;
	}
	
	public void accountHistory() {
		System.out.println("| Histórico da " + getType() + " |");
		System.out.println("| Id da conta: " + getUUID());
		for(int i = transactions.size() - 1; i >= 0; i--){
			System.out.println(transactions.get(i).showSummaryLine());
		}
		S
		
	}
	
	
	
}

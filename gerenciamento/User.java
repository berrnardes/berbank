package gerenciamento;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
	/*
	 * Armazena o primeiro nome do usuário
	 */
	private String firstName;
	
	/*
	 * Armazena o último nome do usuário
	 */
	private String lastName;
	
	/*
	 * Armazena o nome de usuário
	 */
	private String username;
	
	/**
	 * Identificador universal da conta (universally unique identifier)
	 */
	private String uuid;
	
	/*
	 * O nome de usuario convertido em hash
	 */
	private byte userHash[];
	
	/*
	 * Lista de contas atribuidas a este usuário
	 */
	private ArrayList<Account> accounts;
	
	/**
	 * Constrói um objeto do tipo User
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param set
	 */
	public User(String firstName, String lastName, String username, General set) {
		//Atribui o nome os dados base do usuario 
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;		
		
		// Cria uma criptografica do tipo MD5, para que os
		// dados do usuario sejam armazenados com mais
		// segurança
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.userHash = md.digest(username.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.out.printf("Erro, %", e);
			e.printStackTrace();
			System.exit(1);
		}
		
		// Atribui ao usuario um identificador universal
		this.uuid = set.getNewUserUUID();		
		
		this.accounts = new ArrayList<>();
		
		System.out.println("*****************************");
		System.out.printf("Usuário criado\n Seus dados\n -Usuário: %s\n -Id: %s\n",
							this.username, this.uuid);
		System.out.println("*****************************");
	}
	
	/**
	 * Obtem o identificador universal da conta (universally unique identifier)
	 * @return
	 */
	public String getUUID() {
		return this.uuid;
	}
	
	/**
	 * Obtem o nome de usuário
	 * @return
	 */
	public String getUsarname() {
		return this.username;
	}
	
	/**
	 * Obtem o nome completo do usuário
	 * @return
	 */
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	
	/**
	 * Adiciona uma conta a lista de contas
	 * @param account
	 */
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	/**
	 * Retorna o número de contas que um usuario tem cadastrado
	 * @return
	 */
	public int numberOfAccounts() {
		return this.accounts.size();
	}
	
	/**
	 * Retorna o saldo total da conta
	 * @param index
	 * @return
	 */
	public double getAcctBalance(int index) {
		return this.accounts.get(index).getAccountBalance();
	}
	
	public void addAcctTransaction(int acctIndex, double amount, String description) {
		this.accounts.get(acctIndex).addTransaction(amount, description);
	}
	
	/**
	 * Verifica na lista de contas do usuario a partir do index
	 * o tipo de conta
	 * Ex: conta poupança ou conta corrente
	 * @param index
	 * @return 
	 */
	public String accountType(int index) {
		return this.accounts.get(index).getType();
	}
	
	/**
	 * Verifica o nome de usuario passado, comparando com o nome cadastrado 
	 * inicialmente e criptografado em MD5
	 * @param username
	 * @return retorna false, se o username passado não existir
	 */
	public boolean validateUsername(String username) {		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(username.getBytes()), userHash);
		} catch (NoSuchAlgorithmException e) {
			System.out.printf("ERRO, %s", e);
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Exibe um resumo das contas do usuario
	 */
	public void printAccountSummary() {
		System.out.println("\nResumo da conta: ");
		for(int i = 0; i < accounts.size(); i++) {
			System.out.printf("  %d) - %s - R$ %s\n", i+1, accounts.get(i).getType(),
					accounts.get(i).getAccountBalance());
		}
		System.out.println();
	}
	
	/**
	 * Exibe o histórico de transações da conta
	 * @param index
	 */
	public void printAccountHistory(int index) {
		this.accounts.get(index).accountHistory();
	}


}

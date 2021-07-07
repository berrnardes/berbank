package gerenciamento;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
	private String firstName;
	private String lastName;
	private String username;
	private String uuid;
	private byte userHash[];
	private ArrayList<Account> accounts;
	
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
	
	public String getUUID() {
		return this.uuid;
	}
	
	public String getUsarname() {
		return this.username;
	}
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	public int numberOfAccounts() {
		return this.accounts.size();
	}
	public String accountType(int index) {
		return this.accounts.get(index).getType();
	}
	
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
	
	public void printAccountHistory(int index) {
		this.accounts.get(index).accountHistory();
	}


}

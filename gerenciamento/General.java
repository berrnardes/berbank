package gerenciamento;

import java.util.Random;
import java.util.ArrayList;

public class General {
	
	// Nome do banco
	private String name;
	
	//Armazena em vetores os dados das classes Account e User
	private ArrayList<Account> accounts;
	private ArrayList<User> users;
	
	Random rand = new Random();
	
	/**
	 * Inicializa o objeto contendo o nome do banco 
	 * @param name
	 */
	public General(String name) {
		// Atribui o nome do banco à constante
		this.name = name;
		
		//Inicializa os vetores de usuarios e conta
		this.accounts = new ArrayList<>();
		this.users = new ArrayList<>();		
	}
	
	/**
	 * Gera um novo identificador universal para cada conta criada,
	 * sem ter o risco de criar uma conta ja existente
	 * @return String uuid;
	 */
	public String getNewAccountUUID() {
		String uuid = "";
		int generator;
		boolean isUnique;
		int length = 10;		
	
		do {
			for(int i = 0; i < 10; i++) {
				generator = rand.nextInt(9);
				uuid = uuid + ((Integer) generator).toString();
			}
			isUnique = false;
			for(Account c : accounts) {
				if(uuid.compareTo(c.getUUID()) == 0) {
					isUnique = true;
					break;
				}
			}
		}while(isUnique);		
		return uuid;
	}
	
	/**
	 * Gera um novo identificador universal para o usuario,
	 * sem ter o risco de criar um usuario ja existente
	 * @return String uuid;
	 */
	public String getNewUserUUID() {
		String uuid = "";
		int length = 5;
		int generator;
		boolean isUnique;
		
		do {
			
			for(int i = 0; i < length; i++) {
				generator = rand.nextInt(9);
				uuid = uuid + ((Integer)generator).toString();
			}
			
			isUnique = false;
			for(User u : users) {;				
				if(uuid.compareTo(u.getUUID()) == 0) {
					isUnique = true;
					break;
				}
			}
			
		}while(isUnique);
		
		
		return uuid;
	}
	
	
	public User addUser(String firstName, String lastName, String username) {
		User u = new User(firstName, lastName, username, this);
		users.add(u);
		
		Account a = new Account("Conta Corrente", u, this);
		this.accounts.add(a);
		u.addAccount(a);
		
		return u;
	}
	
	public boolean userExists(String username) {
		boolean exist = false;
		String checkUsername = username;
		for(User u : users) {
			if(checkUsername.compareTo(u.getUsarname()) == 0) {
				exist = true;
				break;
			}
		}		
		return exist;
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	
	
	public User userValidation(String username, String id) {
		for(User u : users) {
			if(u.getUUID().compareTo(id) == 0 && u.validateUsername(username)) {
				return u;
			}
		}		
		return null;
	}
	
	
}

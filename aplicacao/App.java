package aplicacao;

import gerenciamento.*;
import java.util.Scanner;

public class App {
	public static void main(String[] args) {
		
		General banco = new General("Berbank");

		Scanner scan = new Scanner(System.in);
		String firstName;
		String lastName;
		String username;

		boolean checkUser = false;

//		System.out.print("Seu primeiro nome: ");
//		firstName = scan.next();
//		
//		System.out.print("Seu sobrenome: ");
//		lastName = scan.next();
//		
//		// 
//		do {						
//			System.out.print("Seu nome de usu�rio: ");
//			username = scan.next();
//			System.out.println();
//			
//			if(banco.userExists(username)) {
//				System.out.print("Nome de usu�rio j� est� em uso...");
//				checkUser = true;
//			}
//			
//		}while(checkUser);		

		User novoUsuario = banco.addUser("Iury", "Bernardes", "berrnardes");

		Account contaPoupanca = new Account("Conta poupan�a", novoUsuario, banco);
		banco.addAccount(contaPoupanca);
		novoUsuario.addAccount(contaPoupanca);

		User currentUser;

		// Loop infinito
		while (true) {
			// Retorna o usuario cadastrado
			currentUser = App.login(banco, scan);

			// Exibe o menu principal do sistema banc�rio
			App.userMenu(currentUser, scan);

		}
	}

	/**
	 * Area de login, onde vai fazer a verifica��o de username e Id do usu�rio
	 * retornando o usu�rio se o usuario e Id, coincidir com os dados do cadastro
	 * 
	 * @param set
	 * @param scan
	 */
	public static User login(General set, Scanner scan) {
		User userAutentication = null;
		String username;
		String id;

		do {
			System.out.print(" Username: ");
			username = scan.next();

			System.out.print(" Id: ");
			id = scan.next();

			userAutentication = set.userValidation(username, id);

			if (userAutentication == null) {
				System.out.println("Combina��o Usu�rio/Id icorreta...\n Por favor tente de novo");
			}
			System.out.println("*****************************");

		} while (userAutentication == null);

		return userAutentication;
	}

	/**
	 * Menu principal do sistema
	 * 
	 * @param user
	 * @param scan
	 */
	public static void userMenu(User user, Scanner scan) {
		// Exibe um resumo de contas do usu�rio
		user.printAccountSummary();
		int choice;

		do {
			System.out.println("--------Menu de opera��es--------");
			System.out.println("|  Hist�rico: (1)");
			System.out.println("|  Dep�sito: (2)");
			System.out.println("|  Saque: (3)");
			System.out.println("|  Transfer�ncia: (4)");
			System.out.println("|  Sair: (5)");
			System.out.print("|  Digite o n�mero da opera��o desejada: ");
			choice = scan.nextInt();
			if (choice < 1 || choice > 5) {
				System.out.println("**** Op��o inv�lida...");
			}
		} while (choice < 1 || choice > 5);

		switch (choice) {
		case 1:
			App.showHistory(user, scan);
			break;
		case 2:
			App.deposit(user, scan);
			break;
		case 3:
			App.withdraw(user, scan);
			break;
		case 4:
			App.transfer(user, scan);
			break;
		case 5:
			System.out.println("\n\n                 SESS�O ENCERRADA\n\n");
			scan.nextLine();
			break;
		}

		if (choice != 5) {
			App.userMenu(user, scan);
		}
	}

	/**
	 * Exibe um hist�rico de transa��es de uma conta escolhida pelo usu�rio
	 * 
	 * @param user
	 * @param scan
	 */
	public static void showHistory(User user, Scanner scan) {
		int accountChoice;
		int numAccounts = user.numberOfAccounts();

		System.out.println("*****************************");
		System.out.printf("\nVoc� tem um total de %s contas cadastradas\n", numAccounts);
		for (int i = 0; i < numAccounts; i++) {
			System.out.println(" " + user.accountType(i) + ": (" + (i + 1) + ")");
		}
		do {
			System.out.print(" Qual conta voc� deseja ver o hist�rico? ");
			accountChoice = scan.nextInt() - 1;

			if (accountChoice < 0 || accountChoice >= numAccounts) {
				System.out.println("\nConta inexistente... Por favor tente de novo!!!\n");
			}
		} while (accountChoice < 0 || accountChoice >= numAccounts);
		System.out.println(" --- " + user.accountType(accountChoice));
		user.printAccountHistory(accountChoice);

	}
	
	/**
	 * Faz o dep�sito para uma determinada conta
	 * @param user
	 * @param scan
	 */
	public static void deposit(User user, Scanner scan) {
		int fromAcct;
		double acctBalance;
		double amount;
		
		System.out.println("*****************************");
		// Faz um loop at� o index correspondente da conta 
		// for selecionado
		do {
			System.out.print("\nQual conta vai receber o dep�sito:\n ");
			for (int i = 0; i < user.numberOfAccounts(); i++) {
				System.out.printf(" %s - (%d)\n ", user.accountType(i), i + 1);
			}
			fromAcct = scan.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= user.numberOfAccounts()) {
				System.out.println("Valor inv�lido, por favor tente um n�mero entre 1 a " + user.numberOfAccounts());
			}
		} while (fromAcct < 0 || fromAcct >= user.numberOfAccounts());
		
		// Atribui a uma v�riavel o saldo total da conta
		acctBalance = user.getAcctBalance(fromAcct);		
		
		
		// Montante a ser depositado
		do {
			System.out.print("Quantia do deposito: ");
			amount = scan.nextInt();			
			if(amount < 0) {
				System.out.println("  *** N�o posso depositar uma quantia menor que ZERO");
			}
		}while(amount < 0);
		
		scan.nextLine();
		System.out.print("Adicionar descri��o: ");
		String description = scan.nextLine();
		
		user.addAcctTransaction(fromAcct, amount, description);
	}
	/**
	 * Faz o saque de uma determinada conta
	 * @param user
	 * @param scan
	 */
	public static void withdraw(User user, Scanner scan) {
		int fromAcct;
		double acctBalance;
		double amount;
		
		System.out.println("*****************************");
		// Faz um loop at� o index correspondente da conta 
		// for selecionado
		do {
			System.out.println("\nDe qual conta voc� deseja sacar: ");
			for (int i = 0; i < user.numberOfAccounts(); i++) {
				System.out.printf(" %s - (%d)\n", user.accountType(i), i + 1);
			}
			fromAcct = scan.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= user.numberOfAccounts()) {
				System.out.println("Valor inv�lido, por favor tente um n�mero entre 1 a " + user.numberOfAccounts());
			}
		} while (fromAcct < 0 || fromAcct >= user.numberOfAccounts());
		
		// Atribui a uma v�riavel o saldo total da conta
		acctBalance = user.getAcctBalance(fromAcct);		
		
		
		// Montante a ser sacado
		do {
			System.out.println("Quantia do saque: ");
			amount = scan.nextInt();
			
			if(amount < 0) {
				System.out.println("  *** N�o posso sacar uma quantia menor que ZERO");
			}else if(amount > acctBalance) {
				System.out.println("  *** Saldo indispon�vel para esse saque");
			}
		}while(amount < 0 || amount > acctBalance);
		
		scan.nextLine();
		System.out.println("Adicionar descri��o: ");
		String description = scan.nextLine();
		
		user.addAcctTransaction(fromAcct, -1*amount, description);
	}

	/**
	 * Faz a transferencia de uma determinada quantia de uma conta para outra 
	 * correspondente as contas cadastradas
	 * @param user
	 * @param scan
	 */
	public static void transfer(User user, Scanner scan) {
		int fromAcct;
		int toAcct;
		double acctBalance;
		double amount;
		
		System.out.println("*****************************");
		// Faz um loop at� o index correspondente da conta a ser trasnferido
		// for selecionado
		do {
			System.out.println("De qual conta voc� deseja transferir: ");
			for (int i = 0; i < user.numberOfAccounts(); i++) {
				System.out.printf(" %s - (%d)\n", user.accountType(i), i + 1);
			}
			fromAcct = scan.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= user.numberOfAccounts()) {
				System.out.println("Valor inv�lido, por favor tente um n�mero entre 1 a " + user.numberOfAccounts());
			}
		} while (fromAcct < 0 || fromAcct >= user.numberOfAccounts());
		
		// Atribui a uma v�riavel o saldo total da conta
		acctBalance = user.getAcctBalance(fromAcct);		
		
		// Faz um loop at� o index correspondente da conta que vai receber a transferencia
		// for selecionado
		do {
			System.out.println("Para qual conta voc� deseja transferir: ");
			for (int i = 0; i < user.numberOfAccounts(); i++) {
				System.out.printf(" %s - (%d)\n", user.accountType(i), i + 1);
			}
			toAcct = scan.nextInt() - 1;
			if (toAcct < 0 || toAcct >= user.numberOfAccounts()) {
				System.out.println("Valor inv�lido, por favor tente um n�mero entre 1 e " + user.numberOfAccounts());
			}
		} while (toAcct < 0 || toAcct >= user.numberOfAccounts());
		
		// Montante a ser transferido
		do {
			System.out.println("Quantia a ser transferida: ");
			amount = scan.nextInt();
			
			if(amount < 0) {
				System.out.println("  *** N�o posso transferir uma quantia menor que ZERO");
			}else if(amount > acctBalance) {
				System.out.println("  *** Saldo indispon�vel para essa transferencia");
			}
		}while(amount < 0 || amount > acctBalance);
		
		// Faz a transferencia 		
		user.addAcctTransaction(fromAcct, -1*amount, String.format("Transferencia para %s", user.accountType(toAcct)));
		user.addAcctTransaction(toAcct, amount, String.format("Transferencia recebida da %s", user.accountType(toAcct)));
	}

}

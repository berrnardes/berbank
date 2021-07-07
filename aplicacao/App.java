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
		;
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

		while (true) {

			currentUser = App.login(banco, scan);

			App.userMenu(currentUser, scan);

		}
	}

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

	public static void userMenu(User user, Scanner scan) {
		int choice;
		do {
			System.out.println("Bem vindo " + user.getFullName() + "\n\n");
			System.out.println("Total na conta: 0\n");
			System.out.println("--------Menu de opera��es--------");
			System.out.println("|  Hist�rico: (1)");
			System.out.println("|  Dep�sito: (2)");
			System.out.println("|  Saque: (3)");
			System.out.println("|  Transfer�ncia: (4)");
			System.out.println("|  Sair: (5)");
			System.out.print("|  Digite o n�mero da opera��o desejada:");
			choice = scan.nextInt();
			if (choice < 0 || choice > 5) {
				System.out.println("**** Op��o inv�lida...");
			}

		} while (choice < 0 && choice > 5);

		switch (choice) {
		case 1:
			App.showHistory(user, scan);
			break;
		case 2:
			App.showDeposit(user, scan);
			break;
		case 3:
			App.showWithdraw(user, scan);
			break;
		case 4:
			App.showTransfer(user, scan);
			break;
		case 5:
			scan.nextLine();
			break;
		}

	}

	public static void showHistory(User user, Scanner scan) {
		int accountChoice;
		int numAccounts =user.numberOfAccounts();
		do {
			System.out.println("*****************************");
			System.out.printf("Voc� tem um total de %s contas cadastradas\n", numAccounts);
			for(int i = 0; i < numAccounts; i++) {
				System.out.println(" " + user.accountType(i) + ": ("+ (i+1) +")");
			}
			System.out.print("Qual conta voc� deseja ver o hist�rico?");
			accountChoice = scan.nextInt() - 1;
			if(!(accountChoice > 0 && accountChoice <= numAccounts)) {
				System.out.println("Op��o n�o existente");
			}			
			System.out.println(" --- " + user.accountType(accountChoice));
		}while(!(accountChoice > 0 && accountChoice <= numAccounts));
		
		user.printAccountHistory(accountChoice);
		
		
	}
	public static void showDeposit(User user, Scanner scan) {
		System.out.println("Dep�sito");
	}
	public static void showWithdraw(User user, Scanner scan) {
		System.out.println("Saque");
	}
	public static void showTransfer(User user, Scanner scan) {
		System.out.println("Transferencia");
	}

}

package com.formation.cdb.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;
import com.formation.cdb.service.MenuActions;

public class Menu {
	final Logger logger = LoggerFactory.getLogger(Menu.class);
	private List<Company> companyList = new ArrayList<Company>();
	private List<Computer> computerList = new ArrayList<Computer>();
	Computer computerFound = new Computer();
	Scanner input;

	public Menu() {
		input = new Scanner(System.in);
	}

	/**
	 * Methode pour print le menu.
	 * 
	 */
	public void printMenu() {
		System.out.println("\nChoose from these choices");
		System.out.println("-------------------------\n");
		System.out.println("1 - List computers");
		System.out.println("2 - List companies");
		System.out.println("3 - Show computer details");
		System.out.println("4 - Create a computer");
		System.out.println("5 - Update a computer");
		System.out.println("6 - Delete a computer");
		System.out.println("7 - Quit");
	}

	/**
	 * Methode pour récupérer un int choisi par l'utilisateur. Demande jusqu'a
	 * avoir un int.
	 * 
	 * @return int
	 */
	public int selectInt() {
		// tant qu'on a pas un int
		while (!input.hasNextInt()) {
			logger.info("Not a number.");
			input.next();
		}
		int number = input.nextInt();
		return number;
	}

	/**
	 * Methode pour récupérer un long choisi par l'utilisateur. Demande jusqu'a
	 * avoir un long.
	 * 
	 * @return long
	 */
	public long selectLong() {
		// tant qu'on a pas un int
		while (!input.hasNextLong()) {
			logger.info("Not a number.");
			input.next();
		}
		long lg = input.nextLong();
		return lg;
	}

	/**
	 * Methode pour récupérer un String saisie par l'utilisateur.
	 * 
	 * @return string
	 */
	public String selectString() {
		String string = input.next();
		return string;
	}

	/**
	 * Methode pour récupérer le choix de l'utilsateur pour un menu type consol.
	 * choix doit etre dans [min,max]. Demande jusuq'a avoir un int valable.
	 * 
	 * @param min
	 *            La min à renvoyer.
	 * @param Le
	 *            max à renvoyer.
	 * @return int
	 */
	public int selectItemMenu(int min, int max) {
		int selection;
		// tant qu'on a pas un int
		while (!input.hasNextInt()) {
			logger.info("Not a number.");
			input.next();
		}
		selection = input.nextInt();

		// tant qu'on a pas un int valable
		if (selection > max || selection < min) {
			logger.info("Indeed a number but not this one.");
			return selectItemMenu(min, max);
		}
		return selection;
	}

	/**
	 * Methode pour exécuter les actions du menu en fonction du choix de
	 * l'utilisateur. Renvoi le choix de départ.
	 * 
	 * @param choix
	 *            L'item choisi.
	 * @return choix
	 */
	public int execute(int itemSelected) {

		switch (itemSelected) {
		case 1:
			logger.info("Case List Computers");
			computerList.clear();
			computerList = MenuActions.INSTANCE.findAllComputer();
			for (Computer computer : computerList) {
				System.out.println(" id : [" + computer.getId() + "]\t| name : " + computer.getName());
			}
			break;
		case 2:
			logger.info("Case List Companies");
			companyList.clear();
			companyList = MenuActions.INSTANCE.findAllCompany();
			for (Company company : companyList) {
				System.out.println(" id : [" + company.getId() + "]\t| name : " + company.getName());
			}
			break;
		case 3:
			logger.info("Case Show computer details");
			System.out.println("id : ");
			computerFound = MenuActions.INSTANCE.findComputer(selectLong());
			System.out.println(computerFound);

			break;
		case 4:
			logger.info("Case Create a computer");
			menuCreate();
			break;
		case 5:
			logger.info("Case Update a computer");
			menuUpdateComputer();
			break;
		case 6:
			logger.info("Case Delete a computer");
			System.out.println("id : ");
			MenuActions.INSTANCE.deleteComputer(selectLong());
			break;
		case 7:
			logger.info("Case 7 EXIT");
			input.close();
			break;
		default:
			// Cow Level
			logger.info("Warning : You are now in the Cow Level !");
		}
		return itemSelected;

	}

	/**
	 * Action du choix update.
	 * 
	 */
	public void menuUpdateComputer() {
		computerFound = new Computer();
		Computer newComputer = new Computer();

		System.out.println("computer ID : ");
		computerFound = MenuActions.INSTANCE.findComputer(selectLong());
		newComputer.setId(computerFound.getId());
		System.out.println("new name[" + computerFound.getName() + "] : ");
		newComputer.setName(selectString());

		System.out.println("companyID [" + computerFound.getCompanyid() + "] : ");
		newComputer.setCompanyId(selectInt());

		System.out.println("introduced year [" + computerFound.getIntroduced().getYear() + "] : ");
		int iyear = selectInt();
		System.out.println("introduced month [" + computerFound.getIntroduced().getMonthValue() + "] : ");
		int imonth = selectInt();
		System.out.println("introduced day [" + computerFound.getIntroduced().getDayOfMonth() + "] : ");
		int iday = selectInt();

		System.out.println("discontinued year [" + computerFound.getDiscontinued().getYear() + "] : ");
		int dyear = selectInt();
		System.out.println("discontinued month [" + computerFound.getDiscontinued().getMonthValue() + "] : ");
		int dmonth = selectInt();
		System.out.println("discontinued day [" + computerFound.getDiscontinued().getDayOfMonth() + "] : ");
		int dday = selectInt();

		newComputer.setIntroduced(LocalDate.of(iyear, imonth, iday));
		newComputer.setDiscontinued(LocalDate.of(dyear, dmonth, dday));

		System.out.println(newComputer);
		MenuActions.INSTANCE.updateComputer(newComputer);
		newComputer = MenuActions.INSTANCE.findComputer(newComputer.getId());
		System.out.println(newComputer);

	}

	/**
	 * Action du choix create.
	 */
	public void menuCreate() {
		computerFound = new Computer();
		System.out.println("name : ");
		computerFound.setName(selectString());

		System.out.println("companyID : ");
		computerFound.setCompanyId(selectInt());

		System.out.println("introduced year : ");
		int iyear = selectInt();
		System.out.println("introduced month : ");
		int imonth = selectInt();
		System.out.println("introduced day : ");
		int iday = selectInt();

		System.out.println("discontinued year : ");
		int dyear = selectInt();
		System.out.println("discontinued month : ");
		int dmonth = selectInt();
		System.out.println("discontinued day : ");
		int dday = selectInt();

		computerFound.setIntroduced(LocalDate.of(iyear, imonth, iday));
		computerFound.setDiscontinued(LocalDate.of(dyear, dmonth, dday));

		System.out.println(computerFound);
		computerFound.setId(MenuActions.INSTANCE.createComputer(computerFound));

		System.out.println(computerFound);
	}

	/**
	 * Manage all the menu. Execute the menu.
	 */
	public void showMeTheMagic() {
		printMenu();
		int choice = execute(selectItemMenu(1, 7));
		if (choice == 7) {
			return;
		} else {
			printMenu();
			while (execute(selectItemMenu(1, 7)) != 7) {
				printMenu();
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.showMeTheMagic();

	}
}

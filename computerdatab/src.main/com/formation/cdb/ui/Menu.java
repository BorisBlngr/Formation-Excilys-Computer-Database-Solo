package com.formation.cdb.ui;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;
import com.formation.cdb.persistence.CompanyDao;
import com.formation.cdb.persistence.ComputerDao;
import com.formation.cdb.persistence.PersistenceManager;

public class Menu {
	final Logger logger = LoggerFactory.getLogger(Menu.class);
	private List<Company> companyList = new ArrayList<Company>();
	private List<Computer> computerList = new ArrayList<Computer>();
	Computer computerFound = new Computer();

	public Menu() {
		setConnectionAndDao();
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
		Scanner input = new Scanner(System.in);

		// tant qu'on a pas un int
		while (!input.hasNextInt()) {
			logger.info("Not a number.");
			input.next();
		}
		return input.nextInt();
	}

	/**
	 * Methode pour récupérer un String saisie par l'utilisateur.
	 * 
	 * @return string
	 */
	public String selectString() {
		Scanner input = new Scanner(System.in);
		return input.next();
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
		Scanner input = new Scanner(System.in);

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
			computerList = ComputerDao.INSTANCE.findAll();
			for (Computer computer : computerList) {
				System.out.println(" id : [" + computer.getId() + "]\t| name : " + computer.getName());
			}
			break;
		case 2:
			logger.info("Case List Companies");
			companyList.clear();
			companyList = CompanyDao.INSTANCE.findAll();
			for (Company company : companyList) {
				System.out.println(" id : [" + company.getId() + "]\t| name : " + company.getName());
			}
			break;
		case 3:
			logger.info("Case Show computer details");
			System.out.println("id : ");
			computerFound = ComputerDao.INSTANCE.find(selectInt());
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
			ComputerDao.INSTANCE.delete(selectInt());
			break;
		case 7:
			logger.info("Case 7 EXIT");
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
		computerFound = ComputerDao.INSTANCE.find(selectInt());
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

		LocalDate introduced = LocalDate.of(iyear, imonth, iday);
		LocalDate discontinued = LocalDate.of(dyear, dmonth, dday);
		Date introducedD = Date.valueOf(introduced);
		Date discontinuedD = Date.valueOf(discontinued);

		newComputer.setIntroduced(introducedD.toLocalDate());
		newComputer.setDiscontinued(discontinuedD.toLocalDate());

		System.out.println(newComputer);
		ComputerDao.INSTANCE.update(newComputer);
		newComputer = ComputerDao.INSTANCE.find(newComputer.getId());
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

		LocalDate introduced = LocalDate.of(iyear, imonth, iday);
		LocalDate discontinued = LocalDate.of(dyear, dmonth, dday);
		Date introducedD = Date.valueOf(introduced);
		Date discontinuedD = Date.valueOf(discontinued);

		computerFound.setIntroduced(introducedD.toLocalDate());
		computerFound.setDiscontinued(discontinuedD.toLocalDate());

		System.out.println(computerFound);
		computerFound.setId(ComputerDao.INSTANCE.create(computerFound));

		System.out.println(computerFound);
	}

	/**
	 * Manage all the menu. Execute the menu.
	 */
	public void showMeTheMagic() {
		printMenu();
		int choice = execute(selectItemMenu(1, 7));
		if (choice == 7) {
			cleanAndClose();
			return;
		} else {
			printMenu();
			while (execute(selectItemMenu(1, 7)) != 7) {
				printMenu();
			}
			cleanAndClose();
		}
	}

	/**
	 * Set the connection and the Daos.
	 */
	public void setConnectionAndDao() {
		PersistenceManager.INSTANCE.connectToDb();

	}

	/**
	 * Close the connection.
	 */
	public void cleanAndClose() {
		PersistenceManager.INSTANCE.close();
	}

	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.showMeTheMagic();

	}
}

package com.formation.cdb.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;
import com.formation.cdb.model.dto.ComputerDto;
import com.formation.cdb.service.MenuActions;

public class Menu {
    final Logger logger = LoggerFactory.getLogger(Menu.class);
    Parameters params = new Parameters();
    Configuration config;
    private List<Company> companyList = new ArrayList<Company>();
    private List<ComputerDto> computerList = new ArrayList<ComputerDto>();
    ComputerDto computerFound = new ComputerDto();
    Scanner input;
    int nbItem = 9;
    int maxInPage = 0;

    /**
     * Constructeur qui instancie les variables en fonction du fichier de conf.
     */
    public Menu() {
        input = new Scanner(System.in);

        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.properties().setFileName("conf.properties"));
        try {
            config = builder.getConfiguration();

        } catch (ConfigurationException cex) {
            // loading of the configuration file failed
        }
        maxInPage = config.getInt("pagination.maxpage");

    }

    /**
     * Methode pour print le menu.
     */
    public void printMenu() {
        System.out.println("\nChoose from these choices");
        System.out.println("-------------------------\n");
        System.out.println("1 - List All computers");
        System.out.println("2 - List All companies");
        System.out.println("3 - Show computer details");
        System.out.println("4 - Create a computer");
        System.out.println("5 - Update a computer");
        System.out.println("6 - Delete a computer");
        System.out.println("7 - List Computers");
        System.out.println("8 - List Companies");
        System.out.println("9 - Quit");
    }

    /**
     * Methode pour récupérer un int choisi par l'utilisateur. Demande jusqu'a
     * avoir un int.
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
     * @return string
     */
    public String selectString() {
        String string = input.next();
        return string;
    }

    /**
     * Methode pour récupérer le choix de l'utilsateur pour un menu type consol.
     * choix doit etre dans [min,max]. Demande jusuq'a avoir un int valable.
     * @param min La min à renvoyer.
     * @param max max à renvoyer.
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
     * @param itemSelected L'item choisi.
     * @return choix
     */
    public int execute(int itemSelected) {

        switch (itemSelected) {
        case 1:
            logger.info("Case List All Computers");
            computerList.clear();
            computerList = MenuActions.INSTANCE.findAllComputer();
            for (ComputerDto computer : computerList) {
                System.out.println(" id : [" + computer.getId() + "]\t| name : " + computer.getName());
            }
            break;
        case 2:
            logger.info("Case List All Companies");
            companyList.clear();
            companyList = MenuActions.INSTANCE.findAllCompany();
            for (Company company : companyList) {
                System.out.println(" id : [" + company.getId() + "]\t| name : " + company.getName());
            }
            break;
        case 3:
            logger.info("Case Show computer details");
            System.out.println("id : ");
            Computer computerUiFound = MenuActions.INSTANCE.findComputerUi(selectLong());
            System.out.println(computerUiFound);

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
            logger.info("Case 7 List of Computers ");
            int nbComputer = MenuActions.INSTANCE.getNbComputers();
            int nbComputerPages = nbComputer / maxInPage;
            if (nbComputer % maxInPage != 0) {
                nbComputerPages++;
            }
            System.out.println("page[" + nbComputerPages + "] : ");
            computerList.clear();
            ComputerPage computerPage = new ComputerPage(selectInt());
            System.out.println(computerPage.getList());
            break;
        case 8:
            logger.info("Case 8 List of Companies");
            int nbCompany = MenuActions.INSTANCE.getNbCompanies();
            int nbCompanyPages = nbCompany / maxInPage;
            if (nbCompany % maxInPage != 0) {
                nbCompanyPages++;
            }
            System.out.println("page[" + nbCompanyPages + "] : ");
            computerList.clear();
            ComputerPage companyPage = new ComputerPage(selectInt());
            System.out.println(companyPage.getList());
            break;
        case 9:
            logger.info("Case 9 EXIT");
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
     */
    public void menuUpdateComputer() {
        computerFound = new ComputerDto();
        ComputerDto newComputer = new ComputerDto();

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
        computerFound = new ComputerDto();
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
        int choice = execute(selectItemMenu(1, nbItem));
        if (choice == 9) {
            return;
        } else {
            printMenu();
            while (execute(selectItemMenu(1, nbItem)) != nbItem) {
                printMenu();
            }
        }
    }

    /**
     * Main.
     * @param args Arguments.
     */
    public static void main(String[] args) {

        Menu menu = new Menu();
        menu.showMeTheMagic();

    }
}

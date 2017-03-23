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

import com.formation.cdb.model.dto.CompanyDto;
import com.formation.cdb.model.dto.ComputerDto;
import com.formation.cdb.service.ComputerService;

public class Menu {
    final Logger logger = LoggerFactory.getLogger(Menu.class);
    Parameters params = new Parameters();
    Configuration config;
    private List<CompanyDto> companyDtoList = new ArrayList<CompanyDto>();
    private List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
    ComputerDto computerDtoFound = new ComputerDto();
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
            computerDtoList.clear();
            computerDtoList = ComputerService.INSTANCE.findAllComputer();
            for (ComputerDto computerDto : computerDtoList) {
                System.out.println(" id : [" + computerDto.getId() + "]\t| name : " + computerDto.getName());
            }
            break;
        case 2:
            logger.info("Case List All Companies");
            companyDtoList.clear();
            companyDtoList = ComputerService.INSTANCE.findAllCompany();
            for (CompanyDto companyDto : companyDtoList) {
                System.out.println(" id : [" + companyDto.getId() + "]\t| name : " + companyDto.getName());
            }
            break;
        case 3:
            logger.info("Case Show computer details");
            System.out.println("id : ");
            ComputerDto computerDtoFound = ComputerService.INSTANCE.findComputer(selectLong());
            System.out.println(computerDtoFound);

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
            ComputerService.INSTANCE.deleteComputer(selectLong());
            break;
        case 7:
            logger.info("Case 7 List of Computers ");
            int nbComputer = ComputerService.INSTANCE.getNbComputers();
            int nbComputerPages = nbComputer / maxInPage;
            if (nbComputer % maxInPage != 0) {
                nbComputerPages++;
            }
            System.out.println("page[" + nbComputerPages + "] : ");
            computerDtoList.clear();
            ComputerPage computerPage = new ComputerPage(selectInt());
            System.out.println(computerPage.getList());
            break;
        case 8:
            logger.info("Case 8 List of Companies");
            int nbCompany = ComputerService.INSTANCE.getNbCompanies();
            int nbCompanyPages = nbCompany / maxInPage;
            if (nbCompany % maxInPage != 0) {
                nbCompanyPages++;
            }
            System.out.println("page[" + nbCompanyPages + "] : ");
            computerDtoList.clear();
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
        computerDtoFound = new ComputerDto();
        ComputerDto newComputerDto = new ComputerDto();

        System.out.println("computer ID : ");
        computerDtoFound = ComputerService.INSTANCE.findComputerDto(selectLong());
        newComputerDto.setId(computerDtoFound.getId());
        System.out.println("new name[" + computerDtoFound.getName() + "] : ");
        newComputerDto.setName(selectString());

        System.out.println("company [" + computerDtoFound.getCompany() + "] : ");
        newComputerDto.getCompany().setId(selectInt());

        System.out.println("introduced year [" + computerDtoFound.getIntroduced().getYear() + "] : ");
        int iyear = selectInt();
        System.out.println("introduced month [" + computerDtoFound.getIntroduced().getMonthValue() + "] : ");
        int imonth = selectInt();
        System.out.println("introduced day [" + computerDtoFound.getIntroduced().getDayOfMonth() + "] : ");
        int iday = selectInt();

        System.out.println("discontinued year [" + computerDtoFound.getDiscontinued().getYear() + "] : ");
        int dyear = selectInt();
        System.out.println("discontinued month [" + computerDtoFound.getDiscontinued().getMonthValue() + "] : ");
        int dmonth = selectInt();
        System.out.println("discontinued day [" + computerDtoFound.getDiscontinued().getDayOfMonth() + "] : ");
        int dday = selectInt();

        newComputerDto.setIntroduced(LocalDate.of(iyear, imonth, iday));
        newComputerDto.setDiscontinued(LocalDate.of(dyear, dmonth, dday));

        System.out.println(newComputerDto);
        ComputerService.INSTANCE.updateComputer(newComputerDto);
        newComputerDto = ComputerService.INSTANCE.findComputerDto(newComputerDto.getId());
        System.out.println(newComputerDto);

    }

    /**
     * Action du choix create.
     */
    public void menuCreate() {
        computerDtoFound = new ComputerDto();
        System.out.println("name : ");
        computerDtoFound.setName(selectString());

        System.out.println("companyID : ");
        computerDtoFound.getCompany().setId(selectInt());

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

        computerDtoFound.setIntroduced(LocalDate.of(iyear, imonth, iday));
        computerDtoFound.setDiscontinued(LocalDate.of(dyear, dmonth, dday));

        System.out.println(computerDtoFound);
        computerDtoFound.setId(ComputerService.INSTANCE.createComputer(computerDtoFound));

        System.out.println(computerDtoFound);
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

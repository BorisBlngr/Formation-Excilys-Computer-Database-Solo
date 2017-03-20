package com.formation.cdb.ui;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import com.formation.cdb.model.dto.ComputerDto;
import com.formation.cdb.service.MenuActions;

public class ComputerPage extends Page<ComputerDto> {

    Parameters params = new Parameters();
    Configuration config;

    /**
     * Constrcteur.
     * @param index Page index.
     */
    public ComputerPage(int index) {
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.properties().setFileName("conf.properties"));
        try {
            config = builder.getConfiguration();

        } catch (ConfigurationException cex) {
            // loading of the configuration file failed
        }

        maxInPage = config.getInt("pagination.maxpage");
        indexMaPage = index;
        this.list = MenuActions.INSTANCE.findComputersInRange(index, maxInPage);
    }

    /**
     * Main.
     * @param args Args.
     */
    public static void main(String[] args) {
        ComputerPage page = new ComputerPage(2);
        System.out.println(page.getList());
        System.out.println(MenuActions.INSTANCE.getNbComputers());
    }
}

package com.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum PersistenceManager {

    INSTANCE;

    Parameters params = new Parameters();
    Configuration config;
    final Logger logger = LoggerFactory.getLogger(PersistenceManager.class);

    // Connection conn = null;
    // Statement stmt = null;
    // ResultSet rs = null;

    /**
     * Constructeur récupérant les propriétés du conf.
     */
    PersistenceManager() {
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.properties().setFileName("conf.properties"));
        try {
            config = builder.getConfiguration();

        } catch (ConfigurationException cex) {
            // loading of the configuration file failed
        }
    }

    /**
     * Methode se connecter à DB_URL et renvoi la Connection.
     * @return conn
     */
    public Connection connectToDb() {
        Connection conn = null;
        // Register JDBC driver
        try {
            Class.forName(config.getString("jdbc.driver"));
            // Open a connection
            logger.debug("Connecting to db .... ");
            conn = DriverManager.getConnection(config.getString("DB_URL"), config.getString("USER"),
                    config.getString("PASS"));
            logger.debug("Connection opened");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Methode qui close la connection. Permet d'afficher l'info en
     * logger.debug.
     * @param conn Connection à close.
     * @throws SQLException Renvoit une SQLException.
     */
    public void close(Connection conn) throws SQLException {
        conn.close();
        logger.debug("Connection closed");
    }

    /**
     * Methode pour faire un sendQuery avec l'instruction sql. Renvoit un
     * ResultSet. No protection against sql injection.
     * @param sql Chaine à exécuter.
     * @return resultSet
     */
    @Deprecated
    public ResultSet sendQuery(String sql) {
        Connection conn = connectToDb();
        Statement stmt = null;
        ResultSet rs = null;
        try {

            stmt = conn.createStatement();
            logger.info("SendQuery : {}", sql);
            rs = stmt.executeQuery(sql);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Methode pour faire un execute(sql). Renvoit le result. No protection
     * against sql protection.
     * @param sql L'id du computer à trouver.
     * @return computer
     */
    @Deprecated
    public boolean sendToExec(String sql) {
        Connection conn = connectToDb();
        Statement stmt = null;
        boolean result = false;
        try {
            stmt = conn.createStatement();
            logger.info("execute : {}", sql);
            result = stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
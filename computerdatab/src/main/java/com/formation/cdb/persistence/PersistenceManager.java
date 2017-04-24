package com.formation.cdb.persistence;

import java.sql.Connection;
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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum PersistenceManager {
    INSTANCE;

    Parameters params = new Parameters();
    Configuration config;
    private static final Logger LOG = LoggerFactory.getLogger(PersistenceManager.class);
    HikariDataSource ds;
    private static final ThreadLocal<Connection> THREAD_CONNECTION = new ThreadLocal<Connection>();;

    /**
     * Constructeur récupérant les propriétés du conf.
     */
    PersistenceManager() {
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.properties().setFileName("db.properties"));
        try {
            config = builder.getConfiguration();
        } catch (ConfigurationException cex) {
            // loading of the configuration file failed
        }
        try {
            Class.forName(config.getString("dataSource.driverClass"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(config.getString("jdbcUrl"));
        cfg.setUsername(config.getString("dataSource.user"));
        cfg.setPassword(config.getString("dataSource.password"));
        // cfg.setDataSourceClassName(config.getString("dataSource.driverClass"));
        cfg.setMaximumPoolSize(config.getInt("dataSource.maximumPoolSize"));
        cfg.setMaxLifetime(config.getInt("dataSource.maxLifetimeMs"));
        cfg.addDataSourceProperty(config.getString("dataSource.cachePrepStmts"), "true");
        cfg.addDataSourceProperty(config.getString("dataSource.prepStmtCacheSize"), "250");
        cfg.addDataSourceProperty(config.getString("dataSource.prepStmtCacheSqlLimit"), "2048");
        // Prevent failed to initialize pool
        cfg.setConnectionTestQuery("show tables");
        //LOG.info(cfg.toString());
        ds = new HikariDataSource(cfg);
    }

    /**
     * Methode se connecter à jdbcUrl et renvoi la Connection du threadLocal.
     * @return conn
     */
    public Connection connectToDb() {
        try {
            if (THREAD_CONNECTION.get() == null || THREAD_CONNECTION.get().isClosed()) {
                THREAD_CONNECTION.set(connect());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return THREAD_CONNECTION.get();

    }

    /**
     * Methode se connecter à jdbcUrl et renvoi la Connection.
     * @return conn
     */
    private Connection connect() {
        Connection conn = null;
        try {
            LOG.debug("Connecting to db .... ");
            conn = ds.getConnection();
            LOG.debug("Connection opened");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;

    }

    /**
     * Methode qui close la connection. Permet d'afficher l'info en LOG.debug
     * Inutile mais indispensable...
     * @param conn Connection à close.
     * @throws SQLException Renvoit une SQLException.
     */
    public void close(Connection conn) throws SQLException {
        conn.close();
        LOG.debug("Connection closed");
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
            LOG.info("SendQuery : {}", sql);
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
            LOG.info("execute : {}", sql);
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
package com.formation.cdb.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.model.Company;

public enum CompanyDao implements Dao<Company> {
    INSTANCE;
    final Logger logger = LoggerFactory.getLogger(CompanyDao.class);
    final Properties prop = new Properties();

    /**
     * Constructeur qui initialise les properties.
     */
    CompanyDao() {
        InputStream input = null;
        try {
            input = new FileInputStream("src/main/resource/conf.properties");
            prop.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Methode pour creer une nouvelle company en base, renvoit l'id de la ligne
     * dans la bdd.
     * @param company La company à créer.
     * @return id
     */
    @Override
    public long create(Company company) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            preparedStatement = conn.prepareStatement("INSERT INTO company(name) VALUES (?)");
            preparedStatement.setString(1, company.getName());
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    PersistenceManager.INSTANCE.close(conn);
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return findIdByName(company.getName());
    }

    /**
     * Methode pour supprimer la company en base, renvoit true si ok.
     * @param company La company à delete.
     * @return result
     */
    @Override
    public boolean delete(Company company) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            preparedStatement = conn.prepareStatement("DELETE FROM company WHERE id = ?");
            preparedStatement.setLong(1, company.getId());
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            result = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    PersistenceManager.INSTANCE.close(conn);
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Methode pour mettre à jour une company.
     * @param company La company à update.
     * @return false
     */
    @Override
    public boolean update(Company company) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            preparedStatement = conn.prepareStatement("UPDATE company SET name = ? WHERE id = ? ");
            preparedStatement.setString(1, company.getName());
            preparedStatement.setLong(2, company.getId());
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    PersistenceManager.INSTANCE.close(conn);
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Methode pour trouver une company en fonction de son id.
     * @param id L'id de la company à trouver.
     * @return company
     */
    @Override
    public Company find(long id) {
        Company company = new Company();
        Connection conn = PersistenceManager.INSTANCE.connectToDb();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM company WHERE id = " + id;
            stmt = conn.createStatement();
            logger.debug("Send : {}", stmt.toString());
            rs = stmt.executeQuery(sql);
            // Extract data from result set
            if (rs.first()) {
                company.setId(id);
                company.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    PersistenceManager.INSTANCE.close(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return company;
    }

    /**
     * Methode pour avoir une liste de toutes les companies en base. Renvoit
     * sous forme d'arraylist.
     * @return companyList
     */
    public List<Company> findAll() {
        List<Company> companyList = new ArrayList<Company>();
        Connection conn = PersistenceManager.INSTANCE.connectToDb();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM company";
            stmt = conn.createStatement();
            logger.debug("Send : {}", sql);
            rs = stmt.executeQuery(sql);
            Company company;
            while (rs.next()) {
                company = new Company();
                company.setId(rs.getInt("id"));
                company.setName(rs.getString("name"));
                companyList.add(company);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    PersistenceManager.INSTANCE.close(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return companyList;
    }

    /**
     * Methode pour avoir une liste de company de taille maximale définie dans
     * conf.properties. Selectionne les company en fonction de l'indexPage (page
     * 1 à x).
     * @param indexPage Index de la page.
     * @param maxInPage Nombre d'item max dans la page.
     * @return companyList
     */
    public List<Company> findInRange(int indexPage, int maxInPage) {
        List<Company> companyList = new ArrayList<Company>();
        if (indexPage < 1) {
            return companyList;
        }
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            preparedStatement = conn.prepareStatement("SELECT * FROM company LIMIT ? OFFSET ?");
            preparedStatement.setInt(1, maxInPage);
            preparedStatement.setInt(2, (indexPage - 1) * maxInPage);
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();
            rs = preparedStatement.getResultSet();
            Company company;
            while (rs.next()) {
                company = new Company();
                company.setId(rs.getInt("id"));
                company.setName(rs.getString("name"));
                companyList.add(company);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    PersistenceManager.INSTANCE.close(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return companyList;
    }

    /**
     * Methode pour trouver une company en fonction de son name en base.
     * @param name Le nom de la company à trouver.
     * @return company
     */
    public Company findByName(String name) {
        Company company = new Company();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            preparedStatement = conn.prepareStatement("SELECT * FROM company WHERE name = ?");
            preparedStatement.setString(1, name);
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();
            rs = preparedStatement.getResultSet();
            if (rs.first()) {
                company.setId(rs.getInt("id"));
                company.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    PersistenceManager.INSTANCE.close(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return company;
    }

    /**
     * Methode pour trouver l'id d'une company en fonction de son name en base.
     * renvoit toujours le premier resultat.
     * @param name Le nom de la company à trouver.
     * @return id
     */
    public long findIdByName(String name) {
        Long id = (long) 0;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            preparedStatement = conn.prepareStatement("SELECT * FROM company WHERE name = ?");
            preparedStatement.setString(1, name);
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();
            rs = preparedStatement.getResultSet();
            if (rs.first()) {
                id = rs.getLong("id");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    PersistenceManager.INSTANCE.close(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    /**
     * Methode qui renvoit le nombre de row dans la table Company.
     * @return count
     */
    public int getRow() {
        Connection conn = PersistenceManager.INSTANCE.connectToDb();
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            String sql = "SELECT COUNT(*) FROM company";
            stmt = conn.createStatement();
            logger.debug("Send : {}", sql);
            rs = stmt.executeQuery(sql);

            if (rs.first()) {
                count = rs.getInt("COUNT(*)");
            }
        } catch (

        SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    PersistenceManager.INSTANCE.close(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }
}

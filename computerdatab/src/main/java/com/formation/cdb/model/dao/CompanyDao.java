package com.formation.cdb.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.model.Company;
import com.formation.cdb.persistence.PersistenceManager;
import com.formation.cdb.util.Order;

public enum CompanyDao implements Dao<Company> {
    INSTANCE;
    final Logger logger = LoggerFactory.getLogger(CompanyDao.class);

    /**
     * Constructeur qui initialise les properties.
     */
    CompanyDao() {
    }

    /**
     * Methode pour creer une nouvelle company en base, renvoit l'id de la ligne
     * dans la bdd.
     * @param company La company à créer.
     * @return id
     */
    @Override
    public long create(Company company) {
        String sql = "INSERT INTO company(name) VALUES (?)";
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setString(1, company.getName());
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return findIdByName(company.getName());
    }

    /**
     * Methode pour supprimer la company en base, renvoit true si ok.
     * @param company La company à delete.
     * @return result
     */
    @Deprecated
    @Override
    public boolean delete(Company company) {
        boolean result = false;
        String sql = "DELETE FROM company WHERE id = ?";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setLong(1, company.getId());
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            result = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Methode pour supprimer la company en base ainsi que tous les computers
     * associé à cette company, renvoit true si ok.
     * @param id L'id de la company à delete.
     * @return result
     */
    public boolean delete(long id) {
        boolean result = false;

        String sql = "DELETE FROM company WHERE id = ?";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            ComputerDao.INSTANCE.deleteWithCompanyId(id);
            preparedStatement.setLong(1, id);
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();

            result = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        String sql = "UPDATE company SET name = ? WHERE id = ? ";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setLong(2, company.getId());
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        String sql = "SELECT id,name FROM company WHERE id = " + id;

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb(); Statement stmt = conn.createStatement();) {
            logger.debug("Send : {}", stmt.toString());
            try (ResultSet rs = stmt.executeQuery(sql);) {
                if (rs.first()) {
                    company.setId(id);
                    company.setName(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }

    /**
     * Methode pour avoir une liste de toutes les companies en base. Renvoit
     * sous forme d'arraylist.
     * @return companyList
     */
    @Deprecated
    public List<Company> findAll() {
        List<Company> companyList = new ArrayList<Company>();
        String sql = "SELECT id,name FROM company";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb(); Statement stmt = conn.createStatement();) {
            logger.debug("Send : {}", sql);
            try (ResultSet rs = stmt.executeQuery(sql);) {
                Company company;
                while (rs.next()) {
                    company = new Company();
                    company.setId(rs.getInt("id"));
                    company.setName(rs.getString("name"));
                    companyList.add(company);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        String sql = "SELECT id,name FROM company LIMIT ? OFFSET ?";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setInt(1, maxInPage);
            preparedStatement.setInt(2, (indexPage - 1) * maxInPage);
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();
            try (ResultSet rs = preparedStatement.getResultSet();) {
                Company company;
                while (rs.next()) {
                    company = new Company();
                    company.setId(rs.getInt("id"));
                    company.setName(rs.getString("name"));
                    companyList.add(company);
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return companyList;
    }

    /**
     * Methode pour avoir une liste de companies qui ont le mot clé search de
     * taille maximale maxInPage. Selectionne les computer en fonction de
     * l'indexPage (page 1 à x) et ordonné suivant l'enum.
     * @param indexPage Index de la page.
     * @param maxInPage Nombre d'item max dans la list.
     * @param search String to search.
     * @param order Order.
     * @return companyList
     */
    public List<Company> findInRangeSearchName(int indexPage, int maxInPage, String search, Order order) {
        List<Company> companyList = new ArrayList<Company>();
        if (indexPage < 1) {
            return companyList;
        }
        String sql = "SELECT id, name FROM computer WHERE name LIKE ? ORDER BY name %s LIMIT ? OFFSET ? ";
        if (order.equals(Order.DESC)) {
            sql = String.format(sql, "DESC");
        } else {
            sql = String.format(sql, "ASC");
        }
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setString(1, "%" + search + "%");
            preparedStatement.setInt(2, maxInPage);
            preparedStatement.setInt(3, (indexPage - 1) * maxInPage);
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();

            try (ResultSet rs = preparedStatement.getResultSet();) {
                while (rs.next()) {
                    companyList.add(constructCompanyWithResultSet(rs));
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        String sql = "SELECT id,name FROM company WHERE name = ?";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setString(1, name);
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();
            try (ResultSet rs = preparedStatement.getResultSet();) {
                if (rs.first()) {
                    company.setId(rs.getInt("id"));
                    company.setName(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        String sql = "SELECT id FROM company WHERE name = ?";
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setString(1, name);
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();
            try (ResultSet rs = preparedStatement.getResultSet();) {
                if (rs.first()) {
                    id = rs.getLong("id");
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Methode qui renvoit le nombre de row dans la table Company.
     * @return count
     */
    public int getRow() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM company";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb(); Statement stmt = conn.createStatement();) {
            logger.debug("Send : {}", sql);
            try (ResultSet rs = stmt.executeQuery(sql);) {
                if (rs.first()) {
                    count = rs.getInt("COUNT(*)");
                }
            }
        } catch (

        SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Construct a company based on the result set.
     * @param rs Resultset.
     * @return company
     * @throws SQLException Sql exception.
     */
    private Company constructCompanyWithResultSet(ResultSet rs) throws SQLException {
        Company company = new Company.CompanyBuilder().id(rs.getInt("id")).name(rs.getString("name")).build();
        return company;
    }
}

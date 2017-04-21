package com.formation.cdb.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.formation.cdb.model.Company;
import com.formation.cdb.persistence.PersistenceManager;
import com.formation.cdb.util.Order;

@Repository ("companyDao")
public class CompanyDao implements Dao<Company> {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyDao.class);
    final String sqlCreate = "INSERT INTO company(name) VALUES (?)";
    final String sqlDeleteById = "DELETE FROM company WHERE id = ?";
    final String sqlUpdate = "UPDATE company SET name = ? WHERE id = ? ";
    final String sqlFindById = "SELECT id,name FROM company WHERE id = ";
    final String sqlFindAll = "SELECT id,name FROM company";
    final String sqlFindInRange = "SELECT id,name FROM company LIMIT ? OFFSET ?";
    final String sqlFindByName = "SELECT id,name FROM company WHERE name = ?";
    final String sqlFindIdByName = "SELECT id FROM company WHERE name = ?";
    final String sqlCountAll = "SELECT COUNT(*) FROM company";

    /**
     * Methode pour creer une nouvelle company en base, renvoit l'id de la ligne
     * dans la bdd.
     * @param company La company à créer.
     * @return id
     */
    @Override
    public long create(Company company) {
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlCreate);) {
            preparedStatement.setString(1, company.getName());
            LOG.debug("Send : {}", preparedStatement.toString());
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
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlDeleteById);) {
            preparedStatement.setLong(1, company.getId());
            LOG.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Methode pour supprimer la company en base, renvoit true si ok.
     * @param id L'id de la company à delete.
     * @return result
     */
    public boolean delete(long id) {
        boolean result = false;
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlDeleteById);) {
            preparedStatement.setLong(1, id);
            LOG.debug("Send : {}", preparedStatement.toString());
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
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlUpdate);) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setLong(2, company.getId());
            LOG.debug("Send : {}", preparedStatement.toString());
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
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb(); Statement stmt = conn.createStatement();) {
            LOG.debug("Send : ", sqlFindById + id);
            try (ResultSet rs = stmt.executeQuery(sqlFindById + id);) {
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
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb(); Statement stmt = conn.createStatement();) {
            LOG.debug("Send : {}", sqlFindAll);
            try (ResultSet rs = stmt.executeQuery(sqlFindAll);) {
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
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlFindInRange);) {
            preparedStatement.setInt(1, maxInPage);
            preparedStatement.setInt(2, (indexPage - 1) * maxInPage);
            LOG.debug("Send : {}", preparedStatement.toString());
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
            preparedStatement.setString(1, search + "%");
            preparedStatement.setInt(2, maxInPage);
            preparedStatement.setInt(3, (indexPage - 1) * maxInPage);
            LOG.debug("Send : {}", preparedStatement.toString());
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
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlFindByName);) {
            preparedStatement.setString(1, name);
            LOG.debug("Send : {}", preparedStatement.toString());
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
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlFindIdByName);) {
            preparedStatement.setString(1, name);
            LOG.debug("Send : {}", preparedStatement.toString());
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
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb(); Statement stmt = conn.createStatement();) {
            LOG.debug("Send : {}", sqlCountAll);
            try (ResultSet rs = stmt.executeQuery(sqlCountAll);) {
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
        return new Company.CompanyBuilder().id(rs.getInt("id")).name(rs.getString("name")).build();
    }
}

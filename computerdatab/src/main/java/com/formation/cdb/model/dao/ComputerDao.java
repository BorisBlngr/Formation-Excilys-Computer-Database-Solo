package com.formation.cdb.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;
import com.formation.cdb.persistence.PersistenceManager;
import com.formation.cdb.util.DataInfo;
import com.formation.cdb.util.Order;
import com.formation.cdb.util.Search;

public enum ComputerDao implements Dao<Computer> {
    INSTANCE;
    private static final Logger LOG = LoggerFactory.getLogger(ComputerDao.class);
    final String sqlCreate = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
    final String sqlFindById = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE c.id = ";
    final String sqlFindByName = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE c.name = ?";
    final String sqlFindAll = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id";
    final String sqlFindInRange = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id LIMIT ? OFFSET ?";
    final String sqlFindIdByName = "SELECT id FROM computer WHERE name = ?";
    final String sqlCountAll = "SELECT COUNT(id) FROM computer";
    final String sqlCountSearchName = "SELECT COUNT(id) FROM computer WHERE name LIKE ?";
    final String sqlCountSearchCompanyName = "SELECT COUNT(c.id) FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE y.name LIKE ? ";
    final String sqlDeleteByComputer = "DELETE FROM computer WHERE id = ?";
    final String sqlDeleteByCompanyId = "DELETE FROM computer WHERE company_id = ?";
    final String sqlDeleteById = "DELETE FROM computer WHERE id = ?";
    final String sqlUpdate = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";

    /**
     * Constructeur.
     */
    ComputerDao() {
    }

    /**
     * Check if the Computer have a name. Check the dates.
     * @param computer Computer.
     * @return result
     */
    private boolean computerIsValid(Computer computer) {
        if (computer.getName().equals(null) || computer.getName().trim().isEmpty()) {
            LOG.error("A computer has no name !");
            return false;
        } else if (computer.getDiscontinued() != null && computer.getIntroduced() == null) {
            LOG.error("Discontinued Date but no Introduced Date!");
            return false;
        } else if (computer.getDiscontinued() != null
                && computer.getDiscontinued().isBefore(computer.getIntroduced())) {
            LOG.error("Discontinued Date before Introduced Date!");
            return false;
        }
        return true;
    }

    /**
     * Construct a computer based on the result set.
     * @param rs Resultset.
     * @return computer
     * @throws SQLException Sql exception.
     */
    private Computer constructComputerWithResultSet(ResultSet rs) throws SQLException {
        Computer computer = new Computer();
        computer.setId(rs.getInt("id"));
        computer.setName(rs.getString("name"));
        computer.setCompany(new Company.CompanyBuilder().id(rs.getLong("y.id")).name(rs.getString("y.name")).build());
        if (rs.getDate("introduced") != null) {
            computer.setIntroduced(rs.getDate("introduced").toLocalDate());
        }
        if (rs.getDate("discontinued") != null) {
            computer.setDiscontinued(rs.getDate("discontinued").toLocalDate());
        }
        return computer;
    }

    /**
     * Methode pour trouver un computer en base en fonction de son id, renvoit
     * le premier resultat.
     * @param id L'id du computer à trouver.
     * @return computer
     */
    @Override
    public Computer find(long id) {
        Computer computer = new Computer();

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb(); Statement stmt = conn.createStatement();) {
            LOG.debug("Send : {}", sqlFindById + id);
            try (ResultSet rs = stmt.executeQuery(sqlFindById + id);) {
                // Extract data from result set
                if (rs.first()) {
                    computer = constructComputerWithResultSet(rs);
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return computer;
    }

    /**
     * Methode pour trouver un computer en fonction de son nom.
     * @param name Le nom du computer à trouver.
     * @return computer
     */
    public Computer findByName(String name) {
        Computer computer = new Computer();
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlFindByName);) {
            preparedStatement.setString(1, name);
            LOG.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();
            try (ResultSet rs = preparedStatement.getResultSet();) {
                if (rs.first()) {
                    computer = constructComputerWithResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return computer;
    }

    /**
     * Methode pour avoir une liste de tous les computer en base. Pas optimisée
     * pour les grosses bdd.
     * @return computerList
     */
    public List<Computer> findAll() {
        List<Computer> computerList = new ArrayList<Computer>();
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb(); Statement stmt = conn.createStatement();) {
            LOG.debug("Send : {}", sqlFindAll);

            try (ResultSet rs = stmt.executeQuery(sqlFindAll);) {
                // Extract data from result set
                while (rs.next()) {
                    computerList.add(constructComputerWithResultSet(rs));
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return computerList;
    }

    /**
     * Methode pour avoir une liste de computer de taille maximale maxInPage.
     * Selectionne les computer en fonction de l'indexPage (page 1 à x).
     * @param indexPage Index de la page.
     * @param maxInPage Nombre d'item max dans la list.
     * @return companyList
     */
    public List<Computer> findInRange(int indexPage, int maxInPage) {
        List<Computer> computerList = new ArrayList<Computer>();
        if (indexPage < 1) {
            return computerList;
        }
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlFindInRange);) {
            preparedStatement.setInt(1, maxInPage);
            preparedStatement.setInt(2, (indexPage - 1) * maxInPage);
            LOG.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();

            try (ResultSet rs = preparedStatement.getResultSet();) {
                while (rs.next()) {
                    computerList.add(constructComputerWithResultSet(rs));
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return computerList;
    }

    /**
     * Methode pour avoir une liste de computer qui ont le mot clé search de
     * taille maximale maxInPage. Selectionne les computer en fonction de
     * l'indexPage (page 1 à x) et ordonné suivant l'enum.
     * @param indexPage Index de la page.
     * @param maxInPage Nombre d'item max dans la list.
     * @param search String to search.
     * @param filterBy Sort the list by.
     * @param order Order.
     * @return computerList
     */
    public List<Computer> findInRangeSearchName(int indexPage, int maxInPage, String search, Search filterBy,
            Order order) {
        List<Computer> computerList = new ArrayList<Computer>();
        String sql = "";
        if (indexPage < 1) {
            return computerList;
        }
        if (filterBy.equals(Search.COMPANIES)) {
            sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE c.name LIKE ? ORDER BY y.name %s LIMIT ? OFFSET ? ";
        } else {
            sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE c.name LIKE ? ORDER BY c.name %s LIMIT ? OFFSET ? ";
        }
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
                    computerList.add(constructComputerWithResultSet(rs));
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return computerList;
    }

    /**
     * Methode pour avoir une liste de computer qui ont le mot clé search
     * présent dans le nom de la company, de taille maximale maxInPage.
     * Selectionne les computer en fonction de l'indexPage (page 1 à x) et
     * ordonné suivant l'enum.
     * @param indexPage Index de la page.
     * @param maxInPage Nombre d'item max dans la list.
     * @param search String to search.
     * @param order Order.
     * @param filterBy Sort the list by.
     * @return computerList
     */
    public List<Computer> findInRangeSearchCompanyName(int indexPage, int maxInPage, String search, Search filterBy,
            Order order) {
        List<Computer> computerList = new ArrayList<Computer>();
        String sql = "";
        if (indexPage < 1) {
            return computerList;
        }
        if (search.equals(Search.COMPANIES)) {
            sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE y.name LIKE ? ORDER BY y.name %s LIMIT ? OFFSET ? ";
        } else {
            sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE y.name LIKE ? ORDER BY c.name %s LIMIT ? OFFSET ? ";
        }
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
                    computerList.add(constructComputerWithResultSet(rs));
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return computerList;
    }

    /**
     * Methode pour trouver l'id d'un computer en fonction de son nom. renvoit
     * le premier resultat.
     * @param name Le nom du computer à trouver.
     * @return id
     */
    public long findIdByName(String name) {
        long id = (long) 0;

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
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Renvoit le nombre de row dans Computer.
     * @return count
     */
    public int getRow() {
        int count = 0;
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb(); Statement stmt = conn.createStatement();) {
            LOG.debug("Send : {}", sqlCountAll);
            try (ResultSet rs = stmt.executeQuery(sqlCountAll);) {
                if (rs.first()) {
                    count = rs.getInt("COUNT(id)");
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Renvoit le nombre de row dans Computer.
     * @param search String to search.
     * @return count
     */
    public int getRowSearchName(String search) {
        int count = 0;
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlCountSearchName);) {
            preparedStatement.setString(1, search + "%");
            LOG.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();

            try (ResultSet rs = preparedStatement.getResultSet();) {
                if (rs.first()) {
                    count = rs.getInt("COUNT(id)");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Renvoit le nombre de row dans Computer ayant une company name like
     * search.
     * @param search String to search.
     * @return count
     */
    public int getRowSearchCompanyName(String search) {
        int count = 0;
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlCountSearchCompanyName);) {
            preparedStatement.setString(1, search + "%");
            LOG.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();

            try (ResultSet rs = preparedStatement.getResultSet();) {
                if (rs.first()) {
                    count = rs.getInt("COUNT(c.id)");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Methode pour creer un nouveau computer en base, renvoit l'id de la ligne
     * dans la bdd.
     * @param cmpt Le computer à créer.
     * @return id
     */
    @Override
    public long create(Computer cmpt) {
        if (!computerIsValid(cmpt)) {
            return 0;
        }
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlCreate);) {

            preparedStatement.setString(1, cmpt.getName());
            if (cmpt.getIntroduced() == null) {
                preparedStatement.setNull(2, Types.TIMESTAMP);
            } else {
                preparedStatement.setDate(2, java.sql.Date.valueOf(cmpt.getIntroduced()));
            }
            if (cmpt.getDiscontinued() == null) {
                preparedStatement.setNull(3, Types.TIMESTAMP);
            } else {
                preparedStatement.setDate(3, java.sql.Date.valueOf(cmpt.getDiscontinued()));
            }
            if (cmpt.getCompany().getId() == 0) {
                preparedStatement.setNull(4, Types.INTEGER);
            } else {
                preparedStatement.setLong(4, cmpt.getCompany().getId());
            }
            LOG.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();
            DataInfo.INSTANCE.increaseComputerCount();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return findIdByName(cmpt.getName());
    }

    /**
     * Methode pour delete un computer en fonction de son id. renvoit le result
     * de sendToExec.
     * @param computer Le computer à delete.
     * @return result
     */
    @Override
    public boolean delete(Computer computer) {
        boolean result = false;
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlDeleteByComputer);) {
            preparedStatement.setLong(1, computer.getId());
            LOG.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();
            DataInfo.INSTANCE.decreaseComputerCount();
            result = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Method to delete all computers with a specific company_id.
     * @param id Id of the company.
     * @return result
     */
    public boolean deleteWithCompanyId(long id) {
        boolean result = false;
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlDeleteByCompanyId);) {
            preparedStatement.setLong(1, id);
            LOG.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();
            DataInfo.INSTANCE.synchronizedWithDb();
            result = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Methode pour delete un computer en fonction de son id. renvoit le result
     * de sendToExec
     * @param id L'id du computer à delete
     * @return result
     */
    public boolean delete(long id) {
        boolean result = false;
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlDeleteById);) {
            preparedStatement.setLong(1, id);
            LOG.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();
            DataInfo.INSTANCE.decreaseComputerCount();
            result = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Methode pour update un computer en fonction de son id. Renvoit le result
     * de sendToExec. Attention avec l'id.
     * @param cmpt Le computer à update.
     * @return result
     */
    @Override
    public boolean update(Computer cmpt) {
        if (!computerIsValid(cmpt)) {
            return false;
        }
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sqlUpdate);) {

            preparedStatement.setString(1, cmpt.getName());
            if (cmpt.getIntroduced() == null) {
                preparedStatement.setNull(2, Types.TIMESTAMP);
            } else {
                preparedStatement.setDate(2, java.sql.Date.valueOf(cmpt.getIntroduced()));
            }
            if (cmpt.getDiscontinued() == null) {
                preparedStatement.setNull(3, Types.TIMESTAMP);
            } else {
                preparedStatement.setDate(3, java.sql.Date.valueOf(cmpt.getDiscontinued()));
            }
            if (cmpt.getCompany().getId() == 0) {
                preparedStatement.setNull(4, Types.INTEGER);
            } else {
                preparedStatement.setLong(4, cmpt.getCompany().getId());
            }
            preparedStatement.setLong(5, cmpt.getId());
            LOG.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
}

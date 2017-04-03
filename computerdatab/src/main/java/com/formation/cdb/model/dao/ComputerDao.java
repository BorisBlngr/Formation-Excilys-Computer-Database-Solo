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
import com.formation.cdb.util.Order;

public enum ComputerDao implements Dao<Computer> {
    INSTANCE;
    final Logger logger = LoggerFactory.getLogger(ComputerDao.class);

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
            logger.error("A computer has no name !");
            return false;
        } else if (!computer.getDiscontinued().equals(null) && computer.getIntroduced().equals(null)) {
            logger.error("Discontinued Date but no Introduced Date!");
            return false;
        } else if (!computer.getDiscontinued().equals(null)
                && computer.getDiscontinued().isBefore(computer.getIntroduced())) {
            logger.error("Discontinued Date before Introduced Date!");
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
            String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE c.id = "
                    + id;
            logger.debug("Send : {}", sql);
            try (ResultSet rs = stmt.executeQuery(sql);) {
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
     * Methode pour trouver un computerUi en base en fonction de son id, renvoit
     * le premier resultat. Une seule requete.
     * @param id L'id du computer à trouver.
     * @return computer
     */
    @Deprecated
    public Computer findComputer(long id) {
        Computer computer = new Computer();
        String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE c.id = "
                + id;
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb(); Statement stmt = conn.createStatement();) {

            logger.debug("Send : {}", sql);
            try (ResultSet rs = stmt.executeQuery(sql);) {
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
        String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE c.name = ?";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setString(1, name);
            logger.debug("Send : {}", preparedStatement.toString());
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

        String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb(); Statement stmt = conn.createStatement();) {
            logger.debug("Send : {}", sql);

            try (ResultSet rs = stmt.executeQuery(sql);) {
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
        String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id LIMIT ? OFFSET ?";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setInt(1, maxInPage);
            preparedStatement.setInt(2, (indexPage - 1) * maxInPage);
            logger.debug("Send : {}", preparedStatement.toString());
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
     * @param order Order.
     * @return computerList
     */
    public List<Computer> findInRangeSearchName(int indexPage, int maxInPage, String search, Order order) {
        List<Computer> computerList = new ArrayList<Computer>();
        if (indexPage < 1) {
            return computerList;
        }
        String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE c.name LIKE ? ORDER BY c.name %s LIMIT ? OFFSET ? ";
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
     * @return computerList
     */
    public List<Computer> findInRangeSearchCompanyName(int indexPage, int maxInPage, String search, Order order) {
        List<Computer> computerList = new ArrayList<Computer>();
        if (indexPage < 1) {
            return computerList;
        }
        String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE y.name LIKE ? ORDER BY c.name %s LIMIT ? OFFSET ? ";
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
                PreparedStatement preparedStatement = conn
                        .prepareStatement("SELECT id FROM computer WHERE name = ?");) {
            preparedStatement.setString(1, name);
            logger.debug("Send : {}", preparedStatement.toString());
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
        String sql = "SELECT COUNT(id) FROM computer";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb(); Statement stmt = conn.createStatement();) {
            logger.debug("Send : {}", sql);
            try (ResultSet rs = stmt.executeQuery(sql);) {

                if (rs.first()) {
                    count = rs.getInt("COUNT(id)");
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
     * Renvoit le nombre de row dans Computer.
     * @param search String to search.
     * @return count
     */
    public int getRowSearchName(String search) {
        int count = 0;
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn
                        .prepareStatement("SELECT COUNT(id) FROM computer WHERE name LIKE ?");) {
            preparedStatement.setString(1, "%" + search + "%");
            logger.debug("Send : {}", preparedStatement.toString());
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
     * Renvoit le nombre de row dans Computer ayant une company name like search.
     * @param search String to search.
     * @return count
     */
    public int getRowSearchCompanyName(String search) {
        int count = 0;
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn
                        .prepareStatement("SELECT COUNT(c.id) FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE y.name LIKE ? ");) {
            preparedStatement.setString(1, "%" + search + "%");
            logger.debug("Send : {}", preparedStatement.toString());
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

        String sql = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?)";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {

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
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return

        findIdByName(cmpt.getName());
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
        String sql = "DELETE FROM computer WHERE id = ?";
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
            preparedStatement.setLong(1, computer.getId());
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
     * Methode pour delete un computer en fonction de son id. renvoit le result
     * de sendToExec
     * @param id L'id du computer à delete
     * @return result
     */
    public boolean delete(long id) {
        boolean result = false;
        String sql = "DELETE FROM computer WHERE id = ?";
        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
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
     * Methode pour update un computer en fonction de son id. Renvoit le result
     * de sendToExec. Attention avec l'id.
     * @param cmpt Le computer à update.
     * @return result
     */
    // Careful with the computer's id
    @Override
    public boolean update(Computer cmpt) {

        if (!computerIsValid(cmpt)) {
            return false;
        }

        String sql = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);) {

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
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}

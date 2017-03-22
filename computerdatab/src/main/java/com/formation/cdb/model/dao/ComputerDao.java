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
import com.formation.cdb.model.dto.ComputerDto;
import com.formation.cdb.persistence.PersistenceManager;

public enum ComputerDao implements Dao<ComputerDto> {
    INSTANCE;
    final Logger logger = LoggerFactory.getLogger(ComputerDao.class);

    /**
     * Constructeur.
     */
    ComputerDao() {
    }

    /**
     * Methode pour trouver un computer en base en fonction de son id, renvoit
     * le premier resultat.
     * @param id L'id du computer à trouver.
     * @return computer
     */
    @Override
    public ComputerDto find(long id) {
        ComputerDto computerDto = new ComputerDto();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE c.id = "
                    + id;
            stmt = conn.createStatement();
            logger.debug("Send : {}", sql);
            rs = stmt.executeQuery(sql);
            // Extract data from result set
            if (rs.first()) {
                computerDto.setId(id);
                computerDto.setName(rs.getString("c.name"));
                computerDto.setCompany(
                        new Company.CompanyBuilder().id(rs.getLong("y.id")).name(rs.getString("y.name")).build());

                if (rs.getDate("c.introduced") != null) {
                    computerDto.setIntroduced(rs.getDate("c.introduced").toLocalDate());
                }
                if (rs.getDate("c.discontinued") != null) {
                    computerDto.setDiscontinued(rs.getDate("c.discontinued").toLocalDate());
                }
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
        return computerDto;
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
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE c.id = "
                    + id;
            stmt = conn.createStatement();
            logger.debug("Send : {}", sql);
            rs = stmt.executeQuery(sql);
            // Extract data from result set
            if (rs.first()) {
                computer.setId(id);
                computer.setName(rs.getString("c.name"));
                computer.setCompany(
                        new Company.CompanyBuilder().id(rs.getLong("y.id")).name(rs.getString("y.name")).build());

                if (rs.getDate("c.introduced") != null) {
                    computer.setIntroduced(rs.getDate("c.introduced").toLocalDate());
                }
                if (rs.getDate("c.discontinued") != null) {
                    computer.setDiscontinued(rs.getDate("c.discontinued").toLocalDate());
                }
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
        return computer;
    }

    /**
     * Methode pour trouver un computer en fonction de son nom.
     * @param name Le nom du computer à trouver.
     * @return computer
     */
    public ComputerDto findByName(String name) {

        ComputerDto computerDto = new ComputerDto();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            preparedStatement = conn.prepareStatement(
                    "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE c.name = ?");
            preparedStatement.setString(1, name);
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();
            rs = preparedStatement.getResultSet();
            if (rs.first()) {
                computerDto.setId(rs.getInt("id"));
                computerDto.setName(rs.getString("name"));
                computerDto.setCompany(
                        new Company.CompanyBuilder().id(rs.getLong("y.id")).name(rs.getString("y.name")).build());
                if (rs.getDate("introduced") != null) {
                    computerDto.setIntroduced(rs.getDate("introduced").toLocalDate());
                }
                if (rs.getDate("discontinued") != null) {
                    computerDto.setDiscontinued(rs.getDate("discontinued").toLocalDate());
                }
            }
        } catch (SQLException e) {
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
        return computerDto;
    }

    /**
     * Methode pour avoir une liste de tous les computer en base. Pas optimisée
     * pour les grosses bdd.
     * @return computerList
     */
    public List<ComputerDto> findAll() {
        List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
        ComputerDto computerDto;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            String sql = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id";
            stmt = conn.createStatement();
            logger.debug("Send : {}", sql);
            rs = stmt.executeQuery(sql);
            // Extract data from result set
            while (rs.next()) {
                computerDto = new ComputerDto();
                computerDto.setId(rs.getLong("c.id"));
                computerDto.setName(rs.getString("c.name"));
                computerDto.setCompany(
                        new Company.CompanyBuilder().id(rs.getLong("y.id")).name(rs.getString("y.name")).build());
                if (rs.getDate("c.introduced") != null) {
                    computerDto.setIntroduced(rs.getDate("c.introduced").toLocalDate());
                }
                if (rs.getDate("c.discontinued") != null) {
                    computerDto.setDiscontinued(rs.getDate("c.discontinued").toLocalDate());
                }
                computerDtoList.add(computerDto);
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
        return computerDtoList;
    }

    /**
     * Methode pour avoir une liste de computer de taille maximale maxInPage.
     * Selectionne les computer en fonction de l'indexPage (page 1 à x).
     * @param indexPage Index de la page.
     * @param maxInPage Nombre d'item max dans la list.
     * @return companyList
     */
    public List<ComputerDto> findInRange(int indexPage, int maxInPage) {
        List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
        if (indexPage < 1) {
            return computerDtoList;
        }
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            preparedStatement = conn.prepareStatement(
                    "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id LIMIT ? OFFSET ?");
            preparedStatement.setInt(1, maxInPage);
            preparedStatement.setInt(2, (indexPage - 1) * maxInPage);
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();
            rs = preparedStatement.getResultSet();
            ComputerDto computerDto;
            while (rs.next()) {
                computerDto = new ComputerDto();
                computerDto.setId(rs.getInt("id"));
                computerDto.setName(rs.getString("name"));
                computerDto.setCompany(
                        new Company.CompanyBuilder().id(rs.getLong("y.id")).name(rs.getString("y.name")).build());
                if (rs.getDate("introduced") != null) {
                    computerDto.setIntroduced(rs.getDate("introduced").toLocalDate());
                }
                if (rs.getDate("discontinued") != null) {
                    computerDto.setDiscontinued(rs.getDate("discontinued").toLocalDate());
                }
                computerDtoList.add(computerDto);
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
        return computerDtoList;
    }

    /**
     * Methode pour trouver l'id d'un computer en fonction de son nom. renvoit
     * le premier resultat.
     * @param name Le nom du computer à trouver.
     * @return id
     */
    public long findIdByName(String name) {
        long id = (long) 0;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            preparedStatement = conn.prepareStatement("SELECT * FROM computer WHERE name = ?");
            preparedStatement.setString(1, name);
            logger.debug("Send : {}", preparedStatement.toString());
            preparedStatement.execute();
            rs = preparedStatement.getResultSet();
            if (rs.first()) {
                id = rs.getLong("id");
            }
        } catch (SQLException e) {
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
     * Renvoit le nombre de row dans Computer.
     * @return count
     */
    public int getRow() {
        Connection conn = PersistenceManager.INSTANCE.connectToDb();
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            String sql = "SELECT COUNT(*) FROM computer";
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

    /**
     * Methode pour creer un nouveau computer en base, renvoit l'id de la ligne
     * dans la bdd.
     * @param cmpt Le computer à créer.
     * @return id
     */
    @Override
    public long create(ComputerDto cmpt) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        if (cmpt.getName().equals(null) || cmpt.getName().trim().isEmpty()) {
            logger.error("A computer has no name !");
            return 0;
        }

        if (!cmpt.getDiscontinued().equals(null) && cmpt.getIntroduced().equals(null)) {
            logger.error("Discontinued Date but no Introduced Date!");
            return 0;
        }
        if (!cmpt.getDiscontinued().equals(null) && cmpt.getDiscontinued().isBefore(cmpt.getIntroduced())) {
            logger.error("Discontinued Date before Introduced Date!");
            return 0;
        }

        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            preparedStatement = conn
                    .prepareStatement("INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?)");

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
    public boolean delete(ComputerDto computer) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            preparedStatement = conn.prepareStatement("DELETE FROM computer WHERE id = ?");
            preparedStatement.setLong(1, computer.getId());
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
     * Methode pour delete un computer en fonction de son id. renvoit le result
     * de sendToExec
     * @param id L'id du computer à delete
     * @return result
     */
    public boolean delete(long id) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            preparedStatement = conn.prepareStatement("DELETE FROM computer WHERE id = ?");
            preparedStatement.setLong(1, id);
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
     * Methode pour update un computer en fonction de son id. Renvoit le result
     * de sendToExec. Attention avec l'id.
     * @param cmpt Le computer à update.
     * @return result
     */
    // Careful with the computer's id
    @Override
    public boolean update(ComputerDto cmpt) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        if (cmpt.getName().equals(null) || cmpt.getName().trim().isEmpty()) {
            logger.error("A computer has no name !");
            return false;
        }

        if (!cmpt.getDiscontinued().equals(null) && cmpt.getIntroduced().equals(null)) {
            logger.error("Discontinued Date but no Introduced Date!");
            return false;
        }
        if (!cmpt.getDiscontinued().equals(null) && cmpt.getDiscontinued().isBefore(cmpt.getIntroduced())) {
            logger.error("Discontinued Date before Introduced Date!");
            return false;
        }

        try {
            conn = PersistenceManager.INSTANCE.connectToDb();
            preparedStatement = conn.prepareStatement(
                    "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?");

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
}

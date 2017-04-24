package com.formation.cdb.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.formation.cdb.mapper.ComputerRowMapper;
import com.formation.cdb.model.Computer;
import com.formation.cdb.util.DataInfo;
import com.formation.cdb.util.Order;
import com.formation.cdb.util.Search;

@Repository("computerDao")
public class ComputerDao implements Dao<Computer> {
    @Autowired
    DataInfo dataInfo;
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(ComputerDao.class);
    final String sqlCreate = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
    final String sqlFindById = "SELECT c.id, c.name, c.introduced, c.discontinued, y.id, y.name  FROM computer c LEFT JOIN company y ON c.company_id=y.id WHERE c.id = ?";
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
     * Methode pour trouver un computer en base en fonction de son id, renvoit
     * le premier resultat.
     * @param id L'id du computer à trouver.
     * @return computer
     */
    @Override
    @SuppressWarnings({"unchecked"})
    public Computer find(long id) {
        try {
            Computer computer = (Computer) jdbcTemplate.queryForObject(sqlFindById, new ComputerRowMapper(), id);
            return computer;
        } catch (EmptyResultDataAccessException e) {
            return new Computer();
        }
    }

    /**
     * Methode pour trouver un computer en fonction de son nom.
     * @param name Le nom du computer à trouver.
     * @return computer
     */
    @SuppressWarnings({"unchecked"})
    public Computer findByName(String name) {
        try {
            Computer computer = (Computer) jdbcTemplate.queryForObject(sqlFindByName, new ComputerRowMapper(), name);
            return computer;
        } catch (EmptyResultDataAccessException e) {
            return new Computer();
        }
    }

    /**
     * Methode pour avoir une liste de tous les computer en base. Pas optimisée
     * pour les grosses bdd.
     * @return computerList
     */
    @SuppressWarnings({"unchecked"})
    public List<Computer> findAll() {
        try {
            List<Computer> computerList = jdbcTemplate.query(sqlFindAll, new ComputerRowMapper());
            return computerList;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Computer>();
        }
    }

    /**
     * Methode pour avoir une liste de computer de taille maximale maxInPage.
     * Selectionne les computer en fonction de l'indexPage (page 1 à x).
     * @param indexPage Index de la page.
     * @param maxInPage Nombre d'item max dans la list.
     * @return companyList
     */
    @SuppressWarnings({"unchecked"})
    public List<Computer> findInRange(int indexPage, int maxInPage) {
        try {
            List<Computer> computerList = jdbcTemplate.query(sqlFindInRange, new ComputerRowMapper(), maxInPage,
                    (indexPage - 1) * maxInPage);
            return computerList;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Computer>();
        }
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
    @SuppressWarnings({"unchecked"})
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
        try {
            computerList = jdbcTemplate.query(sql, new ComputerRowMapper(), search + "%", maxInPage,
                    (indexPage - 1) * maxInPage);
            return computerList;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Computer>();
        }
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
    @SuppressWarnings("unchecked")
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
        try {
            computerList = jdbcTemplate.query(sql, new ComputerRowMapper(), search + "%", maxInPage,
                    (indexPage - 1) * maxInPage);
            return computerList;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Computer>();
        }
    }

    /**
     * Methode pour trouver l'id d'un computer en fonction de son nom. renvoit
     * le premier resultat.
     * @param name Le nom du computer à trouver.
     * @return id
     */
    public long findIdByName(String name) {
        try {
            return (Long) jdbcTemplate.queryForObject(sqlFindIdByName, Long.class, name);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    /**
     * Renvoit le nombre de row dans Computer.
     * @return count
     */
    public int getRow() {
        return jdbcTemplate.queryForObject(sqlCountAll, Integer.class);
    }

    /**
     * Renvoit le nombre de row dans Computer.
     * @param search String to search.
     * @return count
     */
    public int getRowSearchName(String search) {
        return jdbcTemplate.queryForObject(sqlCountSearchName, Integer.class, search + "%");
    }

    /**
     * Renvoit le nombre de row dans Computer ayant une company name like
     * search.
     * @param search String to search.
     * @return count
     */
    public int getRowSearchCompanyName(String search) {
        return jdbcTemplate.queryForObject(sqlCountSearchCompanyName, Integer.class, search + "%");
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
        Object[] params = new Object[4];
        params[0] = cmpt.getName();
        if (cmpt.getIntroduced() == null) {
            params[1] = null;
        } else {
            params[1] = java.sql.Date.valueOf(cmpt.getIntroduced());
        }
        if (cmpt.getDiscontinued() == null) {
            params[2] = null;
        } else {
            params[2] = java.sql.Date.valueOf(cmpt.getDiscontinued());
        }
        if (cmpt.getCompany().getId() == 0) {
            params[3] = null;
        } else {
            params[3] = cmpt.getCompany().getId();
        }
        int returnValue = jdbcTemplate.update(sqlCreate, params);
        if (1 == returnValue) {
            dataInfo.increaseComputerCount();
            return findIdByName(cmpt.getName());
        } else {
            return 0;
        }
    }

    /**
     * Methode pour delete un computer en fonction de son id. renvoit le result
     * de sendToExec.
     * @param computer Le computer à delete.
     * @return result
     */
    @Override
    public boolean delete(Computer computer) {
        int returnValue = jdbcTemplate.update(sqlDeleteByComputer, computer.getId());
        if (returnValue == 1) {
            dataInfo.decreaseComputerCount();
        }
        return returnValue == 1;
    }

    /**
     * Method to delete all computers with a specific company_id.
     * @param id Id of the company.
     * @return result
     */
    public boolean deleteWithCompanyId(long id) {
        int returnValue = jdbcTemplate.update(sqlDeleteByCompanyId, id);
        return returnValue == 1;
    }

    /**
     * Methode pour delete un computer en fonction de son id. renvoit le result
     * de sendToExec
     * @param id L'id du computer à delete
     * @return result
     */
    public boolean delete(long id) {
        int returnValue = jdbcTemplate.update(sqlDeleteById, id);
        if (returnValue == 1) {
            dataInfo.decreaseComputerCount();
        }
        return returnValue == 1;
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

        Object[] params = new Object[5];
        params[0] = cmpt.getName();
        if (cmpt.getIntroduced() == null) {
            params[1] = null;
        } else {
            params[1] = java.sql.Date.valueOf(cmpt.getIntroduced());
        }
        if (cmpt.getDiscontinued() == null) {
            params[2] = null;
        } else {
            params[2] = java.sql.Date.valueOf(cmpt.getDiscontinued());
        }
        if (cmpt.getCompany().getId() == 0) {
            params[3] = null;
        } else {
            params[3] = cmpt.getCompany().getId();
        }
        params[4] = cmpt.getId();
        int returnValue = jdbcTemplate.update(sqlUpdate, params);
        return returnValue == 1;
    }
}

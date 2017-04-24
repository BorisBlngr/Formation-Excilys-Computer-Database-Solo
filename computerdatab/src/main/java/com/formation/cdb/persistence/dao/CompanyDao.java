package com.formation.cdb.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.formation.cdb.model.Company;
import com.formation.cdb.util.Order;

@Repository("companyDao")
public class CompanyDao implements Dao<Company> {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(CompanyDao.class);
    final String sqlCreate = "INSERT INTO company(name) VALUES (?)";
    final String sqlDeleteById = "DELETE FROM company WHERE id = ?";
    final String sqlUpdate = "UPDATE company SET name = ? WHERE id = ? ";
    final String sqlFindById = "SELECT id,name FROM company WHERE id = ?";
    final String sqlFindAll = "SELECT id,name FROM company";
    final String sqlFindInRange = "SELECT id,name FROM company LIMIT ? OFFSET ?";
    final String sqlFindByName = "SELECT id,name FROM company WHERE name = ?";
    final String sqlFindIdByName = "SELECT id FROM company WHERE name = ?";
    final String sqlCountAll = "SELECT COUNT(id) FROM company";

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * Methode pour creer une nouvelle company en base, renvoit l'id de la ligne
     * dans la bdd.
     * @param company La company à créer.
     * @return id
     */
    @Override
    public long create(Company company) {
        int returnValue = getJdbcTemplate().update(sqlCreate, company.getName());
        if (1 == returnValue) {
            return findIdByName(company.getName());
        } else {
            return 0;
        }
    }

    /**
     * Methode pour supprimer la company en base, renvoit true si ok.
     * @param company La company à delete.
     * @return result
     */
    @Deprecated
    @Override
    public boolean delete(Company company) {
        int returnValue = getJdbcTemplate().update(sqlDeleteById, company.getId());
        return returnValue == 1;
    }

    /**
     * Methode pour supprimer la company en base, renvoit true si ok.
     * @param id L'id de la company à delete.
     * @return result
     */
    public boolean delete(long id) {
        int returnValue = getJdbcTemplate().update(sqlDeleteById, id);
        return returnValue == 1;
    }

    /**
     * Methode pour mettre à jour une company.
     * @param company La company à update.
     * @return false
     */
    @Override
    public boolean update(Company company) {
        int returnValue = getJdbcTemplate().update(sqlUpdate, company.getName(), company.getId());
        return returnValue == 1;
    }

    /**
     * Methode pour trouver une company en fonction de son id.
     * @param id L'id de la company à trouver.
     * @return company
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Company find(long id) {
        try {
            Company company = (Company) getJdbcTemplate().queryForObject(sqlFindById,
                    new BeanPropertyRowMapper(Company.class), id);
            return company;
        } catch (EmptyResultDataAccessException e) {
            return new Company();
        }
    }

    /**
     * Methode pour avoir une liste de toutes les companies en base. Renvoit
     * sous forme d'arraylist.
     * @return companyList
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Deprecated
    public List<Company> findAll() {
        List<Company> companyList = getJdbcTemplate().query(sqlFindAll, new BeanPropertyRowMapper(Company.class));
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
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Company> findInRange(int indexPage, int maxInPage) {
        List<Company> companyList = new ArrayList<Company>();
        if (indexPage < 1) {
            return companyList;
        }
        companyList = getJdbcTemplate().query(sqlFindInRange, new BeanPropertyRowMapper(Company.class), maxInPage,
                (indexPage - 1) * maxInPage);
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
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Company> findInRangeSearchName(int indexPage, int maxInPage, String search, Order order) {
        List<Company> companyList = new ArrayList<Company>();
        if (indexPage < 1) {
            return companyList;
        }
        String sql = "SELECT id, name FROM computer WHERE name LIKE ? ORDER BY name %s LIMIT ? OFFSET ? ";
        if (order.equals(Order.DESC)) {
            sql = String.format(sql, "DESC");
            return companyList = getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Company.class), search + "%",
                    maxInPage, (indexPage - 1) * maxInPage);
        } else {
            sql = String.format(sql, "ASC");
            return companyList = getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Company.class), search + "%",
                    maxInPage, (indexPage - 1) * maxInPage);
        }
    }

    /**
     * Methode pour trouver une company en fonction de son name en base.
     * @param name Le nom de la company à trouver.
     * @return company
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Company findByName(String name) {
        try {
            Company company = (Company) getJdbcTemplate().queryForObject(sqlFindByName,
                    new BeanPropertyRowMapper(Company.class), name);
            return company;
        } catch (EmptyResultDataAccessException e) {
            return new Company();
        }
    }

    /**
     * Methode pour trouver l'id d'une company en fonction de son name en base.
     * renvoit toujours le premier resultat.
     * @param name Le nom de la company à trouver.
     * @return id
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public long findIdByName(String name) {
        try {
            Company company = (Company) getJdbcTemplate().queryForObject(sqlFindByName,
                    new BeanPropertyRowMapper(Company.class), name);
            return company.getId();
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    /**
     * Methode qui renvoit le nombre de row dans la table Company.
     * @return count
     */
    public int getRow() {
        return getJdbcTemplate().queryForObject(sqlCountAll, Integer.class);
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

package com.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;

@SuppressWarnings("rawtypes")
public class ComputerRowMapper implements RowMapper {

    @Override
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return constructComputerWithResultSet(rs);
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
}

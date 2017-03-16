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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;
import com.formation.cdb.ui.ComputerUi;

public enum ComputerDao implements Dao<Computer> {
	INSTANCE;
	final Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	final Properties prop = new Properties();

	private ComputerDao() {
		InputStream input = null;
		try {
			input = new FileInputStream("src.main/resource/conf.properties");
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
	 * Methode pour trouver un computer en base en fonction de son id, renvoit
	 * le premier resultat.
	 * 
	 * @param id
	 *            L'id du computer à trouver.
	 * @return computer
	 */
	@Override
	public Computer find(long id) {
		Computer computer = new Computer();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = PersistenceManager.INSTANCE.connectToDb();
			String sql = "SELECT * FROM computer WHERE id = " + id;
			stmt = conn.createStatement();
			logger.debug("Send : {}", sql);
			rs = stmt.executeQuery(sql);
			// Extract data from result set
			if (rs.first()) {
				computer.setId(id);
				computer.setName(rs.getString("name"));
				computer.setCompanyId(rs.getInt("company_id"));
				if (rs.getDate("introduced") != null)
					computer.setIntroduced(rs.getDate("introduced").toLocalDate());
				if (rs.getDate("discontinued") != null) {
					computer.setDiscontinued(rs.getDate("discontinued").toLocalDate());
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
	 * Methode pour trouver un computerUi en base en fonction de son id, renvoit
	 * le premier resultat. Une seule requete.
	 * 
	 * @param id
	 *            L'id du computer à trouver.
	 * @return computerUi
	 */

	public ComputerUi findUi(long id) {
		ComputerUi computerUi = new ComputerUi();
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
				computerUi.setId(id);
				computerUi.setName(rs.getString("c.name"));
				computerUi.setCompany(
						new Company.CompanyBuilder().id(rs.getLong("y.id")).name(rs.getString("y.name")).build());
				
				if (rs.getDate("c.introduced") != null)
					computerUi.setIntroduced(rs.getDate("c.introduced").toLocalDate());
				if (rs.getDate("c.discontinued") != null) {
					computerUi.setDiscontinued(rs.getDate("c.discontinued").toLocalDate());
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
		return computerUi;
	}

	/**
	 * Methode pour trouver un computer en fonction de son nom.
	 * 
	 * @param name
	 *            Le nom du computer à trouver.
	 * @return computer
	 */
	public Computer findByName(String name) {
		Computer computer = new Computer();
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
				computer.setId(rs.getInt("id"));
				computer.setName(rs.getString("name"));
				computer.setCompanyId(rs.getInt("company_id"));
				if (rs.getDate("introduced") != null)
					computer.setIntroduced(rs.getDate("introduced").toLocalDate());
				if (rs.getDate("discontinued") != null) {
					computer.setDiscontinued(rs.getDate("discontinued").toLocalDate());
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
		return computer;
	}

	/**
	 * Methode pour avoir une liste de tous les computer en base. Pas optimisée
	 * pour les grosses bdd.
	 * 
	 * @return computerList
	 */
	public List<Computer> findAll() {
		List<Computer> computerList = new ArrayList<Computer>();
		Computer computer;
		Connection conn = PersistenceManager.INSTANCE.connectToDb();
		Statement stmt = null;
		ResultSet rs = null;
		// ResultSet rs = PersistenceManager.INSTANCE.sendQuery(sql);

		try {
			String sql = "SELECT * FROM computer";
			stmt = conn.createStatement();
			logger.debug("Send : {}", sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				computer = new Computer();
				computer.setId(rs.getInt("id"));
				computer.setName(rs.getString("name"));
				computer.setCompanyId(rs.getInt("company_id"));
				if (rs.getDate("introduced") != null)
					computer.setIntroduced(rs.getDate("introduced").toLocalDate());
				if (rs.getDate("discontinued") != null) {
					computer.setDiscontinued(rs.getDate("discontinued").toLocalDate());
				}
				computerList.add(computer);
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
		return computerList;
	}

	/**
	 * Methode pour avoir une liste de company de taille maximale définie dans
	 * conf.properties. Selectionne les company en fonction de l'indexPage (page
	 * 0 à x).
	 * 
	 * @return companyList
	 */
	public List<Computer> findInRange(int indexPage) {
		List<Computer> computerList = new ArrayList<Computer>();
		if (indexPage < 0) {
			return computerList;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int maxPage = Integer.parseInt(prop.getProperty("pagination.maxpage"));
		try {
			conn = PersistenceManager.INSTANCE.connectToDb();
			preparedStatement = conn.prepareStatement("SELECT * FROM computer LIMIT ? OFFSET ?");
			preparedStatement.setInt(1, maxPage);
			preparedStatement.setInt(2, indexPage * maxPage);
			logger.debug("Send : {}", preparedStatement.toString());
			preparedStatement.execute();
			rs = preparedStatement.getResultSet();
			Computer computer;
			while (rs.next()) {
				computer = new Computer();
				computer.setId(rs.getInt("id"));
				computer.setName(rs.getString("name"));
				computer.setCompanyId(rs.getInt("company_id"));
				if (rs.getDate("introduced") != null)
					computer.setIntroduced(rs.getDate("introduced").toLocalDate());
				if (rs.getDate("discontinued") != null) {
					computer.setDiscontinued(rs.getDate("discontinued").toLocalDate());
				}
				computerList.add(computer);
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
		return computerList;
	}

	/**
	 * Methode pour trouver l'id d'un computer en fonction de son nom. renvoit
	 * le premier resultat.
	 * 
	 * @param name
	 *            Le nom du computer à trouver.
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
	 * Methode pour creer un nouveau computer en base, renvoit l'id de la ligne
	 * dans la bdd.
	 * 
	 * @param computer
	 *            Le computer à créer.
	 * @return id
	 */
	@Override
	public long create(Computer cmpt) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		try {
			conn = PersistenceManager.INSTANCE.connectToDb();
			preparedStatement = conn
					.prepareStatement("INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?)");

			preparedStatement.setString(1, cmpt.getName());
			if (cmpt.getIntroduced() == null)
				preparedStatement.setNull(2, Types.TIMESTAMP);
			else
				preparedStatement.setDate(2, java.sql.Date.valueOf(cmpt.getIntroduced()));
			if (cmpt.getDiscontinued() == null)
				preparedStatement.setNull(3, Types.TIMESTAMP);
			else
				preparedStatement.setDate(3, java.sql.Date.valueOf(cmpt.getDiscontinued()));
			if (cmpt.getCompanyid() == 0)
				preparedStatement.setNull(4, Types.INTEGER);
			else
				preparedStatement.setLong(4, cmpt.getCompanyid());
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
		return findIdByName(cmpt.getName());
	}

	/**
	 * Methode pour delete un computer en fonction de son id. renvoit le result
	 * de sendToExec.
	 * 
	 * @param computer
	 *            Le computer à delete.
	 * @return result
	 */
	@Override
	public boolean delete(Computer computer) {
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
	 * 
	 * @param id
	 *            L'id du computer à delete
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
	 * 
	 * @param computer
	 *            Le computer à update.
	 * @return result
	 */
	// Careful with the computer's id
	@Override
	public boolean update(Computer cmpt) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn = PersistenceManager.INSTANCE.connectToDb();
			preparedStatement = conn.prepareStatement(
					"UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?");

			preparedStatement.setString(1, cmpt.getName());
			if (cmpt.getIntroduced() == null)
				preparedStatement.setNull(2, Types.TIMESTAMP);
			else
				preparedStatement.setDate(2, java.sql.Date.valueOf(cmpt.getIntroduced()));
			if (cmpt.getDiscontinued() == null)
				preparedStatement.setNull(3, Types.TIMESTAMP);
			else
				preparedStatement.setDate(3, java.sql.Date.valueOf(cmpt.getDiscontinued()));
			if (cmpt.getCompanyid() == 0)
				preparedStatement.setNull(4, Types.INTEGER);
			else
				preparedStatement.setLong(4, cmpt.getCompanyid());
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

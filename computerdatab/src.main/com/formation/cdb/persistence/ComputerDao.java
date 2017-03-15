package com.formation.cdb.persistence;

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

import com.formation.cdb.model.Computer;

public enum ComputerDao implements Dao<Computer> {
	INSTANCE;
	final Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	// protected Connection connect = null;

	private ComputerDao() {
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
			logger.info("SendQuery : {}", sql);
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
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return computer;
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
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return computer;
	}

	/**
	 * Methode pour avoir une liste de tous les computer en base.
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
			logger.info("SendQuery : {}", sql);
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
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
			preparedStatement.execute();
			rs = preparedStatement.getResultSet();
			if (rs.first()) {
				id = rs.getLong("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			logger.info("Try to exec : " + preparedStatement.toString());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			preparedStatement.executeUpdate();

			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			preparedStatement.executeUpdate();

			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			logger.info("Try to exec : " + preparedStatement.toString());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
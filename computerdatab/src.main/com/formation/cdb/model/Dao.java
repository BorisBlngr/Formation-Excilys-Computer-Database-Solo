package com.formation.cdb.model;

import java.sql.Connection;

public abstract class Dao<T> {
	protected Connection connect = null;

	public Dao(Connection conn) {
		this.connect = conn;
	}

	/**
	 * Méthode de création.
	 * 
	 * @param obj
	 *            object à créer
	 * @return id
	 */
	public abstract int create(T obj);

	/**
	 * Méthode pour effacer.
	 * 
	 * @param obj
	 *            object à delete
	 * @return boolean
	 */
	public abstract boolean delete(T obj);

	/**
	 * Méthode de mise à jour
	 * 
	 * @param obj
	 *            object à update
	 * @return boolean
	 */
	public abstract boolean update(T obj);

	/**
	 * Méthode de recherche des informations
	 * 
	 * @param id
	 *            id de l'object que l'on veut trouver
	 * @return T
	 */
	public abstract T find(int id);
}
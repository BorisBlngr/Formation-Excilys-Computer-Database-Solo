package com.formation.cdb.model.dao;

public interface Dao<T> {

    /**
     * Méthode de création.
     * @param obj Object à créer.
     * @return id
     */
    long create(T obj);

    /**
     * Méthode pour effacer.
     * @param obj Object à delete.
     * @return boolean
     */
    boolean delete(T obj);

    /**
     * Méthode de mise à jour.
     * @param obj Object à update.
     * @return boolean
     */
    boolean update(T obj);

    /**
     * Méthode de recherche des informations.
     * @param id Id de l'object que l'on veut trouver.
     * @return T
     */
    T find(long id);
}
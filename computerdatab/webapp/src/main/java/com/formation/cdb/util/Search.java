package com.formation.cdb.util;

public enum Search {
    COMPUTERS("computers"), COMPANIES("companies");

    private final String name;

    /**
     * Constructeur.
     * @param s Name.
     */
    Search(String s) {
        name = s;
    }

    /**
     * Equals methode.
     * @param other Other name.
     * @return result
     */
    public boolean equalsName(String other) {
        // (otherName == null) check is not needed because name.equals(null)
        // returns false
        return name.equals(other);
    }

    /**
     * To String methode.
     * @return name
     */
    public String toString() {
        return this.name;
    }
}

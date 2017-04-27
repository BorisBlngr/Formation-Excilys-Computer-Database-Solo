package com.formation.cdb.ui;

public class Page {
    int index = 0;
    String active = "";

    /**
     * Constructor.
     * @param index Index.
     * @param active Active.
     */
    public Page(int index, boolean active) {
        this.index = index;
        if (active) {
            this.active = "active";
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getActive() {
        return active;
    }

    /**
     * Active setter.
     * @param active Active.
     */
    public void setActive(boolean active) {
        if (active) {
            this.active = "active";
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((active == null) ? 0 : active.hashCode());
        result = prime * result + index;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Page other = (Page) obj;
        if (active == null) {
            if (other.active != null) {
                return false;
            }
        } else if (!active.equals(other.active)) {
            return false;
        }
        if (index != other.index) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Page [index=" + index + ", active=" + active + "]";
    }

}

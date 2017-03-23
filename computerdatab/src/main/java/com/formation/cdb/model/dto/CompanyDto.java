package com.formation.cdb.model.dto;

public class CompanyDto {

    private long id = 0;
    private String name = null;

    /**
     * Constructeur.
     */
    public CompanyDto() {
    }

    /**
     * Constructeur avec Builder.
     * @param builder Builder à fournir.
     */
    public CompanyDto(CompanyDtoBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static class CompanyDtoBuilder {
        private String name;
        private long id;

        /**
         * Constructeur.
         */
        public CompanyDtoBuilder() {
        }

        /**
         * Set l'id.
         * @param id Id à définir.
         * @return this
         */
        public CompanyDtoBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Set le name.
         * @param name Name à définir.
         * @return this
         */
        public CompanyDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Finalise le builder.
         * @return company
         */
        public CompanyDto build() {
            return new CompanyDto(this);
        }

    }

    @Override
    public String toString() {
        return "CompanyDto [id=" + id + ", name=" + name + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        CompanyDto other = (CompanyDto) obj;
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * main.
     * @param args Arguments.
     */
    public static void main(String[] args) {
    }

}

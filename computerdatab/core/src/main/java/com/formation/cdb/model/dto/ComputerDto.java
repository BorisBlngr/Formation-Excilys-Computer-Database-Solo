package com.formation.cdb.model.dto;

import com.formation.cdb.model.Company;

public class ComputerDto {
    private long id = 0;
    private String name;
    private String introduced = null;
    private String discontinued = null;
    private Company company = new Company();

    /**
     * Constructeur.
     */
    public ComputerDto() {
    }

    /**
     * Constructeur complet.
     * @param id Id.
     * @param name Name.
     * @param introduced Introduction date.
     * @param company Company.
     * @param discontinued Discontinued date.
     */
    public ComputerDto(int id, String name, String introduced, Company company, String discontinued) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.company = company;
        this.discontinued = discontinued;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    @Override
    public String toString() {
        return "ComputerDto [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
                + discontinued + ", company=" + company + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
        ComputerDto other = (ComputerDto) obj;
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (discontinued == null) {
            if (other.discontinued != null) {
                return false;
            }
        } else if (!discontinued.equals(other.discontinued)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (introduced == null) {
            if (other.introduced != null) {
                return false;
            }
        } else if (!introduced.equals(other.introduced)) {
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
     * Computer builder.
     * @param builder Builder.
     */
    public ComputerDto(ComputerDtoBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    public static class ComputerDtoBuilder {
        String name;
        long id;
        String introduced = null;
        Company company = new Company();
        String discontinued = null;

        /**
         * Constructeur builder.
         */
        public ComputerDtoBuilder() {
        }

        /**
         * Set Id.
         * @param id Id.
         * @return this
         */
        public ComputerDtoBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Set name.
         * @param name Name.
         * @return this
         */
        public ComputerDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set introduced.
         * @param introduced Introduced date.
         * @return this
         */
        public ComputerDtoBuilder introduced(String introduced) {
            if (introduced == null) {
                this.introduced = "";
            } else {
                this.introduced = introduced;
            }
            return this;
        }

        /**
         * Set discontinued.
         * @param discontinued Discontinued date.
         * @return this
         */
        public ComputerDtoBuilder discontinued(String discontinued) {
            if (discontinued == null) {
                this.discontinued = "";
            } else {
                this.discontinued = discontinued;
            }
            return this;
        }

        /**
         * Set companyId.
         * @param company Company.
         * @return this
         */
        public ComputerDtoBuilder company(Company company) {
            this.company = company;
            return this;
        }

        /**
         * Build.
         * @return this.
         */
        public ComputerDto build() {
            return new ComputerDto(this);
        }

    }
}
package com.formation.cdb.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;
    @Column
    private String name;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDate introduced = null;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDate discontinued = null;
    @ManyToOne(optional = true)
    private Company company = new Company();

    /**
     * Constrcteur.
     */
    public Computer() {
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

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        Computer other = (Computer) obj;
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
     * Constrcteur Builder.
     * @param builder Builder.
     */
    public Computer(ComputerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    @Override
    public String toString() {
        return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
                + ", company=" + company + "]";
    }

    public static class ComputerBuilder {
        private String name;
        private long id;
        private LocalDate introduced = null;
        private LocalDate discontinued = null;
        private Company company = new Company();

        /**
         * Constrcteur.
         */
        public ComputerBuilder() {
        }

        /**
         * Set id.
         * @param id Id.
         * @return this
         */
        public ComputerBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Set name.
         * @param name Name.
         * @return this
         */
        public ComputerBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set introduced.
         * @param introduced Introduced.
         * @return this
         */
        public ComputerBuilder introduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         * Set discontinued.
         * @param discontinued Discontinued.
         * @return this
         */
        public ComputerBuilder discontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         * Set company.
         * @param company Company.
         * @return this
         */
        public ComputerBuilder company(Company company) {
            this.company = company;
            return this;
        }

        /**
         * Build.
         * @return this
         */
        public Computer build() {
            return new Computer(this);
        }
    }

    /**
     * Set company.
     * @param args Args.
     */
    public static void main(String[] args) {
    }
}

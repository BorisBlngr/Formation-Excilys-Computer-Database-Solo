package com.formation.cdb.model;

import java.time.LocalDate;

import com.formation.cdb.mapper.ComputerMapper;
import com.formation.cdb.model.dao.ComputerDao;
import com.formation.cdb.model.dto.ComputerDto;

public class Computer {

    private long id = 0;
    private String name;
    private LocalDate introduced = null;
    private LocalDate discontinued = null;
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
    public Computer(ComputerUiBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    @Override
    public String toString() {
        return "ComputerUi [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
                + discontinued + ", company=" + company + "]";
    }

    public static class ComputerUiBuilder {
        String name;
        long id;
        LocalDate introduced = null;
        LocalDate discontinued = null;
        Company company = new Company();

        /**
         * Constrcteur.
         */
        public ComputerUiBuilder() {
        }

        /**
         * Set id.
         * @param id Id.
         * @return this
         */
        public ComputerUiBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Set name.
         * @param name Name.
         * @return this
         */
        public ComputerUiBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set introduced.
         * @param introduced Introduced.
         * @return this
         */
        public ComputerUiBuilder introduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         * Set discontinued.
         * @param discontinued Discontinued.
         * @return this
         */
        public ComputerUiBuilder discontinued(LocalDate discontinued) {
            this.introduced = discontinued;
            return this;
        }

        /**
         * Set company.
         * @param company Company.
         * @return this
         */
        public ComputerUiBuilder company(Company company) {
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

        ComputerDto computerDto = ComputerDao.INSTANCE.find(2);
        System.out.println(computerDto);
        Computer computer = ComputerMapper.INSTANCE.map(computerDto);
        System.out.println(computer);

    }
}
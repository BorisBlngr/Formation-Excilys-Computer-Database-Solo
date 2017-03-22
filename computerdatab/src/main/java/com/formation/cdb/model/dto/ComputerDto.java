package com.formation.cdb.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.dao.CompanyDao;
import com.formation.cdb.model.dao.ComputerDao;

public class ComputerDto {
    private long id = 0;
    private String name;
    private LocalDate introduced = null;
    private LocalDate discontinued = null;
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
    public ComputerDto(int id, String name, LocalDate introduced, Company company, LocalDate discontinued) {
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

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    @Override
    public String toString() {
        return "ComputerDto [id=" + id + ", name=" + name + ", introduced=" + introduced + ", company=" + company
                + ", discontinued=" + discontinued + "]";
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
        LocalDate introduced = null;
        Company company = new Company();
        LocalDate discontinued = null;

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
        public ComputerDtoBuilder introduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         * Set discontinued.
         * @param discontinued Discontinued date.
         * @return this
         */
        public ComputerDtoBuilder discontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
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

    /**
     * Main.
     * @param args Args.
     */
    public static void main(String[] args) {

        ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder().company(CompanyDao.INSTANCE.find(2))
                .name("Orditropbien").introduced(LocalDate.of(1980, 10, 10)).discontinued(LocalDate.of(1990, 10, 10))
                .build();
        System.out.println(computerDto.toString());
        System.out.println(java.sql.Date.valueOf(computerDto.getIntroduced()));
        ComputerDto computerDto2 = ComputerDao.INSTANCE.find(600);
        System.out.println(computerDto2.toString());

        // test create
        computerDto.setId(ComputerDao.INSTANCE.create(computerDto));
        computerDto2 = ComputerDao.INSTANCE.findByName(computerDto.getName());
        System.out.println("Try to create : " + computerDto.toString());
        System.out.println("Try to find " + computerDto.getName() + " : " + computerDto2);

        // try to update
        computerDto.setCompany(CompanyDao.INSTANCE.find(8));
        ComputerDao.INSTANCE.update(computerDto);
        System.out.println("Try to update : \n" + computerDto2 + "\n to \n" + computerDto);
        System.out.println("Find : " + ComputerDao.INSTANCE.findByName(computerDto.getName()));

        // try to delete

        ComputerDao.INSTANCE.delete(computerDto);
        System.out.println(
                "Try to delete " + computerDto + " : " + ComputerDao.INSTANCE.findByName(computerDto.getName()));

        // try find all
        List<ComputerDto> computerDtoList = ComputerDao.INSTANCE.findAll();
        System.out.println(computerDtoList.size());

    }
}
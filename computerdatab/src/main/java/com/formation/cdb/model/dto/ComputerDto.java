package com.formation.cdb.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.formation.cdb.model.dao.ComputerDao;

public class ComputerDto {
    private long id = 0;
    private String name;
    private LocalDate introduced = null;
    private long companyId = 0;
    private LocalDate discontinued = null;

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
     * @param companyId Company id.
     * @param discontinued Discontinued date.
     */
    public ComputerDto(int id, String name, LocalDate introduced, int companyId, LocalDate discontinued) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.companyId = companyId;
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

    public long getCompanyid() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    @Override
    public String toString() {
        return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", companyId=" + companyId
                + ", discontinued=" + discontinued + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (companyId ^ (companyId >>> 32));
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
        if (companyId != other.companyId) {
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
    public ComputerDto(ComputerBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.companyId = builder.companyId;
    }

    public static class ComputerBuilder {
        String name;
        long id;
        LocalDate introduced = null;
        long companyId = 0;
        LocalDate discontinued = null;

        /**
         * Constructeur builder.
         */
        public ComputerBuilder() {
        }

        /**
         * Set Id.
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
         * @param introduced Introduced date.
         * @return this
         */
        public ComputerBuilder introduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         * Set discontinued.
         * @param discontinued Discontinued date.
         * @return this
         */
        public ComputerBuilder discontinued(LocalDate discontinued) {
            this.introduced = discontinued;
            return this;
        }
        /**
         * Set companyId.
         * @param companyId Company id.
         * @return this
         */
        public ComputerBuilder companyId(long companyId) {
            this.companyId = companyId;
            return this;
        }
        /**
         * Build.
         * @return this.
         * */
        public ComputerDto build() {
            return new ComputerDto(this);
        }

    }
    /**
     * Main.
     * @param args Args.
     * */
    public static void main(String[] args) {

        ComputerDto computerDto = new ComputerDto.ComputerBuilder().companyId(2).name("Orditropbien")
                .introduced(LocalDate.of(1980, 10, 10)).discontinued(LocalDate.of(1990, 10, 10)).build();
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
        computerDto.setCompanyId(8);
        ComputerDao.INSTANCE.update(computerDto);
        System.out.println("Try to update : \n" + computerDto2 + "\n to \n" + computerDto);
        System.out.println("Find : " + ComputerDao.INSTANCE.findByName(computerDto.getName()));

        // try to delete

        ComputerDao.INSTANCE.delete(computerDto);
        System.out.println("Try to delete " + computerDto + " : " + ComputerDao.INSTANCE.findByName(computerDto.getName()));

        // try find all
        List<ComputerDto> computerDtoList = ComputerDao.INSTANCE.findAll();
        System.out.println(computerDtoList.size());

    }
}
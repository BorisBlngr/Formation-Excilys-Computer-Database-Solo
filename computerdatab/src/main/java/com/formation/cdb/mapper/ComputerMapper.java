package com.formation.cdb.mapper;

import com.formation.cdb.model.Computer;
import com.formation.cdb.persistence.CompanyDao;
import com.formation.cdb.ui.ComputerUi;

/**
 * Computer => ComputerUi ComputerUi => Computer
 */
public enum ComputerMapper {
	INSTANCE;

	public Computer map(ComputerUi computerUi) {
		Computer computer = new Computer.ComputerBuilder()
				.id(computerUi.getId())
				.name(computerUi.getName())
				.companyId(computerUi.getCompany().getId())
				.discontinued(computerUi.getDiscontinued())
				.introduced(computerUi.getIntroduced())
				.build();
		return computer;
	}

	public ComputerUi map(Computer computer) {
		ComputerUi computerUi = new ComputerUi.ComputerUiBuilder()
				.id(computer.getId())
				.name(computer.getName())
				.company(CompanyDao.INSTANCE.find(computer.getCompanyid()))
				.introduced(computer.getIntroduced())
				.discontinued(computer.getDiscontinued())
				.build();
		return computerUi;
	}

}

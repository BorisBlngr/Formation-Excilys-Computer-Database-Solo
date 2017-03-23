package com.formation.cdb.mapper;

import com.formation.cdb.model.Computer;
import com.formation.cdb.model.dto.ComputerDto;

/**
 * Computer => ComputerUi ComputerUi => Computer.
 */
public enum ComputerMapper {
    INSTANCE;

    /**
     * Transforme un Computer en ComputerDto.
     * @param computer Computer.
     * @return computerDto
     */
    public ComputerDto toDto(Computer computer) {
        ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder().id(computer.getId()).name(computer.getName())
                .company(computer.getCompany()).discontinued(computer.getDiscontinued())
                .introduced(computer.getIntroduced()).build();
        return computerDto;
    }

    /**
     * Transforme un ComputerDto en Computer.
     * @param computerDto ComputerDto.
     * @return Computer
     */
    public Computer toEntity(ComputerDto computerDto) {
        Computer computer = new Computer.ComputerBuilder().id(computerDto.getId()).name(computerDto.getName())
                .company(computerDto.getCompany()).introduced(computerDto.getIntroduced())
                .discontinued(computerDto.getDiscontinued()).build();
        return computer;
    }

}

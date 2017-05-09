package com.formation.cdb.mapper;

import java.time.LocalDate;
import java.util.Arrays;

import com.formation.cdb.model.Computer;
import com.formation.cdb.model.dto.ComputerDto;

/**
 * ComputerDto => Computer Computer => ComputerDto.
 */
public class ComputerMapper {

    private static final String REGEX = "-";

    /**
     * Transforme un Computer en ComputerDto.
     * @param computer Computer.
     * @return computerDto
     */
    public static ComputerDto toDto(Computer computer) {
        String discontinuedStr = "";
        String introducedStr = "";
        if (computer.getIntroduced() != null) {
            introducedStr = computer.getIntroduced().toString();
        }
        if (computer.getDiscontinued() != null) {
            discontinuedStr = computer.getDiscontinued().toString();
        }

        ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder().id(computer.getId()).name(computer.getName())
                .company(computer.getCompany()).discontinued(discontinuedStr).introduced(introducedStr).build();
        return computerDto;
    }

    /**
     * Transforme un ComputerDto en Computer.
     * @param computerDto ComputerDto.
     * @return Computer
     */
    public static Computer toEntity(ComputerDto computerDto) {
        LocalDate introducedd = null;
        LocalDate discontinuedd = null;
        if (computerDto.getIntroduced() != null && !computerDto.getIntroduced().isEmpty()) {
            int[] introduced = Arrays.stream(computerDto.getIntroduced().split(REGEX)).mapToInt(Integer::parseInt)
                    .toArray();
            introducedd = LocalDate.of(introduced[0], introduced[1], introduced[2]);
        }

        if (computerDto.getIntroduced() != null && !computerDto.getIntroduced().isEmpty()) {
            int[] discontinued = Arrays.stream(computerDto.getDiscontinued().split(REGEX)).mapToInt(Integer::parseInt)
                    .toArray();
            discontinuedd = LocalDate.of(discontinued[0], discontinued[1], discontinued[2]);
        }

        Computer computer = new Computer.ComputerBuilder().id(computerDto.getId()).name(computerDto.getName())
                .company(computerDto.getCompany()).introduced(introducedd).discontinued(discontinuedd).build();
        return computer;
    }
}

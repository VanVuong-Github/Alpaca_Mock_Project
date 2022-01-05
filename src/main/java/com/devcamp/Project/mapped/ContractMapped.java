package com.devcamp.Project.mapped;

import com.devcamp.Project.dto.ContractDTO;
import com.devcamp.Project.entity.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContractMapped {

    ContractMapped INSTANCE = Mappers.getMapper(ContractMapped.class);

    ContractDTO contractToContractDTO(Contract contract);
}

package com.devcamp.Project.mapped;

import com.devcamp.Project.dto.CCustomerDTO;
import com.devcamp.Project.entity.CCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ICustomerMapped {

    ICustomerMapped INSTANCE = Mappers.getMapper(ICustomerMapped.class);

    CCustomerDTO customerToCustomerDTO(CCustomer customer);
}

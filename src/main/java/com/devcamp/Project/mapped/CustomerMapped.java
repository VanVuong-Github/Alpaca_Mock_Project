package com.devcamp.Project.mapped;

import com.devcamp.Project.dto.CustomerDTO;
import com.devcamp.Project.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapped {

    CustomerMapped INSTANCE = Mappers.getMapper(CustomerMapped.class);

    CustomerDTO customerToCustomerDTO(Customer customer);
}

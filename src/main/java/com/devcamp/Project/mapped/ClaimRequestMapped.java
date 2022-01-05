package com.devcamp.Project.mapped;

import com.devcamp.Project.dto.ClaimRequestDTO;
import com.devcamp.Project.entity.ClaimRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel =  "spring")
public interface ClaimRequestMapped {

    ClaimRequestMapped INSTANCE = Mappers.getMapper(ClaimRequestMapped.class);

    ClaimRequestDTO claimRequestToClaimRequestDTO(ClaimRequest claimRequest);

}

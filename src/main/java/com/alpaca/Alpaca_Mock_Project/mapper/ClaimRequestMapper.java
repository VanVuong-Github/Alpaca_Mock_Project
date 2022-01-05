package com.alpaca.Alpaca_Mock_Project.mapper;

import com.alpaca.Alpaca_Mock_Project.dto.ClaimRequestDto;
import com.alpaca.Alpaca_Mock_Project.entity.ClaimRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClaimRequestMapper {

    ClaimRequestMapper INSTANCE = Mappers.getMapper(ClaimRequestMapper.class);

    ClaimRequest claimRequestDtoToClaimRequest(ClaimRequestDto claimRequestDto);

    ClaimRequestDto claimRequestToClaimRequestDto(ClaimRequest claimRequest);
}

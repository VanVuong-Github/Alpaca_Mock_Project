package com.alpaca.Alpaca_Mock_Project.service.impl;

import com.alpaca.Alpaca_Mock_Project.dto.ClaimRequestDto;
import com.alpaca.Alpaca_Mock_Project.entity.ClaimRequest;
import com.alpaca.Alpaca_Mock_Project.mapper.ClaimRequestMapper;
import com.alpaca.Alpaca_Mock_Project.repository.ClaimRequestRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ClaimRequestServiceImpl {

    private static final Logger logger = Logger.getLogger(ClaimRequestServiceImpl.class.getName());

    private final ClaimRequestRepository claimRequestRepository;

    private final ClaimRequestMapper claimRequestMapper;

    public ClaimRequestServiceImpl(ClaimRequestRepository claimRequestRepository, ClaimRequestMapper claimRequestMapper) {
        this.claimRequestRepository = claimRequestRepository;
        this.claimRequestMapper = claimRequestMapper;
    }

    @Transactional
    public ClaimRequestDto getClaimRequestByCustomerNameAndCardId(final String customerName, final String cardId){
        logger.log(Level.INFO, "Getting Claim Request CustomerName and Customer Card ID");
        return ClaimRequestMapper.INSTANCE.claimRequestToClaimRequestDto(claimRequestRepository.findByCustomerNameContainingIgnoreCaseAndCardIdContainingIgnoreCaseOrderByCustomerNameAsc(customerName, cardId)
                .orElse(null));
    }

    @Transactional
    public List<ClaimRequestDto> findAll(){
        logger.log(Level.INFO, "Finding all Claim Request");
        return claimRequestRepository.findAll().stream().map(claimRequestMapper::claimRequestToClaimRequestDto).collect(Collectors.toList());
    }

    @Transactional
    public ClaimRequestDto getClaimRequestById(final Long id){
        logger.log(Level.INFO, "Getting Claim Request by ID");
        return ClaimRequestMapper.INSTANCE.claimRequestToClaimRequestDto(claimRequestRepository.findById(id).orElse(null));
    }

    @Transactional
    public void createClaimRequest(final ClaimRequestDto claimRequestDto){
        logger.log(Level.INFO, "Creating new Claim Request...");
        claimRequestRepository.save(ClaimRequestMapper.INSTANCE.claimRequestDtoToClaimRequest(claimRequestDto));
    }

    @Transactional
    public void updateClaimRequest(final ClaimRequestDto claimRequestDto,
                                           final Long id){
        logger.log(Level.INFO, "Updating all Claim Request");
        ClaimRequest oldClaimRequest = claimRequestRepository.findById(id).orElseThrow(NullPointerException::new); //this is the request before change
        oldClaimRequest.setCustomerName(claimRequestDto.getCustomerName());
        oldClaimRequest.setCardId(claimRequestDto.getCardId());
        oldClaimRequest.setUrls(claimRequestDto.getUrls());
        claimRequestRepository.saveAndFlush(oldClaimRequest);
    }

    @Transactional
    public void deleteClaimRequest(final Long id){
        logger.log(Level.INFO, "Deleting all Claim Request");
        ClaimRequest claimRequestToDelete = claimRequestRepository.findById(id).orElseThrow(NullPointerException::new);
        claimRequestRepository.delete(claimRequestToDelete);
    }

}

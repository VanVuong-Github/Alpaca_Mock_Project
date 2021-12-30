package com.alpaca.Alpaca_Mock_Project.service;

import com.alpaca.Alpaca_Mock_Project.dto.ClaimRequestDto;
import com.alpaca.Alpaca_Mock_Project.dto.ClaimRequestResponse;
import com.alpaca.Alpaca_Mock_Project.dto.FileResponse;
import com.alpaca.Alpaca_Mock_Project.entity.ClaimRequest;
import com.alpaca.Alpaca_Mock_Project.entity.FileEntity;
import com.alpaca.Alpaca_Mock_Project.repository.ClaimRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimRequestService {

    @Autowired
    private ClaimRequestRepository claimRequestRepository;

    @Autowired
    private FileService fileService;

    @Transactional
    public ClaimRequestResponse getClaimRequestByCustomerNameAndCardId(final String customerName, final String cardId){
        return mapToClaimRequestResponse(claimRequestRepository.findByCustomerNameContainingIgnoreCaseAndCardIdContainingIgnoreCaseOrderByCustomerNameAsc(customerName, cardId).orElse(null));
    }

    @Transactional
    public List<ClaimRequestResponse> findAll(){
        return claimRequestRepository.findAll().stream().map(this::mapToClaimRequestResponse).collect(Collectors.toList());
    }

    @Transactional
    public ClaimRequestResponse getClaimRequestById(final Long id){
        return mapToClaimRequestResponse(claimRequestRepository.findById(id).orElse(null));
    }

    @Transactional
    public void createClaimRequest(final ClaimRequestDto claimRequestDto) throws IOException {
        // create request
        ClaimRequest claimRequest = ClaimRequest.builder()
                .customerName(claimRequestDto.getCustomerName())
                .cardId(claimRequestDto.getCardId())
                .build();
        try {
            //save file with claim request id
            List<FileEntity> photos = fileService.saveAll(claimRequestDto.getPhotos()
                    , claimRequestRepository.save(claimRequest).getId());
        } catch (Exception e){
            throw new IOException("Create Claim Request Failed: " + e.getMessage());
        }
    }

    @Transactional
    public void updateClaimRequest(final ClaimRequestDto claimRequestDto,
                                           final Long id) throws IOException {
        ClaimRequest oldClaimRequest = claimRequestRepository.findById(id).orElse(null); //this is the request before change
        oldClaimRequest.setCustomerName(claimRequestDto.getCustomerName());
        oldClaimRequest.setCardId(claimRequestDto.getCardId());
        claimRequestRepository.save(oldClaimRequest);
        // delete all old photos
        fileService.deleteAllByRequestId(oldClaimRequest.getId());
        // then save the new ones
        List<FileEntity> photos = fileService.saveAll(claimRequestDto.getPhotos(), oldClaimRequest.getId());
    }

    @Transactional
    public void deleteClaimRequest(final Long id){
        claimRequestRepository.deleteById(id);
        fileService.deleteAllByRequestId(id);
    }

    /**
     * map a ClaimRequest to ClaimRequestResponse
     * @param claimRequest
     * @return claimRequestResponse
     */
    private ClaimRequestResponse mapToClaimRequestResponse(ClaimRequest claimRequest){
        List<FileResponse> photos = fileService.findAllByRequestId(claimRequest.getId());
        ClaimRequestResponse claimRequestResponse = ClaimRequestResponse.builder()
                .customerName(claimRequest.getCustomerName())
                .cardId(claimRequest.getCardId())
                .photos(photos)
                .build();
        return claimRequestResponse;
    }
}

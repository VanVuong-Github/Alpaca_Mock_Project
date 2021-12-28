package com.alpaca.Alpaca_Mock_Project.service;

import com.alpaca.Alpaca_Mock_Project.entity.ClaimRequest;
import com.alpaca.Alpaca_Mock_Project.repository.ClaimRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimRequestService {

    @Autowired
    private ClaimRequestRepository claimRequestRepository;

    public List<ClaimRequest> findAll(){
        return claimRequestRepository.findAll();
    }

    public ClaimRequest getClaimRequestById(final Long id){
        return claimRequestRepository.findById(id).orElse(null);
    }

    public ClaimRequest createClaimRequest(final ClaimRequest claimRequest){
        return claimRequestRepository.save(claimRequest);
    }

    public ClaimRequest updateClaimRequest(final ClaimRequest claimRequest,
                                           final Long id){
        ClaimRequest oldClaimRequest = claimRequestRepository.findById(id).orElse(null); //this is the request before change
        oldClaimRequest.setCustomerName(claimRequest.getCustomerName());
        oldClaimRequest.setCardId(claimRequest.getCardId());
        oldClaimRequest.setPhotos(claimRequest.getPhotos());
        return claimRequestRepository.save(oldClaimRequest);
    }

    public void deleteClaimRequest(final Long id){
        claimRequestRepository.deleteById(id);
    }
}

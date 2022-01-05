package com.devcamp.Project.service;

import com.devcamp.Project.dto.ClaimRequestDTO;
import com.devcamp.Project.entity.ClaimRequest;
import com.devcamp.Project.mapped.ClaimRequestMapped;
import com.devcamp.Project.repository.ClaimRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClaimRequestService {
    @Autowired
    ClaimRequestRepository claimRequestRepository;

    @Autowired
    FileService fileService;

    @Autowired
    ClaimRequestMapped claimRequestMapped;

    // lấy tất cả thông tin claimRequest
    @Transactional
    public List<ClaimRequestDTO> getAll(){

        return claimRequestRepository.findAll().stream()
                .map(claimRequestMapped::claimRequestToClaimRequestDTO).collect(Collectors.toList());
    }

    // lấy tất cả thông tin claimRequest theo id
    @Transactional
    public ClaimRequestDTO getById(Long id){

        //return claimRequestRepository.findById(id).orElse(null);
        return ClaimRequestMapped.INSTANCE.claimRequestToClaimRequestDTO(claimRequestRepository.findById(id).orElse(null));
    }

    // tạo mới claimRequest
    @Transactional
    public ClaimRequestDTO createClaimRequest(ClaimRequest cClaimRequest) {
        //return claimRequestRepository.saveAndFlush(cClaimRequest);
        return ClaimRequestMapped.INSTANCE.claimRequestToClaimRequestDTO(claimRequestRepository.saveAndFlush(cClaimRequest));
    }

    // cập nhật thông tin claimRequest
    @Transactional
    public ClaimRequestDTO updateClaimRequest(ClaimRequest inputClaimRequest, Long id)  {
        ClaimRequest claimRequest = claimRequestRepository.findById(id).orElse(null); //this is the request before change
        claimRequest.setCustomerName(inputClaimRequest.getCustomerName());
        claimRequest.setCustomerCardId(inputClaimRequest.getCustomerCardId());
        return  ClaimRequestMapped.INSTANCE.claimRequestToClaimRequestDTO(claimRequestRepository.saveAndFlush(claimRequest));
    }

    // xóa thông tin claimRequest và file kèm theo
    @Transactional
    public ResponseEntity<?> deleteClaimRequest(Long id){
        Optional<ClaimRequest> claimRequest = claimRequestRepository.findById(id);
        if ( claimRequest.isPresent()){
            claimRequestRepository.deleteById(id);
            return ResponseEntity.ok("Deleted Claim Request Success");
        } else {
            return ResponseEntity.ok("Deleted Claim Request Fail!");
        }
    }
}

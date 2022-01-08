package com.devcamp.Project.service;

import com.devcamp.Project.dto.ClaimRequestDTO;
import com.devcamp.Project.entity.ClaimRequest;
import com.devcamp.Project.mapped.ClaimRequestMapped;
import com.devcamp.Project.repository.ClaimRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClaimRequestService {
    private static Logger logger = LoggerFactory.getLogger(ClaimRequestService.class);

    @Autowired
    ClaimRequestRepository claimRequestRepository;

    @Autowired
    FileService fileService;

    @Autowired
    ClaimRequestMapped claimRequestMapped;

    // lấy tất cả thông tin claimRequest
    @Transactional
    public List<ClaimRequestDTO> getAll(){
        logger.info(String.format("List Claim Request Had Found!!"));
        return claimRequestRepository.findAll().stream()
                .map(claimRequestMapped::claimRequestToClaimRequestDTO).collect(Collectors.toList());
    }

    // lấy tất cả thông tin claimRequest theo id
    @Transactional
    public ResponseEntity<?> getById(Long id){
        Optional<ClaimRequest> oldClaimRequest = claimRequestRepository.findById(id);
        if (oldClaimRequest.isPresent()){
            logger.info(String.format("Claim Request with ID %s had found!",id));
            return new ResponseEntity<>(ClaimRequestMapped.INSTANCE.claimRequestToClaimRequestDTO(claimRequestRepository.findById(id).orElse(null)), HttpStatus.OK);
        } else {
            logger.info(String.format("Claim Request do not exist!"));
            return ResponseEntity.badRequest().body("Claim Request do not exist!!");
        }
    }

    // tạo mới claimRequest
    @Transactional
    public ClaimRequestDTO createClaimRequest(ClaimRequest cClaimRequest) {
        logger.info(String.format("Claim Request had saved!!"));
        return ClaimRequestMapped.INSTANCE.claimRequestToClaimRequestDTO(claimRequestRepository.saveAndFlush(cClaimRequest));
    }

    // cập nhật thông tin claimRequest
    @Transactional
    public ResponseEntity<?> updateClaimRequest(ClaimRequest inputClaimRequest, Long id)  {
        Optional<ClaimRequest> oldClaimRequest = claimRequestRepository.findById(id);
        if (oldClaimRequest.isPresent()){
            ClaimRequest claimRequest = claimRequestRepository.findById(id).orElse(null); //this is the request before change
            claimRequest.setCustomerName(inputClaimRequest.getCustomerName());
            claimRequest.setCustomerCardId(inputClaimRequest.getCustomerCardId());
            logger.info(String.format("Claim Request with ID %s updated", id));
            return new ResponseEntity<>(ClaimRequestMapped.INSTANCE.claimRequestToClaimRequestDTO(claimRequestRepository.saveAndFlush(claimRequest)), HttpStatus.OK);
        } else {
            logger.info(String.format("Claim Request do not exist!"));
            return ResponseEntity.badRequest().body("Claim Request do not exist!!");
        }

    }

    // xóa thông tin claimRequest và file kèm theo
    @Transactional
    public ResponseEntity<?> deleteClaimRequest(Long id){
        Optional<ClaimRequest> claimRequest = claimRequestRepository.findById(id);
        if ( claimRequest.isPresent()){
            claimRequestRepository.deleteById(id);
            logger.info(String.format("Claim Request with ID %s deleted", id));
            return ResponseEntity.ok("Deleted Claim Request Success!");
        } else {
            logger.info(String.format("Claim Request do not exist!"));
            return ResponseEntity.badRequest().body("Claim Request do not exist!!");
        }
    }
}

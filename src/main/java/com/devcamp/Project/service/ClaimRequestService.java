package com.devcamp.Project.service;

import com.devcamp.Project.dto.ClaimRequestDTO;
import com.devcamp.Project.entity.ClaimRequest;
import com.devcamp.Project.mapped.ClaimRequestMapped;
import com.devcamp.Project.repository.ClaimRequestRepository;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
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

    private final RedissonClient redissonClient;
    public ClaimRequestService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
    private RBucket<Object> quantity(){
        return redissonClient.getBucket("quantityClaimRequest");
    }

    public Object getQuantity(){
        // set quantity
        //quantity().set(0);
        return quantity().get();
    }

    // lấy tất cả thông tin claimRequest
    @Transactional
    public List<ClaimRequestDTO> getAll(){
        logger.info("List Claim Request Had Found!!");
        return claimRequestRepository.findAll().stream()
                .map(claimRequestMapped::claimRequestToClaimRequestDTO).collect(Collectors.toList());
    }

    // lấy tất cả thông tin claimRequest theo id
    @Transactional
    public Object getById(Long id){
        Optional<ClaimRequest> oldClaimRequest = claimRequestRepository.findById(id);
        if (oldClaimRequest.isPresent()){
            logger.info("Claim Request with ID {} had found!",id);
            return ClaimRequestMapped.INSTANCE.claimRequestToClaimRequestDTO(claimRequestRepository.findById(id).orElse(null));
        } else {
            logger.error("Claim Request do not exist!");
            return "Claim Request do not exist!!";
        }
    }

    @Transactional
    public  Object getClaimRequestByNumClaimRequest(String numClaim){
        ClaimRequest claimRequest = claimRequestRepository.findByNumClaimRequest(numClaim);
        if (claimRequest.getNumClaimRequest().isEmpty()){
            logger.error("Claim Request do not exist!");
            return "Claim Request do not exist!";
        } else {
            logger.info("Claim Request Had Found!!");
            return claimRequest;
        }
    }
    // tạo mới claimRequest
    @Transactional
    public ClaimRequestDTO createClaimRequest(ClaimRequest cClaimRequest) {
        int quantityContract = (int) quantity().get();
        quantity().set(++quantityContract);

        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

        cClaimRequest.setNumClaimRequest("YCBT_"+ year + "_" + String.format("%06d", quantity().get()));

        logger.info("Claim Request had saved!!");
        return ClaimRequestMapped.INSTANCE.claimRequestToClaimRequestDTO(claimRequestRepository.saveAndFlush(cClaimRequest));
    }

    // cập nhật thông tin claimRequest
    @Transactional
    public Object updateClaimRequest(ClaimRequest inputClaimRequest, Long id)  {
        Optional<ClaimRequest> oldClaimRequest = claimRequestRepository.findById(id);
        if (oldClaimRequest.isPresent()){
            ClaimRequest claimRequest = claimRequestRepository.findById(id).orElse(null); //this is the request before change
            claimRequest.setCustomerName(inputClaimRequest.getCustomerName());
            claimRequest.setCustomerCardId(inputClaimRequest.getCustomerCardId());
            logger.info("Claim Request with ID {} updated", id);
            return ClaimRequestMapped.INSTANCE.claimRequestToClaimRequestDTO(claimRequestRepository.saveAndFlush(claimRequest));
        } else {
            logger.error("Claim Request do not exist!");
            return "Claim Request do not exist!!";
        }

    }

    // xóa thông tin claimRequest và file kèm theo
    @Transactional
    public Object deleteClaimRequest(Long id){
        Optional<ClaimRequest> claimRequest = claimRequestRepository.findById(id);
        if ( claimRequest.isPresent()){
            claimRequestRepository.deleteById(id);
            logger.info("Claim Request with ID %s deleted", id);
            return "Deleted Claim Request Success!";
        } else {
            logger.error("Claim Request do not exist!");
            return "Claim Request do not exist!!";
        }
    }
}

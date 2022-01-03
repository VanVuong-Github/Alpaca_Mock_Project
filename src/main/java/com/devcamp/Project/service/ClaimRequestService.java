package com.devcamp.Project.service;

import com.devcamp.Project.entity.ClaimRequest;
import com.devcamp.Project.entity.File;
import com.devcamp.Project.repository.ClaimRequestRepository;
import com.devcamp.Project.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class ClaimRequestService {
    @Autowired
    ClaimRequestRepository claimRequestRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileService fileService;

    // lấy tất cả thông tin claimRequest
    public List<ClaimRequest> getAll(){
        return claimRequestRepository.findAll();
    }

    // lấy tất cả thông tin claimRequest theo id
    public ClaimRequest getById(Long id){
        return claimRequestRepository.findById(id).orElse(null);
    }

    // tạo mới claimRequest
    public ClaimRequest createClaimRequest(ClaimRequest cClaimRequest) throws IOException {

        ClaimRequest claimRequest = ClaimRequest.builder()
                .customerName(cClaimRequest.getCustomerName())
                .customerCardId(cClaimRequest.getCustomerCardId())
                .build();

        List<File> files = fileService.storeFile(cClaimRequest.getFiles(), claimRequestRepository.save(claimRequest).getId());

        return claimRequest;
    }

    // cập nhật thông tin claimRequest
    @Transactional
    public ClaimRequest updateClaimRequest(ClaimRequest inputClaimRequest, Long id) throws IOException {
        ClaimRequest claimRequest = claimRequestRepository.findById(id).orElse(null); //this is the request before change
        claimRequest.setCustomerName(inputClaimRequest.getCustomerName());
        claimRequest.setCustomerCardId(inputClaimRequest.getCustomerCardId());

        if ( inputClaimRequest.getFiles() != null){
            fileService.deleteAllFileByClaimRequestId(id);
            List<File> files = fileService.storeFile(inputClaimRequest.getFiles(), id);
        }

        return  claimRequestRepository.save(claimRequest);
    }

    // xóa thông tin claimRequest và file kèm theo
    public void deleteClaimRequest(Long id){
        claimRequestRepository.deleteById(id);
        fileService.deleteAllFileByClaimRequestId(id);

    }
}

package com.devcamp.Project.service;

import com.devcamp.Project.entity.CClaimRequest;
import com.devcamp.Project.entity.CFile;
import com.devcamp.Project.repository.IClaimRequestRepository;
import com.devcamp.Project.repository.IFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class CClaimRequestService {
    @Autowired
    IClaimRequestRepository iClaimRequestRepository;

    @Autowired
    IFileRepository iFileRepository;

    @Autowired
    CFileService cFileService;

    public List<CClaimRequest> getAll(){
        return iClaimRequestRepository.findAll();
    }

    public CClaimRequest getById(Long id){
        return iClaimRequestRepository.findById(id).orElse(null);
    }

    public CClaimRequest  createClaimRequest(CClaimRequest cClaimRequest) throws IOException {

        CClaimRequest claimRequest = CClaimRequest.builder()
                .customerName(cClaimRequest.getCustomerName())
                .customerCardId(cClaimRequest.getCustomerCardId())
                .build();

        List<CFile> files = cFileService.storeFile(cClaimRequest.getFiles(), iClaimRequestRepository.save(claimRequest).getId());

        return claimRequest;
    }

    @Transactional
    public CClaimRequest updateClaimRequest(CClaimRequest inputClaimRequest, Long id) throws IOException {
        CClaimRequest claimRequest = iClaimRequestRepository.findById(id).orElse(null); //this is the request before change
        claimRequest.setCustomerName(inputClaimRequest.getCustomerName());
        claimRequest.setCustomerCardId(inputClaimRequest.getCustomerCardId());

        if ( inputClaimRequest.getFiles() != null){
            cFileService.deleteAllFileByClaimRequestId(id);
            List<CFile> files = cFileService.storeFile(inputClaimRequest.getFiles(), id);
        }

        return  iClaimRequestRepository.save(claimRequest);
    }

    public void deleteClaimRequest(Long id){
        iClaimRequestRepository.deleteById(id);
        cFileService.deleteAllFileByClaimRequestId(id);

    }
}

package com.devcamp.Project.controller;

import com.devcamp.Project.entity.CClaimRequest;
import com.devcamp.Project.repository.IClaimRequestRepository;
import com.devcamp.Project.service.CClaimRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/claimrequest")
public class CClaimController {

    @Autowired
    IClaimRequestRepository iClaimRequestRepository;

    @Autowired
    CClaimRequestService cClaimRequestService;

    @GetMapping("/")
    public ResponseEntity<List<CClaimRequest>> getAll(){
        try {
            return new ResponseEntity<>(cClaimRequestService.getAll(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<CClaimRequest> getById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(cClaimRequestService.getById(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createClaim(@ModelAttribute CClaimRequest claimRequest){
        try {
            CClaimRequest cClaimRequest = cClaimRequestService.createClaimRequest(claimRequest);
            return new ResponseEntity<>(cClaimRequest , HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClaimRequest(@ModelAttribute  CClaimRequest claimRequest,
                                                @PathVariable Long id){
        try {
            CClaimRequest cClaimRequest = cClaimRequestService.updateClaimRequest(claimRequest, id);
            return new ResponseEntity<>(cClaimRequest , HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClaimRequest(@PathVariable Long id){
        try {
            cClaimRequestService.deleteClaimRequest(id);
            return ResponseEntity.ok().body(String.format("Delete Claim Request Successfully!"));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(String.format("Delete Claim Request Failed!"));
        }
    }
}

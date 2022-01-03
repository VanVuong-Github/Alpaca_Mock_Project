package com.devcamp.Project.controller;

import com.devcamp.Project.entity.ClaimRequest;
import com.devcamp.Project.repository.ClaimRequestRepository;
import com.devcamp.Project.service.ClaimRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/claimrequest")
public class ClaimController {

    @Autowired
    ClaimRequestRepository claimRequestRepository;

    @Autowired
    ClaimRequestService claimRequestService;

    // lấy tất cả thông tin của claimRequest thông qa service
    @GetMapping("/")
    public ResponseEntity<List<ClaimRequest>> getAll(){
        try {
            return new ResponseEntity<>(claimRequestService.getAll(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // lấy tất cả thông tin của claimRequest theo id thông qa service
    @GetMapping("/{id}")
    public ResponseEntity<ClaimRequest> getById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(claimRequestService.getById(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // tạo mơi claimRequest thông qa service
    @PostMapping("/")
    public ResponseEntity<?> createClaim(@ModelAttribute ClaimRequest claimRequest){
        try {
            ClaimRequest cClaimRequest = claimRequestService.createClaimRequest(claimRequest);
            return new ResponseEntity<>(cClaimRequest , HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // cập nhật claimRequest thông qa service
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClaimRequest(@ModelAttribute ClaimRequest claimRequest,
                                                @PathVariable Long id){
        try {
            ClaimRequest cClaimRequest = claimRequestService.updateClaimRequest(claimRequest, id);
            return new ResponseEntity<>(cClaimRequest , HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // xóa claimRequest thông qa service
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClaimRequest(@PathVariable Long id){
        try {
            claimRequestService.deleteClaimRequest(id);
            return ResponseEntity.ok().body(String.format("Delete Claim Request Successfully!"));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(String.format("Delete Claim Request Failed!"));
        }
    }
}

package com.devcamp.Project.controller;

import com.devcamp.Project.dto.ClaimRequestDTO;
import com.devcamp.Project.entity.ClaimRequest;
import com.devcamp.Project.repository.ClaimRequestRepository;
import com.devcamp.Project.service.ClaimRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/claimrequest")
public class ClaimController {

    @Autowired
    ClaimRequestRepository claimRequestRepository;

    @Autowired
    ClaimRequestService claimRequestService;

    // lấy tất cả thông tin của claimRequest thông qa service
    @GetMapping("")
    public ResponseEntity<List<ClaimRequestDTO>> getAll(){

        return new ResponseEntity<>(claimRequestService.getAll(), HttpStatus.OK);
    }

    // lấy tất cả thông tin của claimRequest theo id thông qa service
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return new ResponseEntity<>(claimRequestService.getById(id),HttpStatus.OK) ;
    }

    @GetMapping("/numClaim/{claim}")
    public ResponseEntity<?> getClaimRequestByNumContract(@PathVariable("claim") String numClaim){
        return new ResponseEntity<>(claimRequestService.getClaimRequestByNumClaimRequest(numClaim), HttpStatus.OK);
    }

    // tạo mơi claimRequest thông qa service
    @PostMapping("/")
    // requestBody
    public ResponseEntity<?> createClaimRequest(@Valid @RequestBody ClaimRequest claimRequest){
        return new ResponseEntity<>(claimRequestService.createClaimRequest(claimRequest), HttpStatus.CREATED) ;
    }

    // cập nhật claimRequest thông qa service
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClaimRequest(@Valid @RequestBody ClaimRequest claimRequest,
                                                @PathVariable Long id) {
        return new ResponseEntity<>(claimRequestService.updateClaimRequest(claimRequest, id), HttpStatus.OK) ;
    }

    // xóa claimRequest thông qa service
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClaimRequest(@PathVariable Long id){
        return new ResponseEntity<>(claimRequestService.deleteClaimRequest(id), HttpStatus.NO_CONTENT);
    }
}

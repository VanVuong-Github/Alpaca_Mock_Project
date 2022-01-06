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
import java.io.IOException;
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
    public List<ClaimRequestDTO> getAll(){
        return claimRequestService.getAll();
    }

    // lấy tất cả thông tin của claimRequest theo id thông qa service
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return claimRequestService.getById(id);

    }

    // tạo mơi claimRequest thông qa service
    @PostMapping("")
    // requestBody
    public ClaimRequestDTO createClaimRequest(@Valid @RequestParam ClaimRequest claimRequest){
        return claimRequestService.createClaimRequest(claimRequest);
    }

    // cập nhật claimRequest thông qa service
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClaimRequest(@RequestParam ClaimRequest claimRequest,
                                                @PathVariable Long id) {
        return claimRequestService.updateClaimRequest(claimRequest, id);
    }

    // xóa claimRequest thông qa service
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClaimRequest(@PathVariable Long id){
        return claimRequestService.deleteClaimRequest(id);
    }
}

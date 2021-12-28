package com.alpaca.Alpaca_Mock_Project.controller;

import com.alpaca.Alpaca_Mock_Project.entity.ClaimRequest;
import com.alpaca.Alpaca_Mock_Project.service.ClaimRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/claim-request")
public class ClaimRequestController {

    @Autowired
    private ClaimRequestService claimRequestService;

    @GetMapping("/{id}")
    public ClaimRequest getClaimRequestById(@PathVariable("id") final Long id){
        return claimRequestService.getClaimRequestById(id);
    }

    @GetMapping
    public List<ClaimRequest> findAll(){
        return claimRequestService.findAll();
    }

    @PostMapping
    public ClaimRequest createClaimRequest(@RequestBody final ClaimRequest claimRequest){
        return claimRequestService.createClaimRequest(claimRequest);
    }

    @PutMapping("/{id}")
    public ClaimRequest updateClaimRequest(@RequestBody final ClaimRequest claimRequest,
                                           @PathVariable("id") final Long id){
        return claimRequestService.updateClaimRequest(claimRequest, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClaimRequest(@PathVariable("id") final Long id){
        try {
            claimRequestService.deleteClaimRequest(id);
            return ResponseEntity.ok().body("Claim Request Deleted!");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Claim Request Deleting Failed!");
        }
    }
}

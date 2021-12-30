package com.alpaca.Alpaca_Mock_Project.controller;

import com.alpaca.Alpaca_Mock_Project.dto.ClaimRequestDto;
import com.alpaca.Alpaca_Mock_Project.dto.ClaimRequestResponse;
import com.alpaca.Alpaca_Mock_Project.service.ClaimRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/claim-request")
public class ClaimRequestController {

    @Autowired
    private ClaimRequestService claimRequestService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getClaimRequestById(@PathVariable("id") final Long id){
        try {
            return ResponseEntity.ok().body(claimRequestService.getClaimRequestById(id));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body("Claim Request Not Found! ");
        }
    }

    @GetMapping("/by")
    public ResponseEntity<?> getClaimRequestByCustomerNameAndCardId(@RequestParam("customerName") final String customerName,
                                                                    @RequestParam("cardId") final String cardId){
        try {
            return ResponseEntity.ok().body(claimRequestService.getClaimRequestByCustomerNameAndCardId(customerName, cardId));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Claim Request Not Found! ");
        }
    }

    @GetMapping
    public List<ClaimRequestResponse> findAll(){
        return claimRequestService.findAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createClaimRequest(@ModelAttribute final ClaimRequestDto claimRequestDto){
        try {
            claimRequestService.createClaimRequest(claimRequestDto);
            return ResponseEntity.ok().body(String.format("Create Claim Request Successfully!"));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(String.format("Create Claim Request Failed! "));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClaimRequest(@ModelAttribute final ClaimRequestDto claimRequestDto,
                                           @PathVariable("id") final Long id){
        try {
            claimRequestService.updateClaimRequest(claimRequestDto, id);
            return ResponseEntity.ok().body(String.format("Update Claim Request Successfully!"));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(String.format("Update Claim Request Failed! "));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClaimRequest(@PathVariable("id") final Long id){
        try {
            claimRequestService.deleteClaimRequest(id);
            return ResponseEntity.ok().body("Claim Request Deleted!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Claim Request Deleting Failed! ");
        }
    }
}

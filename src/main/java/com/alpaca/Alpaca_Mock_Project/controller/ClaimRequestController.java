package com.alpaca.Alpaca_Mock_Project.controller;

import com.alpaca.Alpaca_Mock_Project.dto.ClaimRequestDto;
import com.alpaca.Alpaca_Mock_Project.service.ClaimRequestService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/claim-request")
public class ClaimRequestController {

    private final ClaimRequestService claimRequestService;

    public ClaimRequestController(ClaimRequestService claimRequestService) {
        this.claimRequestService = claimRequestService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaimRequestDto> getClaimRequestById(@PathVariable("id") final Long id){
        return ResponseEntity.ok().body(claimRequestService.getClaimRequestById(id));
    }

    @GetMapping("/by")
    public ResponseEntity<ClaimRequestDto> getClaimRequestByCustomerNameAndCardId(@RequestParam("customerName") final String customerName,
                                                                    @RequestParam("cardId") final String cardId){
        return ResponseEntity.ok().body(claimRequestService.getClaimRequestByCustomerNameAndCardId(customerName, cardId));
    }

    @GetMapping
    public ResponseEntity<List<ClaimRequestDto>> findAll(){
        return ResponseEntity.ok().body(claimRequestService.findAll());
    }

    @PostMapping
    public ResponseEntity<ClaimRequestDto> createClaimRequest(@RequestBody final ClaimRequestDto claimRequestDto){
        claimRequestService.createClaimRequest(claimRequestDto);
        return ResponseEntity.ok().body(claimRequestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaimRequestDto> updateClaimRequest(@RequestBody final ClaimRequestDto claimRequestDto,
                                           @PathVariable("id") @NotNull final Long id){
        claimRequestService.updateClaimRequest(claimRequestDto, id);
        return ResponseEntity.ok().body(claimRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClaimRequest(@PathVariable("id") @NotNull final Long id){
        claimRequestService.deleteClaimRequest(id);
        return ResponseEntity.ok().body("Claim Request Deleted!");
    }
}

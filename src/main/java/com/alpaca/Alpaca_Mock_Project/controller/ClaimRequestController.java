package com.alpaca.Alpaca_Mock_Project.controller;

import com.alpaca.Alpaca_Mock_Project.dto.ClaimRequestDto;
import com.alpaca.Alpaca_Mock_Project.service.impl.ClaimRequestServiceImpl;
import com.sun.istack.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/claim-request")
public class ClaimRequestController {

    private final ClaimRequestServiceImpl claimRequestService;

    public ClaimRequestController(ClaimRequestServiceImpl claimRequestService) {
        this.claimRequestService = claimRequestService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaimRequestDto> getClaimRequestById(@PathVariable("id") @Min(1) final Long id){
        return ResponseEntity.ok().body(claimRequestService.getClaimRequestById(id));
    }

    @GetMapping("/by")
    public ResponseEntity<ClaimRequestDto> getClaimRequestByCustomerNameAndCardId(@RequestParam("customerName") @NotBlank(message = "Customer name is required") final String customerName,
                                                                    @RequestParam("cardId") @NotBlank(message = "No. card id is required") final String cardId){
        return ResponseEntity.ok().body(claimRequestService.getClaimRequestByCustomerNameAndCardId(customerName, cardId));
    }

    @GetMapping
    public ResponseEntity<List<ClaimRequestDto>> findAll(){
        return ResponseEntity.ok().body(claimRequestService.findAll());
    }

    @PostMapping
    public ResponseEntity<ClaimRequestDto> createClaimRequest(@RequestBody @Valid final ClaimRequestDto claimRequestDto){
        claimRequestService.createClaimRequest(claimRequestDto);
        return ResponseEntity.ok().body(claimRequestDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaimRequestDto> updateClaimRequest(@RequestBody @Valid final ClaimRequestDto claimRequestDto,
                                           @PathVariable("id") @Min(1) final Long id){
        claimRequestService.updateClaimRequest(claimRequestDto, id);
        return ResponseEntity.ok().body(claimRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClaimRequest(@PathVariable("id") @Min(1) final Long id){
        claimRequestService.deleteClaimRequest(id);
        return ResponseEntity.ok().body("Claim Request Deleted!");
    }
}

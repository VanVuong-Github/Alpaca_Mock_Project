package com.devcamp.Project.controller;

import com.devcamp.Project.dto.ContractDTO;
import com.devcamp.Project.entity.Contract;
import com.devcamp.Project.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    ContractService contractService;

    // lấy thông tin hợp đồng thông qa service
    @GetMapping("")
    public ResponseEntity<List<ContractDTO>> getAllContract() {

        return new ResponseEntity<>(contractService.getAllContract(), HttpStatus.OK) ;
    }

    // lấy thông tin hợp đồng bằng id thông qa service
    @GetMapping("/{id}")
    public ResponseEntity<?> getContractById(@PathVariable Long id) {

        return new ResponseEntity<>(contractService.getContractById(id), HttpStatus.OK);
    }

    // lấy thông tin hợp đồng bằng id của khách hàng thông qa service
    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getContractByCustomerId(@PathVariable Long id) {
        return new ResponseEntity<>(contractService.getContractByCustomerId(id), HttpStatus.OK);
    }

    @GetMapping("/numcontract/{contract}")
    public ResponseEntity<?> getContractByNumContract(@PathVariable("contract") String numContract){
        return new ResponseEntity<>(contractService.getContractByNumContract(numContract), HttpStatus.OK);
    }
    // tạo mới hợp đồng
    @PostMapping("/customer/{id}")
    public ResponseEntity<?> createContract(@Valid @PathVariable Long id, @RequestBody Contract inputContract) {
        return new ResponseEntity<>(contractService.createContract(id, inputContract), HttpStatus.CREATED);
    }

    // cập nhật hợp đồng
    @PutMapping("/{id}")
    public ResponseEntity<?> updateContract(@Valid @PathVariable Long id, @RequestBody Contract inputContract) {
        return new ResponseEntity<>(contractService.updateContract(id, inputContract), HttpStatus.OK) ;

    }

    // xóa hợp đồng
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContract(@PathVariable Long id) {

        return new ResponseEntity<>(contractService.deleteContractById(id), HttpStatus.NO_CONTENT) ;
    }
}

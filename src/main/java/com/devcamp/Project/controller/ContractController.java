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
    @GetMapping("/")
    public List<ContractDTO> getAllContract() {
        return contractService.getAllContract();
    }

    // lấy thông tin hợp đồng bằng id thông qa service
    @GetMapping("/{id}")
    public ContractDTO getContractById(@PathVariable Long id) {
        return contractService.getContractById(id);
    }

    // lấy thông tin hợp đồng bằng id của khách hàng thông qa service
    @GetMapping("/customer/{id}")
    public List<Contract> getContractByCustomerId(@PathVariable Long id) {
        return contractService.getContractByCustomerId(id);
    }

    // tạo mới hợp đồng
    @PostMapping("/customer/{id}")
    public Object createContract(@Valid @PathVariable Long id, @RequestBody Contract inputContract) {
        return contractService.createContract(id, inputContract);
    }

    // cập nhật hợp đồng
    @PutMapping("/{id}")
    public Object updateContract(@PathVariable Long id, @RequestBody Contract inputContract) {
        return contractService.updateContract(id, inputContract);

    }

    // xóa hợp đồng
    @DeleteMapping("/")
    public ResponseEntity<?> deleteContract(@PathVariable Long id) {
        return contractService.deleteContractById(id);
    }
}

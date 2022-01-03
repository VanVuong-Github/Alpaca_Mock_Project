package com.devcamp.Project.controller;

import com.devcamp.Project.entity.Contract;
import com.devcamp.Project.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    ContractService contractService;

    // lấy thông tin hợp đồng thông qa service
    @GetMapping("/")
    public ResponseEntity<List<Contract>> getAllContract(){
        try {
            return new  ResponseEntity<>(contractService.getAllContract(), HttpStatus.OK);
        } catch(Exception e) {
            return new  ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // lấy thông tin hợp đồng bằng id thông qa service
    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(contractService.getContractById(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // lấy thông tin hợp đồng bằng id của khách hàng thông qa service
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Contract>> getContractByCustomerId(@PathVariable Long id){
        try {
            return new ResponseEntity<>(contractService.getContractByCustomerId(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // tạo mới hợp đồng
    @PostMapping("/customer/{id}")
    public ResponseEntity<Object> createContract(@PathVariable Long id, @RequestBody Contract inputContract){
        try {
            return new ResponseEntity<>(contractService.createContract(id, inputContract), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // cập nhật hợp đồng
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateContract(@PathVariable Long id, @RequestBody Contract inputContract){
        try {
            return new ResponseEntity<>(contractService.updateContract(id, inputContract), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // xóa hợp đồng
    @DeleteMapping("/")
    public ResponseEntity<Object> deleteContract(@PathVariable Long id){
        try {
            contractService.deleteContractById(id);
            return ResponseEntity.ok().body(String.format("Delete Contract Successfully!"));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(String.format("Delete Contract Failed!"));
        }
    }

}

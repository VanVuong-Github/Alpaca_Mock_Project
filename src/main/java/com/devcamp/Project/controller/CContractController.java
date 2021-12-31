package com.devcamp.Project.controller;

import com.devcamp.Project.entity.CContract;
import com.devcamp.Project.service.CContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract")
public class CContractController {

    @Autowired
    CContractService cContractService;

    // lấy thông tin hợp đồng thông qa service
    @GetMapping("/")
    public ResponseEntity<List<CContract>> getAllContract(){
        try {
            return new  ResponseEntity<>(cContractService.getAllContract(), HttpStatus.OK);
        } catch(Exception e) {
            return new  ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // lấy thông tin hợp đồng bằng id thông qa service
    @GetMapping("/{id}")
    public ResponseEntity<CContract> getContractById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(cContractService.getContractById(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // lấy thông tin hợp đồng bằng id của khách hàng thông qa service
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<CContract>> getContractByCustomerId(@PathVariable Long id){
        try {
            return new ResponseEntity<>(cContractService.getContractByCustomerId(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // tạo mới hợp đồng
    @PostMapping("/customer/{id}")
    public ResponseEntity<Object> createContract(@PathVariable Long id, @RequestBody CContract inputContract){
        try {
            return new ResponseEntity<>(cContractService.createContract(id, inputContract), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // cập nhật hợp đồng
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateContract(@PathVariable Long id, @RequestBody CContract inputContract){
        try {
            return new ResponseEntity<>(cContractService.updateContract(id, inputContract), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // xóa hợp đồng
    @DeleteMapping("/")
    public ResponseEntity<Object> deleteContract(@PathVariable Long id){
        try {
            cContractService.deleteContractById(id);
            return ResponseEntity.ok().body(String.format("Delete Contract Successfully!"));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(String.format("Delete Contract Failed!"));
        }
    }

}

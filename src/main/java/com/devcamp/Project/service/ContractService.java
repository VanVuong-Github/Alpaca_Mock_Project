package com.devcamp.Project.service;

import com.devcamp.Project.dto.ContractDTO;
import com.devcamp.Project.entity.Contract;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.mapped.ContractMapped;
import com.devcamp.Project.mapped.CustomerMapped;
import com.devcamp.Project.repository.ContractRepository;
import com.devcamp.Project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    ContractMapped contractMapped;

    // lấy tất cả thông tin hợp đồng
    @Transactional
    public List<ContractDTO> getAllContract(){

        return contractRepository.findAll().stream()
                .map(contractMapped::contractToContractDTO).collect(Collectors.toList());
    }

    // lấy thông tin hợp đồng theo id
    @Transactional
    public ContractDTO getContractById(final Long id){

        //return contractRepository.findById(id).orElse(null);
        return ContractMapped.INSTANCE.contractToContractDTO(contractRepository.findById(id).get());
    }

    // lấy thông tin hợp đồng theo id của khách hàng
    @Transactional
    public List<Contract> getContractByCustomerId(final Long customerId){
        return contractRepository.findByCustomerId(customerId);
    }

    // tạo mới hợp đồng
    @Transactional
    public ContractDTO createContract(Long customerId, Contract inputContract){
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()){
            inputContract.setCustomer(customer.get());
            return ContractMapped.INSTANCE.contractToContractDTO(contractRepository.save(inputContract));
        } else {
            return null;
        }
    }

    // cập nhật hợp đồng
    @Transactional
    public ContractDTO updateContract(Long id, Contract inputContract){
        Contract contract = contractRepository.findById(id).orElse(null);
        contract.setValidFrom(inputContract.getValidFrom());
        contract.setValidTo((inputContract.getValidTo()));
        contract.setMaximumAmount(inputContract.getMaximumAmount());
        contract.setRemainingAmount(inputContract.getRemainingAmount());
        contract.setAcceptableAccidents(inputContract.getAcceptableAccidents());
        contract.setAcceptableHospitals(inputContract.getAcceptableHospitals());

        return ContractMapped.INSTANCE.contractToContractDTO(contractRepository.save(contract));

    }

    // xóa hợp đồng
    @Transactional
    public ResponseEntity<?> deleteContractById(final Long id){
        Optional<Contract> contract = contractRepository.findById(id);
        if(contract.isPresent()){
            contractRepository.deleteById(id);
            return ResponseEntity.ok("Deleted Contract Success!!");
        } else {
            return ResponseEntity.ok("Deleted Contract Fail!!");
        }
    }
}

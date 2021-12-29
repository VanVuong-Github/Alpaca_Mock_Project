package com.devcamp.Project.service;

import com.devcamp.Project.entity.CContract;
import com.devcamp.Project.entity.CCustomer;
import com.devcamp.Project.repository.IContractRepository;
import com.devcamp.Project.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CContractService {
    @Autowired
    private IContractRepository iContractRepository;

    @Autowired
    private ICustomerRepository iCustomerRepository;

    public List<CContract> getAllContract(){
        return iContractRepository.findAll();
    }

    public  CContract getContractById(final Long id){
        return iContractRepository.findById(id).orElse(null);
    }

    public List<CContract> getContractByCustomerId(final Long customerId){
        return iContractRepository.findByCustomerId(customerId);
    }

    public CContract createContract(Long customerId, CContract inputContract){
        Optional<CCustomer> customer = iCustomerRepository.findById(customerId);
        if (customer.isPresent()){
            inputContract.setCustomer(customer.get());
            return iContractRepository.save(inputContract);
        } else {
            return null;
        }
    }

    public CContract updateContract(Long id, CContract inputContract){
        CContract contract = iContractRepository.findById(id).orElse(null);
        contract.setValidFrom(inputContract.getValidFrom());
        contract.setValidTo((inputContract.getValidTo()));
        contract.setMaximumAmount(inputContract.getMaximumAmount());
        contract.setRemainingAmount(inputContract.getRemainingAmount());
        contract.setAcceptableAccidents(inputContract.getAcceptableAccidents());
        contract.setAcceptableHospitals(inputContract.getAcceptableHospitals());

        return iContractRepository.save(contract);

    }

    public void deleteContractById(final Long id){

        iContractRepository.deleteById(id);
    }
}

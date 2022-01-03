package com.devcamp.Project.service;

import com.devcamp.Project.entity.Contract;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.repository.ContractRepository;
import com.devcamp.Project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // lấy tất cả thông tin hợp đồng
    public List<Contract> getAllContract(){
        return contractRepository.findAll();
    }

    // lấy thông tin hợp đồng theo id
    public Contract getContractById(final Long id){
        return contractRepository.findById(id).orElse(null);
    }

    // lấy thông tin hợp đồng theo id của khách hàng
    public List<Contract> getContractByCustomerId(final Long customerId){
        return contractRepository.findByCustomerId(customerId);
    }

    // tạo mới hợp đồng
    public Contract createContract(Long customerId, Contract inputContract){
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()){
            inputContract.setCustomer(customer.get());
            return contractRepository.save(inputContract);
        } else {
            return null;
        }
    }

    // cập nhật hợp đồng
    public Contract updateContract(Long id, Contract inputContract){
        Contract contract = contractRepository.findById(id).orElse(null);
        contract.setValidFrom(inputContract.getValidFrom());
        contract.setValidTo((inputContract.getValidTo()));
        contract.setMaximumAmount(inputContract.getMaximumAmount());
        contract.setRemainingAmount(inputContract.getRemainingAmount());
        contract.setAcceptableAccidents(inputContract.getAcceptableAccidents());
        contract.setAcceptableHospitals(inputContract.getAcceptableHospitals());

        return contractRepository.save(contract);

    }

    // xóa hợp đồng
    public void deleteContractById(final Long id){
        contractRepository.deleteById(id);
    }
}

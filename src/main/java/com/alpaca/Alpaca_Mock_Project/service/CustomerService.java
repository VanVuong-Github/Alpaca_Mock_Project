package com.alpaca.Alpaca_Mock_Project.service;

import com.alpaca.Alpaca_Mock_Project.dto.CustomerDto;
import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import com.alpaca.Alpaca_Mock_Project.mapper.CustomerMapper;
import com.alpaca.Alpaca_Mock_Project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional
    public CustomerDto getCustomerById(final Long id){
        return CustomerMapper.INSTANCE.customerToCustomerDto(customerRepository.findById(id).orElse(null));
    }

    @Transactional
    public List<CustomerDto> findAll(){
        // map List<Customer> to List<CustomerDto> to remove customer id from response customer list
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto).collect(Collectors.toList());
    }

    @Transactional
    public void saveCustomer(final CustomerDto customer){
        customerRepository.save(CustomerMapper.INSTANCE.customerDtoToCustomer(customer));
    }

    @Transactional
    public void updateCustomer(final CustomerDto customer, final Long id){
        Customer oldCustomer = customerRepository.findById(id).orElseThrow(NullPointerException::new); // this is the customer before update
        oldCustomer.setName(customer.getName());
        oldCustomer.setGender(customer.getGender());
        oldCustomer.setCardId(customer.getCardId());
        oldCustomer.setPhone(customer.getPhone());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setDateOfBirth(customer.getDateOfBirth());
        oldCustomer.setAddress(customer.getAddress());
        oldCustomer.setOccupation(customer.getOccupation());
        CustomerMapper.INSTANCE.customerToCustomerDto(customerRepository.save(oldCustomer));
    }

    @Transactional
    public void deleteCustomerById(final Long id){
        Customer oldCustomer = customerRepository.findById(id).orElseThrow(NullPointerException::new);
        customerRepository.delete(oldCustomer);
    }
}

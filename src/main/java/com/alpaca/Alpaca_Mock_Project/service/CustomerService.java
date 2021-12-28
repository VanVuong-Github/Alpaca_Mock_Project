package com.alpaca.Alpaca_Mock_Project.service;

import com.alpaca.Alpaca_Mock_Project.dto.CustomerDto;
import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import com.alpaca.Alpaca_Mock_Project.mapper.CustomerMapper;
import com.alpaca.Alpaca_Mock_Project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public Customer getCustomerById(final Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<CustomerDto> findAll(){
        // map List<Customer> to List<CustomerDto> to remove customer id from response customer list
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto).collect(Collectors.toList());
    }

    public Customer saveCustomer(final Customer customer){
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(final Customer customer, final Long id){
        Customer oldCustomer = getCustomerById(id); // this is the customer before update
        oldCustomer.setName(customer.getName());
        oldCustomer.setGender(customer.getGender());
        oldCustomer.setCardId(customer.getCardId());
        oldCustomer.setPhone(customer.getPhone());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setDateOfBirth(customer.getDateOfBirth());
        oldCustomer.setAddress(customer.getAddress());
        oldCustomer.setOccupation(customer.getOccupation());
        return customerRepository.save(oldCustomer);
    }

    public void deleteCustomerById(final Long id){
        customerRepository.deleteById(id);
    }
}

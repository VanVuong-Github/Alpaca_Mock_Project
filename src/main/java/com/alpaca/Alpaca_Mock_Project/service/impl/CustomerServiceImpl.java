package com.alpaca.Alpaca_Mock_Project.service.impl;

import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import com.alpaca.Alpaca_Mock_Project.repository.CustomerRepository;
import com.alpaca.Alpaca_Mock_Project.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class.getName());

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer getCustomerById(final Long id){
        logger.log(Level.INFO, "Get customer with id: {0}", id);
        return customerRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Override
    @Transactional
    public List<Customer> findAll(){
        logger.log(Level.INFO, "Get all customer");
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer saveCustomer(final Customer customer){
        logger.log(Level.INFO, "Save new customer");
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer updateCustomer(final Customer customer, final Long id){
        logger.log(Level.INFO, "Update customer with id: {0}", id);
        Customer oldCustomer = customerRepository.findById(id).orElseThrow(NullPointerException::new); // this is the customer before update
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

    @Override
    @Transactional
    public void deleteCustomerById(final Long id){
        logger.log(Level.INFO, "Delete customer with id: {0}", id);
        Customer oldCustomer = customerRepository.findById(id).orElseThrow(NullPointerException::new);
        customerRepository.delete(oldCustomer);
    }
}

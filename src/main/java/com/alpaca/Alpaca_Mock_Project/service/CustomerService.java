package com.alpaca.Alpaca_Mock_Project.service;

import com.alpaca.Alpaca_Mock_Project.dto.CustomerDto;
import com.alpaca.Alpaca_Mock_Project.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer getCustomerById(final Long id);

    List<Customer> findAll();

    Customer saveCustomer(final CustomerDto customer);

    Customer updateCustomer(final CustomerDto customer, final Long id);

    void deleteCustomerById(final Long id);
}

package com.alpaca.Alpaca_Mock_Project.service;

import com.alpaca.Alpaca_Mock_Project.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer getCustomerById(final Long id);

    List<Customer> findAll();

    Customer saveCustomer(final Customer customer);

    Customer updateCustomer(final Customer customer, final Long id);

    void deleteCustomerById(final Long id);

    List<Customer> search(String textToSearch);
}

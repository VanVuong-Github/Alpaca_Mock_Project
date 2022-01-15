package com.alpaca.Alpaca_Mock_Project.service.impl;

import com.alpaca.Alpaca_Mock_Project.RabbitMQConfig;
import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import com.alpaca.Alpaca_Mock_Project.repository.CustomerRepository;
import com.alpaca.Alpaca_Mock_Project.service.CustomerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class.getName());

    private final CustomerRepository customerRepository;

    private final RabbitTemplate rabbitTemplate;

    public CustomerServiceImpl(CustomerRepository customerRepository, RabbitTemplate rabbitTemplate) {
        this.customerRepository = customerRepository;
        this.rabbitTemplate = rabbitTemplate;
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
    public List<Customer> search(String textToSearch){
        logger.log(Level.INFO, "Get all customer");
        return customerRepository.findAllByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(textToSearch, textToSearch, textToSearch);
    }

    @Override
    @Transactional
    public Customer saveCustomer(final Customer customer){
        logger.log(Level.INFO, "Save new customer");
        Customer customerToSave = customerRepository.save(customer);
        String routingKey = "customer.created";
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, routingKey, customerToSave);
        return customerToSave;
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
        customerRepository.save(oldCustomer);
        String routingKey = "customer.updated";
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, routingKey, oldCustomer);
        return oldCustomer;
    }

    @Override
    @Transactional
    public void deleteCustomerById(final Long id){
        logger.log(Level.INFO, "Delete customer with id: {0}", id);
        Customer oldCustomer = customerRepository.findById(id).orElseThrow(NullPointerException::new);
        customerRepository.delete(oldCustomer);
        String routingKey = "customer.deleted";
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, routingKey, id);
    }
}

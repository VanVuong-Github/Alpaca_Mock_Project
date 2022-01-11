package com.devcamp.Project.elasticSearchService;

import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.repository.CustomerElasticSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerElasticSearchService {

    private final Logger logger = LoggerFactory.getLogger(CustomerElasticSearchService.class);

    private final CustomerElasticSearchRepository customerElasticSearchRepository;

    public CustomerElasticSearchService(CustomerElasticSearchRepository customerElasticSearchRepository) {
        this.customerElasticSearchRepository = customerElasticSearchRepository;
    }

    public Object getCustomerById(final Long id){
        Optional<Customer> oldCustomer = customerElasticSearchRepository.findById(id);
        if (oldCustomer.isPresent()){
            logger.info("Customer with ID {} had found!",id);
            return oldCustomer.orElse(null);
        } else {
            logger.error("Customer do not exist!");
            return "Customer do not exist!";
        }
    }

    public Iterable<Customer> getAll(){
        return customerElasticSearchRepository.findAll();
    }

//    public Iterable<Customer> insertBulk(final List<Customer> customerList){
//        return customerElasticSearchRepository.saveAll(customerList);
//    }

    public Object delete( Long id){
        Optional<Customer> customer = customerElasticSearchRepository.findById(id);
        if ( customer.isPresent()){
            customerElasticSearchRepository.deleteById(id);
            logger.info("Customer with ID {} deleted", id);
            return "Deleted Customer Success!!";
        } else {
            logger.error("Customer do not exist!");
            return "Customer do not exist!";
        }
    }

    public Object create(final Customer inputcustomer){
        Customer customer =  customerElasticSearchRepository.save(inputcustomer);
        logger.info("Customer with ID {} saved",customer.getId());
        return customer;
    }

    public Object update(Long id, Customer customer){
        Optional<Customer> oldCustomer = customerElasticSearchRepository.findById(id);
        if(oldCustomer.isPresent()) {
            logger.info("Customer with ID {} updated",oldCustomer.get().getId());
            return customerElasticSearchRepository.save(customer);
        } else {
            logger.error("Customer do not exist!");
            return "Customer do not exist!";
        }
    }

}

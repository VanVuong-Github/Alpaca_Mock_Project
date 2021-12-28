package com.devcamp.Project.repository;

import com.devcamp.Project.entity.CCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<CCustomer, Long> {

}

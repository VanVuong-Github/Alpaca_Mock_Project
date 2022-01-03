package com.devcamp.Project.repository;

import com.devcamp.Project.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByCustomerId(Long id);
}

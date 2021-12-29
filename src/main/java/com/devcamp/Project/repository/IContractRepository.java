package com.devcamp.Project.repository;

import com.devcamp.Project.entity.CContract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IContractRepository extends JpaRepository<CContract, Long> {
    List<CContract> findByCustomerId( Long id);
}

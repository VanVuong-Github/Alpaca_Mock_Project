package com.devcamp.Project.repository;

import com.devcamp.Project.entity.CFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFileRepository extends JpaRepository<CFile, Long> {

    List<CFile> findByClaimRequestId(Long id);

    void deleteAllByClaimRequestId(Long id);
}

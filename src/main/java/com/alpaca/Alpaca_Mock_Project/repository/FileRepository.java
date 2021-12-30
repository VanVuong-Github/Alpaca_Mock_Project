package com.alpaca.Alpaca_Mock_Project.repository;

import com.alpaca.Alpaca_Mock_Project.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findAllByRequestId(Long id);

    void deleteAllByRequestId(Long id);
}

package com.alpaca.Alpaca_Mock_Project.service;

import com.alpaca.Alpaca_Mock_Project.entity.FileEntity;
import com.alpaca.Alpaca_Mock_Project.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Transactional
    public FileEntity save(MultipartFile file, Long id) throws IOException {
        FileEntity fileEntity = FileEntity.builder()
                .name(StringUtils.cleanPath(file.getOriginalFilename()))
                .contentType(file.getContentType())
                .size(file.getSize())
                .data(file.getBytes())
                .requestId(id)
                .build();
        return fileRepository.save(fileEntity);
    }

    @Transactional
    public List<FileEntity> saveAll(List<MultipartFile> files, Long id) throws IOException {
        List<FileEntity> filesToSave = new ArrayList<>();
        for (MultipartFile file: files) {
            FileEntity fileEntity = FileEntity.builder()
                    .name(StringUtils.cleanPath(file.getOriginalFilename()))
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .data(file.getBytes())
                    .requestId(id)
                    .build();
            filesToSave.add(fileEntity);
        }
        return fileRepository.saveAll(filesToSave);
    }

    @Transactional
    public FileEntity getFileById(final Long id){
        return fileRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<FileEntity> findAll(){
        return fileRepository.findAll();
    }

    /**
     * find all file save in db with request id
     * @param id - request id
     * @return
     */
    @Transactional
    public List<FileEntity> findAllByRequestId(final Long id){
        return fileRepository.findAllByRequestId(id);
    }

    @Transactional
    public void deleteAllByRequestId(final Long id){
        fileRepository.deleteAllByRequestId(id);
    }
}

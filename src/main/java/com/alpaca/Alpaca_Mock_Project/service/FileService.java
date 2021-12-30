package com.alpaca.Alpaca_Mock_Project.service;

import com.alpaca.Alpaca_Mock_Project.dto.FileResponse;
import com.alpaca.Alpaca_Mock_Project.entity.FileEntity;
import com.alpaca.Alpaca_Mock_Project.exception.FileStorageException;
import com.alpaca.Alpaca_Mock_Project.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Transactional
    public FileEntity save(MultipartFile file, Long id){
        try {
            FileEntity fileEntity = mapMultipartFileToFileEntity(file, id);
            return fileRepository.save(fileEntity);
        } catch (Exception e){
            e.printStackTrace();
            throw new FileStorageException("Unable to store file!");
        }
    }

    @Transactional
    public List<FileEntity> saveAll(List<MultipartFile> files, Long id){
        List<FileEntity> filesToSave = new ArrayList<>();
        for (MultipartFile file: files) {
            try {
                FileEntity fileEntity = mapMultipartFileToFileEntity(file, id);
                filesToSave.add(fileEntity);
            } catch (Exception e){
                e.printStackTrace();
                throw new FileStorageException("Unable to store file!");
            }
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
    public List<FileResponse> findAllByRequestId(final Long id){
        return fileRepository.findAllByRequestId(id)
                .stream().map(this::mapToFileResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deleteAllByRequestId(final Long id){
        fileRepository.deleteAllByRequestId(id);
    }

    /**
     * map a MultipartFile to FileEntity with ClaimRequest id require
     * @param file
     * @param id
     * @return
     * @throws IOException
     */
    private FileEntity mapMultipartFileToFileEntity(MultipartFile file, Long id) throws IOException {
        FileEntity fileEntity = FileEntity.builder()
                .name(StringUtils.cleanPath(file.getOriginalFilename()))
                .contentType(file.getContentType())
                .size(file.getSize())
                .data(file.getBytes())
                .requestId(id)
                .build();
        return fileEntity;
    }

    /**
     * map a FileEntity to FileResponse
     * @param file
     * @return fileResponse
     */
    private FileResponse mapToFileResponse(FileEntity file){
        // build url for this file
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(file.getId().toString())
                .toUriString();

        FileResponse fileResponse = FileResponse.builder()
                .id(file.getId())
                .name(file.getName())
                .contentType(file.getContentType())
                .size(file.getSize())
                .url(url)
                .requestId(file.getRequestId())
                .build();
        return fileResponse;
    }
}

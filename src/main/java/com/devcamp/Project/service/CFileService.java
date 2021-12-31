package com.devcamp.Project.service;

import com.devcamp.Project.entity.CClaimRequest;
import com.devcamp.Project.entity.CFile;
import com.devcamp.Project.exception.CFileStorageException;
import com.devcamp.Project.exception.CMyFileNotFoundException;
import com.devcamp.Project.entity.CFileProperties;
import com.devcamp.Project.repository.IClaimRequestRepository;
import com.devcamp.Project.repository.IFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class CFileService {

    private final Path fileStorageLocation;

    @Autowired
    public CFileService(CFileProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new CFileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Autowired
    IFileRepository iFileRepository;


    // lưu trữ file
    public List<CFile> storeFile(List<MultipartFile> files, long id) throws IOException {

        // Lưu ảnh vào DB
        List<CFile> filesToSave = new ArrayList<>();
        for (MultipartFile file: files) {
            CFile fileEntity = CFile.builder()
                    .fileName(StringUtils.cleanPath(file.getOriginalFilename()))
                    .fileType(file.getContentType())
                    .size(file.getSize())
                    .data(file.getBytes())
                    .claimRequestId(id)
                    .build();
            filesToSave.add(fileEntity);

            // Lưu ảnh vào folder pictures trong project
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        }

        return iFileRepository.saveAll(filesToSave);
    }

    // lấy tất cả file trong Db
    public List<CFile> getAll(){
        return iFileRepository.findAll();
    }

    // lấy file bằng id trong Db
    public CFile getById(Long id){
        return iFileRepository.findById(id).orElse(null);
    }

    // lấy file luu trong folder
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new CMyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new CMyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    // xóa file theo id của claimRequest
    public void deleteAllFileByClaimRequestId(Long id){
        iFileRepository.deleteAllByClaimRequestId(id);
    }
}

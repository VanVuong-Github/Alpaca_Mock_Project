package com.devcamp.Project.service;

import com.devcamp.Project.entity.File;
import com.devcamp.Project.exception.FileStorageException;
import com.devcamp.Project.exception.MyFileNotFoundException;
import com.devcamp.Project.entity.FileProperties;
import com.devcamp.Project.repository.FileRepository;
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
public class FileService {

    private final Path fileStorageLocation;

    @Autowired
    public FileService(FileProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Autowired
    FileRepository fileRepository;


    // lưu trữ file
    public List<File> storeFile(List<MultipartFile> files, long id) throws IOException {

        // Lưu ảnh vào DB
        List<File> filesToSave = new ArrayList<>();
        for (MultipartFile file: files) {
//            File fileEntity = File.builder()
//                    .fileName(StringUtils.cleanPath(file.getOriginalFilename()))
//                    .fileType(file.getContentType())
//                    .size(file.getSize())
//                    .data(file.getBytes())
//                    .claimRequestId(id)
//                    .build();
//            filesToSave.add(fileEntity);

            // Lưu ảnh vào folder pictures trong project
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileRepository.saveAll(filesToSave);
    }

    // lấy tất cả file trong Db
    public List<File> getAll(){
        return fileRepository.findAll();
    }

    // lấy file bằng id trong Db
    public File getById(Long id){
        return fileRepository.findById(id).orElse(null);
    }

    // lấy file luu trong folder
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    // xóa file theo id của claimRequest
    public void deleteAllFileByClaimRequestId(Long id){
        fileRepository.deleteAllByClaimRequestId(id);
    }
}

package com.alpaca.Alpaca_Mock_Project.controller;

import com.alpaca.Alpaca_Mock_Project.entity.FileEntity;
import com.alpaca.Alpaca_Mock_Project.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable("id") final Long id){
        FileEntity fileEntity = fileService.getFileById(id);
        if (fileEntity.equals(null)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                .contentType(MediaType.valueOf(fileEntity.getContentType()))
                .body(fileEntity.getData());
    }
}

package com.devcamp.Project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

public class File {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    private long claimRequestId;

    public File(String fileName, String fileDownloadUri, String fileType, long size, long claimRequestId) {

        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
        this.claimRequestId = claimRequestId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getClaimRequestId() {
        return claimRequestId;
    }

    public void setClaimRequestId(long claimRequestId) {
        this.claimRequestId = claimRequestId;
    }
}

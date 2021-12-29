package com.alpaca.Alpaca_Mock_Project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResponse {
    private Long id;
    private String name;
    private Long size;
    private String contentType;
    private String url;
    private Long requestId;
}

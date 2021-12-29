package com.alpaca.Alpaca_Mock_Project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRequestDto {
    private String customerName;
    private String cardId;

    @Lob
    private List<MultipartFile> photos;
}

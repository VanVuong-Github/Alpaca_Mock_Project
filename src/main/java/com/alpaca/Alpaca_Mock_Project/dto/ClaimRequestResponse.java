package com.alpaca.Alpaca_Mock_Project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimRequestResponse {
    private String customerName;
    private String cardId;
    private List<FileResponse> photos;
}

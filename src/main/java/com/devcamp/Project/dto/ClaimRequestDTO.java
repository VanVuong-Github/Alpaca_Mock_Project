package com.devcamp.Project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@Getter
@Setter
public class ClaimRequestDTO {

    private String customerName;
    private String customerCardId;
}

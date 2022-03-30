package pl.fc.invoicing.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyListDto {

    private String taxIdentifier;
    private String name;
}

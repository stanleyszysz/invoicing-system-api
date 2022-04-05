package pl.fc.invoicing.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import pl.fc.invoicing.model.Address;

@Data
@Builder
public class CompanyListDto {

    private String taxIdentifier;
    private String name;
    private Address address;
    private BigDecimal pensionInsurance;
    private BigDecimal healthInsurance;
    
}

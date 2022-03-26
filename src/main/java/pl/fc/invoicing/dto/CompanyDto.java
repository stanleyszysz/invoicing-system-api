package pl.fc.invoicing.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fc.invoicing.model.Address;
import pl.fc.invoicing.model.Company;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private UUID companyId;
    private String taxIdentifier;
    private String name;
    private Address address;
    private BigDecimal pensionInsurance;
    private BigDecimal healthInsurance;

    public CompanyDto(Company company) {
        this.companyId = company.getCompanyId();
        this.taxIdentifier = company.getTaxIdentifier();
        this.name = company.getName();
        this.address = company.getAddress();
        this.pensionInsurance = company.getPensionInsurance();
        this.healthInsurance = company.getHealthInsurance();

    }
}

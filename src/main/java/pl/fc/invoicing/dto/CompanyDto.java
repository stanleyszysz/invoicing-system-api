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

    public static CompanyDto of(Company company) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.companyId = company.getCompanyId();
        companyDto.taxIdentifier = company.getTaxIdentifier();
        companyDto.name = company.getName();
        companyDto.address = company.getAddress();
        companyDto.pensionInsurance = company.getPensionInsurance();
        companyDto.healthInsurance = company.getHealthInsurance();
        return companyDto;
    }
}

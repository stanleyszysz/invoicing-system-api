package pl.fc.invoicing.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fc.invoicing.model.Address;
import pl.fc.invoicing.model.Company;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private UUID companyId;
    private String taxIdentifier;
    private String name;
    private Address address;

    public CompanyDto(Company company) {
        this.companyId = company.getCompanyId();
        this.taxIdentifier = company.getTaxIdentifier();
        this.name = company.getName();
        this.address = company.getAddress();

    }
}

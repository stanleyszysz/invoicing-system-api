package pl.fc.invoicing.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.fc.invoicing.dto.CompanyDto;

@Entity
@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "companyId", length = 16, updatable = false, nullable = false)
    @Schema(description = "Company id", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
    private UUID companyId;
    @Schema(description = "Tax Identification Number", example = "5252287009", required = true)
    private String taxIdentifier;
    @Schema(description = "Company Name", example = "Torte", required = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @JsonManagedReference
    private Address address;

    @Builder.Default
    @Schema(description = "Pension insurance amount", example = "1000.00", required = true)
    private BigDecimal pensionInsurance = BigDecimal.ZERO;

    @Builder.Default
    @Schema(description = "Health insurance amount", example = "500.00", required = true)
    private BigDecimal healthInsurance = BigDecimal.ZERO;

    public static Company of(CompanyDto companyDto) {
        Company company = new Company();
        company.companyId = companyDto.getCompanyId();
        company.taxIdentifier = companyDto.getTaxIdentifier();
        company.name = companyDto.getName();
        company.address = companyDto.getAddress();
        company.pensionInsurance = companyDto.getPensionInsurance();
        company.healthInsurance = companyDto.getHealthInsurance();
        return company;
    }

}

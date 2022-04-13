package pl.fc.invoicing.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fc.invoicing.model.Company;
import pl.fc.invoicing.model.InvoiceEntry;
// import pl.fc.invoicing.dto.InvoiceToSaveDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {

    private UUID invoiceId;
    private LocalDate dateAt;
    private String number;
    private Company seller;
    private Company buyer;
    private List<InvoiceEntry> entries;

    public static InvoiceToSaveDto of(InvoiceDto invoiceDto) {
        InvoiceToSaveDto invoiceToSaveDto = new InvoiceToSaveDto();
        invoiceToSaveDto.setDateAt(invoiceDto.getDateAt());
        invoiceToSaveDto.setNumber(invoiceDto.getNumber());
        invoiceToSaveDto.setSellerTaxIdentifier(invoiceDto.getSeller().getTaxIdentifier());
        invoiceToSaveDto.setBuyerTaxIdentifier(invoiceDto.getBuyer().getTaxIdentifier());
        invoiceToSaveDto.setEntries(invoiceDto.getEntries());
        return invoiceToSaveDto;
    }
}

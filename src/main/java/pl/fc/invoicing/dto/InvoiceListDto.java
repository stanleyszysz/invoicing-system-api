package pl.fc.invoicing.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import pl.fc.invoicing.model.Company;
import pl.fc.invoicing.model.InvoiceEntry;

@Data
@Builder
public class InvoiceListDto {

    private UUID invoiceId;
    private LocalDate dateAt;
    private String number;
    private Company seller;
    private Company buyer;
    private List<InvoiceEntry> entries;
}

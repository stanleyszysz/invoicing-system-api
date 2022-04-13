package pl.fc.invoicing.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fc.invoicing.model.InvoiceEntry;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceToSaveDto {

    private LocalDate dateAt;
    private String number;
    private String sellerTaxIdentifier;
    private String buyerTaxIdentifier;
    private List<InvoiceEntry> entries;
}

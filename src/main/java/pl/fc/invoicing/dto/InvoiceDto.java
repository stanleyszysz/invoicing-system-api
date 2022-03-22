package pl.fc.invoicing.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fc.invoicing.model.Company;
import pl.fc.invoicing.model.Invoice;
import pl.fc.invoicing.model.InvoiceEntry;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {

    private UUID invoiceId;
    // private LocalDate dateAt;
    private String number;
    private Company seller;
    private Company buyer;
    private List<InvoiceEntry> entries;

    public InvoiceDto(Invoice invoice) {
        this.invoiceId = invoice.getInvoiceId();
        // this.dateAt = invoice.getDateAt();
        this.number = invoice.getNumber();
        this.seller = invoice.getSeller();
        this.buyer = invoice.getBuyer();
        this.entries = invoice.getEntries();
    }
}

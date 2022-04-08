package pl.fc.invoicing.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceListDto {

    private UUID invoiceId;
    private LocalDate dateAt;
    private String number;

    public static InvoiceListDto of(InvoiceDto invoiceDto) {
        return new InvoiceListDto(invoiceDto.getInvoiceId(), invoiceDto.getDateAt(), invoiceDto.getNumber());
    }
}

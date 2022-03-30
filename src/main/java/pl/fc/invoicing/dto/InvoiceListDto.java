package pl.fc.invoicing.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceListDto {

    private LocalDate dateAt;
    private String number;

    public static InvoiceListDto of(InvoiceDto invoiceDto) {
        return new InvoiceListDto(invoiceDto.getDateAt(), invoiceDto.getNumber());
    }
}

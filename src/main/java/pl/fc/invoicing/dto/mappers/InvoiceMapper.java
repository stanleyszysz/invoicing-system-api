package pl.fc.invoicing.dto.mappers;

import org.mapstruct.Mapper;
import pl.fc.invoicing.dto.InvoiceDto;
import pl.fc.invoicing.model.Invoice;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    Invoice toEntity(InvoiceDto invoiceDto);

    InvoiceDto toDto(Invoice invoice);
}

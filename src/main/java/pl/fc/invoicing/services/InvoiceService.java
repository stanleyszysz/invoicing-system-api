package pl.fc.invoicing.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import pl.fc.invoicing.dto.InvoiceDto;
import pl.fc.invoicing.dto.InvoiceListDto;

public interface InvoiceService {

    InvoiceDto save(InvoiceDto invoice);

    Optional<InvoiceDto> getById(UUID id);

    List<InvoiceListDto> getAll();

    InvoiceDto update(UUID id, InvoiceDto updatedInvoice);

    void delete(UUID id);
}

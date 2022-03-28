package pl.fc.invoicing.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import pl.fc.invoicing.dto.InvoiceDto;

public interface InvoiceService {

    InvoiceDto save(InvoiceDto invoice);

    Optional<InvoiceDto> getById(UUID id);

    List<InvoiceDto> getAll();

    InvoiceDto update(UUID id, InvoiceDto updatedInvoice);

    void delete(UUID id);

    void clear();

}

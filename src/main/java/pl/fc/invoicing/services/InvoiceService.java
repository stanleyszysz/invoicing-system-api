package pl.fc.invoicing.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import pl.fc.invoicing.dto.InvoiceDto;
import pl.fc.invoicing.dto.InvoiceToSaveDto;

public interface InvoiceService {

    InvoiceDto save(InvoiceToSaveDto invoice);

    Optional<InvoiceDto> getById(UUID id);

    List<InvoiceDto> getAll();

    InvoiceDto update(UUID id, InvoiceToSaveDto updatedInvoice);

    void delete(UUID id);

    void clear();

}

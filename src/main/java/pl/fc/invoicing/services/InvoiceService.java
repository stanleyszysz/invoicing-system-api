package pl.fc.invoicing.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import pl.fc.invoicing.dto.InvoiceDto;
import pl.fc.invoicing.dto.InvoiceListDto;
import pl.fc.invoicing.model.InvoiceEntry;

public interface InvoiceService {

    InvoiceDto save(InvoiceDto invoice);

    Optional<InvoiceDto> getById(UUID id);

    List<InvoiceListDto> getAll();

    InvoiceDto update(UUID id, InvoiceDto updatedInvoice);

    void delete(UUID id);

    void clear();

    default BigDecimal visitStream(Predicate<InvoiceListDto> invoiceListDto, Function<InvoiceEntry, BigDecimal> invoiceEntryToAmount) {
        return getAll().stream()
            .filter(invoiceListDto)
            .flatMap(i -> i.getEntries().stream())
            .map(invoiceEntryToAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

package pl.fc.invoicing.services.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fc.invoicing.dto.InvoiceDto;
import pl.fc.invoicing.dto.InvoiceListDto;
import pl.fc.invoicing.model.Invoice;
import pl.fc.invoicing.repositories.InvoiceRepository;
import pl.fc.invoicing.services.InvoiceService;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public InvoiceDto save(InvoiceDto invoice) {
        Invoice invoiceModel = new Invoice(invoice);
        Invoice savedInvoice = invoiceRepository.save(invoiceModel);
        return new InvoiceDto(savedInvoice);
    }

    @Override
    public Optional<InvoiceDto> getById(UUID id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(new InvoiceDto(invoice.get()));
        }
    }

    @Override
    public List<InvoiceListDto> getAll() {
        return invoiceRepository.findAll().stream().map(item ->
            InvoiceListDto.builder()
                // .dateAt(item.getDateAt())
                .number(item.getNumber())
                .build()).collect(Collectors.toList());
    }

    @Override
    public InvoiceDto update(UUID id, InvoiceDto updatedInvoice) {
        if (invoiceRepository.findById(id).isPresent()) {
            updatedInvoice.setInvoiceId(id);
            Invoice invoiceModel = new Invoice(updatedInvoice);
            invoiceRepository.save(invoiceModel);
            return updatedInvoice;
        } else {
            throw new NoSuchElementException("Invoice with id: " + id + " doesn't exist.");
        }
    }

    @Override
    public void delete(UUID id) {
        if (invoiceRepository.findById(id).isPresent()) {
            invoiceRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Invoice with id: " + id + " doesn't exist.");
        }
    }
}

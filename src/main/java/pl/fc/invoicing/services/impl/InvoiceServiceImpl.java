package pl.fc.invoicing.services.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fc.invoicing.dto.InvoiceDto;
import pl.fc.invoicing.dto.mappers.InvoiceMapper;
import pl.fc.invoicing.exceptions.handlers.IdNotFoundException;
import pl.fc.invoicing.model.Invoice;
import pl.fc.invoicing.repositories.InvoiceRepository;
import pl.fc.invoicing.services.InvoiceService;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper invoiceMapper;

    @Override
    public InvoiceDto save(InvoiceDto invoiceDto) {
        Invoice invoiceModel = invoiceMapper.toEntity(invoiceDto);
        invoiceModel.updateRelations();
        Invoice savedInvoice = invoiceRepository.save(invoiceModel);
        return invoiceMapper.toDto(savedInvoice);
    }

    @Override
    public Optional<InvoiceDto> getById(UUID id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            InvoiceDto foundInvoiceDto = invoiceMapper.toDto(invoice.get());
            return Optional.of(foundInvoiceDto);
        } else {
            throw new IdNotFoundException("Invoice id: " + id + " not found.");
        }
    }

    @Override
    public List<InvoiceDto> getAll() {
        return invoiceRepository.findAll().stream().map(item ->
            InvoiceDto.builder()
                .dateAt(item.getDateAt())
                .number(item.getNumber())
                .seller(item.getSeller())
                .buyer(item.getBuyer())
                .entries(item.getEntries())
                .build()).collect(Collectors.toList());
    }

    @Override
    public InvoiceDto update(UUID id, InvoiceDto updatedInvoice) {
        if (invoiceRepository.findById(id).isPresent()) {
            Invoice invoice = invoiceMapper.toEntity(updatedInvoice);
            invoice.updateRelations();
            invoiceRepository.save(invoice);
            return updatedInvoice;
        } else {
            throw new NoSuchElementException("Invoice with id: " + id + " doesn't exist.");
        }
    }

    @Override
    public void delete(UUID id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public void clear() {
        invoiceRepository.deleteAll();
    }
}

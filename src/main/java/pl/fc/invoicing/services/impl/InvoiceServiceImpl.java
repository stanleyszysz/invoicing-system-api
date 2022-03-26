package pl.fc.invoicing.services.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public InvoiceDto save(InvoiceDto invoiceDto) {
        Invoice invoiceModel = modelMapper.map(invoiceDto, Invoice.class);
        invoiceModel.updateRelations();
        Invoice savedInvoice = invoiceRepository.save(invoiceModel);
        return modelMapper.map(savedInvoice, InvoiceDto.class);
    }

    @Override
    public Optional<InvoiceDto> getById(UUID id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty()) {
            return Optional.empty();
        } else {
            InvoiceDto foundInvoiceDto = modelMapper.map(invoice.get(), InvoiceDto.class);
            return Optional.of(foundInvoiceDto);
        }
    }

    @Override
    public List<InvoiceListDto> getAll() {
        return invoiceRepository.findAll().stream().map(item ->
            InvoiceListDto.builder()
                .invoiceId(item.getInvoiceId())
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
            Invoice invoice = invoiceRepository.findById(id).get();
            invoice.updateFromDto(updatedInvoice);
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

package pl.fc.invoicing.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fc.invoicing.dto.InvoiceDto;
import pl.fc.invoicing.dto.InvoiceToSaveDto;
import pl.fc.invoicing.dto.mappers.InvoiceMapper;
import pl.fc.invoicing.exceptions.handlers.IdNotFoundException;
import pl.fc.invoicing.model.Company;
import pl.fc.invoicing.model.Invoice;
import pl.fc.invoicing.repositories.CompanyRepository;
import pl.fc.invoicing.repositories.InvoiceRepository;
import pl.fc.invoicing.services.InvoiceService;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper invoiceMapper;

    private final CompanyRepository companyRepository;

    private Boolean checkIfCompaniesExist(String sellerTaxIdentifier, String buyerTaxIdentifier) {
        return companyRepository.findByTaxIdentifier(sellerTaxIdentifier).isPresent() && companyRepository.findByTaxIdentifier(buyerTaxIdentifier).isPresent();
    }

    @Override
    public InvoiceDto save(InvoiceToSaveDto invoiceDto) {
        Invoice savedInvoice = null;
        if (checkIfCompaniesExist(invoiceDto.getSellerTaxIdentifier(), invoiceDto.getBuyerTaxIdentifier())) {
            Company companySeller = companyRepository.findByTaxIdentifier(invoiceDto.getSellerTaxIdentifier()).get(); // sprawdzic jak z optionalem??
            Company companyBuyer = companyRepository.findByTaxIdentifier(invoiceDto.getBuyerTaxIdentifier()).get();
            InvoiceDto invoiceDto2 = new InvoiceDto();
            invoiceDto2.setNumber(invoiceDto.getNumber());
            invoiceDto2.setDateAt(invoiceDto.getDateAt());
            invoiceDto2.setSeller(companySeller);
            System.out.println("companySeller:");
            System.out.println(companySeller);
            invoiceDto2.setBuyer(companyBuyer);
            invoiceDto2.setEntries(invoiceDto.getEntries());
            Invoice invoiceModel = invoiceMapper.toEntity(invoiceDto2);
            invoiceModel.updateRelations();
            savedInvoice = invoiceRepository.save(invoiceModel);
            // return invoiceMapper.toDto(savedInvoice);
        } else {
            return null; // throw Exception
        }
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
                .invoiceId(item.getInvoiceId())
                .dateAt(item.getDateAt())
                .number(item.getNumber())
                .seller(item.getSeller())
                .buyer(item.getBuyer())
                .entries(item.getEntries())
                .build()).collect(Collectors.toList());
    }

    @Override
    public InvoiceDto update(UUID id, InvoiceToSaveDto invoiceToSaveDto) {
        if (invoiceRepository.findById(id).isPresent()) {
            Invoice invoiceToUpdate = invoiceRepository.findById(id).get();
            Company companySeller = companyRepository.findByTaxIdentifier(invoiceToSaveDto.getSellerTaxIdentifier()).get();
            Company companyBuyer = companyRepository.findByTaxIdentifier(invoiceToSaveDto.getBuyerTaxIdentifier()).get();
            // InvoiceDto invoiceDto2 = new InvoiceDto();
            invoiceToUpdate.setNumber(invoiceToSaveDto.getNumber());
            invoiceToUpdate.setDateAt(invoiceToSaveDto.getDateAt());
            invoiceToUpdate.setSeller(companySeller);
            invoiceToUpdate.setBuyer(companyBuyer);
            invoiceToUpdate.setEntries(invoiceToSaveDto.getEntries());
            // Invoice invoice = invoiceMapper.toEntity(invoiceDto2);
            // Invoice invoice = invoiceMapper.toEntity(invoiceDto2);
            invoiceToUpdate.updateRelations();
            Invoice updatedInvoice = invoiceRepository.save(invoiceToUpdate);
            return invoiceMapper.toDto(updatedInvoice);
        } else {
            throw new IdNotFoundException("Invoice id: " + id + " not found.");
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

package pl.fc.invoicing.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fc.invoicing.dto.InvoiceDto;
import pl.fc.invoicing.dto.InvoiceListDto;
import pl.fc.invoicing.dto.InvoiceToSaveDto;
import pl.fc.invoicing.exceptions.handlers.IdNotFoundException;
import pl.fc.invoicing.services.InvoiceService;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(path = "/invoice", produces = {"application/json;charset=UTF-8"})
// @Transactional
@RequiredArgsConstructor
public class InvoiceController implements InvoiceControllerApi {

    @Autowired
    private final InvoiceService invoiceService;

    @Override
    public ResponseEntity<InvoiceDto> save(@RequestBody InvoiceToSaveDto invoice) {
        log.debug("Adding new invoice");
        return ResponseEntity.ok(invoiceService.save(invoice));
    }

    @Override
    public ResponseEntity<Optional<InvoiceDto>> getById(@PathVariable UUID id) {
        try {
            log.debug("Getting invoice by id: " + id);
            return ResponseEntity.ok(invoiceService.getById(id));
        } catch (Exception e) {
            throw new IdNotFoundException("Invoice id: " + id + " not found.");
        }

    }

    @Override
    public ResponseEntity<List<InvoiceListDto>> getAll() {
        log.debug("Getting list of all invoice");
        return ResponseEntity.ok(invoiceService.getAll().stream().map(invoiceDto ->
            InvoiceListDto.of(invoiceDto)).toList());
    }

    @Override
    public ResponseEntity<InvoiceDto> update(@PathVariable UUID id, @RequestBody InvoiceToSaveDto updatedInvoice) {
        log.debug("Updating invoice by id: " + id);
        return ResponseEntity.ok(invoiceService.update(id, updatedInvoice));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (invoiceService.getById(id).isPresent()) {
            invoiceService.delete(id);
            log.debug("Deleting invoice by id: " + id);
            return ResponseEntity.status(204).build();
        } else {
            throw new IdNotFoundException("Company id: " + id + " not found.");
        }
    }

    @Override
    public ResponseEntity<Void> deleteAll() {
        invoiceService.clear();
        return ResponseEntity.noContent().build();
    }
}

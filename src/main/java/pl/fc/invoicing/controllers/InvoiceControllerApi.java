package pl.fc.invoicing.controllers;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.fc.invoicing.dto.InvoiceDto;
import pl.fc.invoicing.dto.InvoiceListDto;
import pl.fc.invoicing.dto.InvoiceToSaveDto;

public interface InvoiceControllerApi {

    @PostMapping
    @Operation(summary = "Save invoice", description = "Save new invoice")
    ResponseEntity<InvoiceDto> save(@RequestBody InvoiceToSaveDto invoice);

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get invoice by id", description = "Get invoice selected by id")
    ResponseEntity<Optional<InvoiceDto>> getById(@PathVariable UUID id);

    @GetMapping
    @Operation(summary = "Get list of all invoices", description = "Get list of all invoices")
    ResponseEntity<List<InvoiceListDto>> getAll();

    @PatchMapping(path = "/{id}")
    @Operation(summary = "Update invoice", description = "Update invoice selected by id")
    ResponseEntity<InvoiceDto> update(@PathVariable UUID id, @RequestBody InvoiceToSaveDto updatedInvoice);

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete invoice", description = "Delete invoice selected by id")
    ResponseEntity<Void> delete(@PathVariable UUID id);

    @GetMapping(path = "/deleteAll")
    @Operation(summary = "Delete all invoice", description = "Delete all invoice")
    ResponseEntity<Void> deleteAll();
}

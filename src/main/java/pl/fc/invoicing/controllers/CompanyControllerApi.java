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
import pl.fc.invoicing.dto.CompanyDto;
import pl.fc.invoicing.dto.CompanyListDto;

public interface CompanyControllerApi {

    @PostMapping
    @Operation(summary = "Save company", description = "Save new company")
    ResponseEntity<CompanyDto> save(@RequestBody CompanyDto company);

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get company by id", description = "Get company selected by id")
    ResponseEntity<Optional<CompanyDto>> getById(@PathVariable UUID id);

    @GetMapping
    @Operation(summary = "Get list of all companies", description = "Get list of all companies")
    ResponseEntity<List<CompanyListDto>> getAll();

    @PatchMapping(path = "/{id}")
    @Operation(summary = "Update company", description = "Update company selected by id")
    ResponseEntity<CompanyDto> update(@PathVariable UUID id, @RequestBody CompanyDto updatedCompany);

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete company", description = "Delete company selected by id")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}

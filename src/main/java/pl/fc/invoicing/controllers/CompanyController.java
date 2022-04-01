package pl.fc.invoicing.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fc.invoicing.dto.CompanyDto;
import pl.fc.invoicing.dto.CompanyListDto;
import pl.fc.invoicing.exceptions.handlers.IdNotFoundException;
import pl.fc.invoicing.services.CompanyService;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(path = "/company", produces = {"application/json;charset=UTF-8"})
@RequiredArgsConstructor
public class CompanyController implements CompanyControllerApi {

    private final CompanyService companyService;

    @Override
    public ResponseEntity<CompanyDto> save(@RequestBody CompanyDto company) {
        log.debug("Adding new invoice");
        return ResponseEntity.ok(companyService.save(company));
    }

    @Override
    public ResponseEntity<Optional<CompanyDto>> getById(@PathVariable UUID id) {
        try {
            log.debug("Getting company by id: " + id);
            return ResponseEntity.ok(companyService.getById(id));
        } catch (Exception e) {
            throw new IdNotFoundException("Company id: " + id + " not found.");
        }
    }

    @Override
    public ResponseEntity<List<CompanyListDto>> getAll() {
        log.debug("Getting list of all invoice");
        return ResponseEntity.ok(companyService.getAll());
    }

    @Override
    public ResponseEntity<CompanyDto> update(@PathVariable UUID id, @RequestBody CompanyDto updatedCompany) {
        log.debug("Updating invoice by id: " + id);
        return ResponseEntity.ok(companyService.update(id, updatedCompany));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws IdNotFoundException {
        if (companyService.getById(id).isPresent()) {
            companyService.delete(id);
            log.debug("Deleting invoice by id: " + id);
            return ResponseEntity.status(204).build();
        } else {
            throw new IdNotFoundException("Company id: " + id + " not found.");
        }
    }

    @Override
    public ResponseEntity<Void> deleteAll() {
        companyService.clear();
        return ResponseEntity.noContent().build();
    }
}

package pl.fc.invoicing.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fc.invoicing.dto.CompanyDto;
import pl.fc.invoicing.dto.CompanyListDto;
import pl.fc.invoicing.services.CompanyService;

@Slf4j
@RestController
@RequestMapping(path = "/company", produces = {"application/json;charset=UTF-8"})
@RequiredArgsConstructor
public class CompanyContoller implements CompanyControllerApi {

    private final CompanyService companyService;

    @Override
    public ResponseEntity<CompanyDto> save(@RequestBody CompanyDto company) {
        log.debug("Adding new invoice");
        return ResponseEntity.ok(companyService.save(company));
    }

    @Override
    public ResponseEntity<Optional<CompanyDto>> getById(@PathVariable UUID id) {
        log.debug("Getting invoice by id: " + id);
        return ResponseEntity.ok(companyService.getById(id));
    }

    @Override
    //    public ResponseEntity<List<CompanyListDto>> list(@RequestParam Pageable pageable) {
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
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            companyService.delete(id);
        } catch (NoSuchElementException e) {
            log.debug("Deleting invoice by id: " + id);
            return ResponseEntity.status(204).build();
        }
        log.debug("Cannot delete invoice by id: " + id);
        return ResponseEntity.noContent().build();
    }
}

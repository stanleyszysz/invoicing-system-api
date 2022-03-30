package pl.fc.invoicing.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fc.invoicing.dto.CompanyDto;
import pl.fc.invoicing.model.TaxCalculatorResult;
import pl.fc.invoicing.services.TaxCalculatorService;

@RequestMapping(path = "/tax", produces = {"application/json;charset=UTF-8"})
@RestController
@RequiredArgsConstructor
@Tag(name = "Controller for tax calculator")
public class TaxCalculatorController implements TaxCalculatorControllerApi {

    private final TaxCalculatorService taxCalculatorService;

    @Override
    public ResponseEntity<TaxCalculatorResult> taxCalculatorResult(@RequestBody CompanyDto companyDto) {
        return ResponseEntity.ok().body(taxCalculatorService.taxCalculatorResult(companyDto));
    }
}

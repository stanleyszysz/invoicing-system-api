package pl.fc.invoicing.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.fc.invoicing.dto.CompanyDto;
import pl.fc.invoicing.model.TaxCalculatorResult;

public interface TaxCalculatorControllerApi {

    @PostMapping
    @Operation(summary = "Tax calculation", description = "Calculation of taxes for the company")
    ResponseEntity<TaxCalculatorResult> taxCalculatorResult(@RequestBody CompanyDto companyDto);
}

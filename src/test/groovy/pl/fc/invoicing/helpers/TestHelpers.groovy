package pl.fc.invoicing.helpers

import org.modelmapper.ModelMapper
import pl.fc.invoicing.dto.CompanyDto
import pl.fc.invoicing.dto.InvoiceDto
import pl.fc.invoicing.model.*

import java.time.LocalDate

class TestHelpers {

    private static ModelMapper modelMapper = new ModelMapper()

    static address(int id) {
        Address.builder()
                .city("Wroclaw $id")
                .postalCode("99-99$id")
                .streetName("Słonecznikowa")
                .streetNumber("$id")
                .build()
    }

    static car(int id, boolean personalUsage) {
        Car.builder()
                .registrationNumber("DW 5G88$id")
                .personalUsage(personalUsage)
                .build()
    }

    static companyDto(int id) {
        CompanyDto.builder()
                .taxIdentifier("555555555$id")
                .name("Abra $id")
                .address(address(id))
                .pensionInsurance(BigDecimal.valueOf(1000) * BigDecimal.valueOf(id))
                .healthInsurance(BigDecimal.valueOf(500) * BigDecimal.valueOf(id))
                .build()
    }

    static invoiceEntry(int id) {
        InvoiceEntry.builder()
                .description("Product $id")
                .price(BigDecimal.valueOf(id * 1000))
                .vatValue(BigDecimal.valueOf(id * 1000 * 0.23))
                 .vatRate(Vat.VAT_23)
                .carRelatedExpense(car(1, false))
                .build()
    }

    static invoiceDto(int id) {
        InvoiceDto.builder()
                .dateAt(LocalDate.now())
                .number("FA/$id")
                .seller(modelMapper.map(companyDto(id), Company.class))
                .buyer(modelMapper.map(companyDto(id + 1), Company.class))
                .entries((1..5).collect({ invoiceEntry(it) }))
                .build()
    }
}

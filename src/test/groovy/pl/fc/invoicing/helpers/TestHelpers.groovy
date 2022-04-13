package pl.fc.invoicing.helpers

import org.modelmapper.ModelMapper
import pl.fc.invoicing.dto.CompanyDto
import pl.fc.invoicing.dto.InvoiceDto
import pl.fc.invoicing.dto.InvoiceToSaveDto
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

    static invoiceToSaveDto(int id, String sellerTaxIdentifier, String buyerTaxIdentifier) {
        InvoiceToSaveDto.builder()
                .dateAt(LocalDate.now())
                .number("FA/$id")
                .sellerTaxIdentifier(sellerTaxIdentifier)
                .buyerTaxIdentifier(buyerTaxIdentifier)
                .entries((1..5).collect({ invoiceEntry(it) }))
                .build()
    }

    // static invoiceDto(int id) {
    //     InvoiceDto.builder()
    //             .dateAt(LocalDate.now())
    //             .number("FA/$id")
    //             .seller(modelMapper.map(companyDto(id), Company.class))
    //             .buyer(modelMapper.map(companyDto(id + 1), Company.class))
    //             .entries((1..5).collect({ invoiceEntry(it) }))
    //             .build()
    // }

    static checkIfAllFieldsNotNull(InvoiceDto invoiceDto) {
        return invoiceDto.getInvoiceId() != null && invoiceDto.getDateAt() != null && invoiceDto.getNumber() != null && checkIfAllFieldsNotNull(invoiceDto.getSeller()) != null && checkIfAllFieldsNotNull(invoiceDto.getBuyer()) != null && checkIfAllFieldsNotNull(invoiceDto.getEntries()) != null
    }

    static checkIfAllInvoicesFieldsNotNull(List<InvoiceDto> invoiceDtos) {
        for (invoice in invoiceDtos) {
            if (!checkIfAllFieldsNotNull(invoice)) {
                return false
            }
        }
        return true
    }

    static checkIfAllFieldsNotNull(InvoiceEntry invoiceEntry) {
        return invoiceEntry.getInvoiceEntryId() != null && invoiceEntry.getDescription() != null && invoiceEntry.getPrice() != null && invoiceEntry.getVatValue() != null && invoiceEntry.getVatRate() != null && checkIfAllFieldsNotNull(invoiceEntry.getCarRelatedExpense()) != null
    }

    static checkIfAllFieldsNotNull(List<InvoiceEntry> invoiceEntries) {
        for (entry in invoiceEntries) {
            if (!checkIfAllFieldsNotNull(entry)) {
                return false
            }
        }
        return true
    }

    static checkIfAllFieldsNotNull(Company company) {
        return company.getCompanyId() != null && company.getTaxIdentifier() != null && company.getName() != null && checkIfAllFieldsNotNull(company.getAddress()) && company.getPensionInsurance() != null && company.getHealthInsurance() != null
    }

    static checkIfAllFieldsNotNull(Address address) {
        return address.getAddressId() != null && address.getCity() != null && address.getPostalCode() != null && address.getStreetName() != null && address.getStreetNumber() != null
    }

    static checkIfAllFieldsNotNull(Car car) {
        return car.getCarId() != null && car.getRegistrationNumber() != null && car.isPersonalUsage() != null
    }
}

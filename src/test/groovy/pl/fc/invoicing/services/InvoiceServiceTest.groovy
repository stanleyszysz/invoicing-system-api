package pl.fc.invoicing.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.fc.invoicing.dto.InvoiceDto
import pl.fc.invoicing.dto.InvoiceToSaveDto
import pl.fc.invoicing.helpers.TestHelpers
import pl.fc.invoicing.model.InvoiceEntry
import pl.fc.invoicing.model.Vat
import pl.fc.invoicing.model.Address
import pl.fc.invoicing.model.Car
import pl.fc.invoicing.dto.CompanyDto
import pl.fc.invoicing.model.Company
import pl.fc.invoicing.repositories.*
import spock.lang.Specification

@SpringBootTest
class InvoiceServiceTest extends Specification {

    @Autowired
    InvoiceService invoiceService

    @Autowired
    CompanyService companyService

    private List<CompanyDto> companyDtos = new ArrayList<>()
    private List<InvoiceToSaveDto> invoiceToSaveDtos = new ArrayList<>()
    private List<InvoiceDto> invoiceSavedDtos = new ArrayList<>()

    def setup() {
        invoiceService.clear()
        companyService.clear()
        companyDtos.clear()
        invoiceToSaveDtos.clear()
        invoiceSavedDtos.clear()
        for (int i = 1; i <= 6; i++) {
            def company = TestHelpers.companyDto(i)
            companyDtos.add(company)
            companyService.save(company)
        }
        for (int i = 1; i <= 5; i = i + 2) {
            def invoice = TestHelpers.invoiceToSaveDto(i, companyDtos[i - 1].getTaxIdentifier(), companyDtos[i].getTaxIdentifier())
            invoiceToSaveDtos.add(invoice)
            def invoiceSavedDto = invoiceService.save(invoice)
            invoiceSavedDtos.add(invoiceSavedDto)
            
        }
    }

    def "Save"() {
        expect:
        invoiceService.getAll().size() == 3
        TestHelpers.checkIfAllInvoicesFieldsNotNull(invoiceSavedDtos)
    }

    def "GetById"() {
        expect:
        invoiceService.getById(invoiceSavedDtos[0].getInvoiceId()).get().getBuyer().getName() == "Abra 2"
    }

    def "GetAll"() {
        expect:
        invoiceService.getAll().size() == 3
    }

    def "Update"() {
        when:
        def savedInvoiceDto = invoiceSavedDtos[0]
        UUID invoiceId = savedInvoiceDto.getInvoiceId()
        InvoiceToSaveDto invoiceToUpdate = InvoiceDto.of(savedInvoiceDto)
        invoiceToUpdate.setNumber("FA/15")
        def invoiceToUpdateEntries = invoiceToUpdate.getEntries()
        invoiceToUpdateEntries[0].setDescription("FirstProduct")
        invoiceToUpdate.setEntries(invoiceToUpdateEntries)
        invoiceService.update(invoiceId, invoiceToUpdate)

        then:
        invoiceService.getById(invoiceId).get().getNumber() == "FA/15"
        invoiceService.getById(invoiceId).get().getEntries()[0].getDescription() == "FirstProduct"

    }

    def "Delete"() {
        when:
        invoiceService.delete(invoiceSavedDtos[1].getInvoiceId())

        then:
        invoiceService.getAll().size() == 2
    }
}

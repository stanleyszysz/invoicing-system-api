package pl.fc.invoicing.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.fc.invoicing.dto.InvoiceDto
import pl.fc.invoicing.helpers.TestHelpers
import spock.lang.Specification

@SpringBootTest
class InvoiceServiceTest extends Specification {

    @Autowired
    InvoiceService invoiceService

    private InvoiceDto invoiceDto1 = TestHelpers.invoiceDto(1)
    private InvoiceDto invoiceDto2 = TestHelpers.invoiceDto(3)
    private InvoiceDto invoiceDto3 = TestHelpers.invoiceDto(5)

    def setup() {
        invoiceService.clear()
    }


    def "Save"() {
        when:
        invoiceService.save(invoiceDto1)

        then:
        invoiceService.getAll().size() == 1
    }

    def "GetById"() {
        when:
        def savedInvoiceDto = invoiceService.save(invoiceDto1)

        then:
        def invoiceId = savedInvoiceDto.getInvoiceId()
        invoiceService.getById(invoiceId).get().getBuyer().getName() == "Abra 2"
    }

    def "GetAll"() {
        when:
        invoiceService.save(invoiceDto1)
        invoiceService.save(invoiceDto2)
        invoiceService.save(invoiceDto3)

        then:
        invoiceService.getAll().size() == 3
    }

    def "Update"() {
        when:
        def savedInvoiceDto = invoiceService.save(invoiceDto1)
        savedInvoiceDto.setNumber("FA/15")
        invoiceService.update(savedInvoiceDto.getInvoiceId(), savedInvoiceDto)

        then:
        invoiceService.getById(savedInvoiceDto.getInvoiceId()).get().getNumber() == "FA/15"
    }

    def "Delete"() {
        when:
        def savedInvoiceDto = invoiceService.save(invoiceDto1)
        def invoiceId = savedInvoiceDto.getInvoiceId()
        invoiceService.delete(invoiceId)

        then:
        invoiceService.getAll().size() == 0
    }
}

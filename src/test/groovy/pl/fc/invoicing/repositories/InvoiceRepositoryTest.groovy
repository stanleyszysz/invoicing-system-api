package pl.fc.invoicing.repositories

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.fc.invoicing.model.Address
import pl.fc.invoicing.model.Car
import pl.fc.invoicing.model.Company
import pl.fc.invoicing.model.Invoice
import pl.fc.invoicing.model.InvoiceEntry
import pl.fc.invoicing.model.Vat
import spock.lang.Specification

import java.time.LocalDate


@SpringBootTest
class InvoiceRepositoryTest extends Specification {

    @Autowired
    InvoiceRepository invoiceRepository

    def setup() {
        invoiceRepository.deleteAll()
    }

    def saveInvoice() {
        Address sellerAddress = new Address()
        sellerAddress.setCity("Wrocław")
        sellerAddress.setPostalCode("11-111")
        sellerAddress.setStreetName("Wrocławska")
        sellerAddress.setStreetNumber("10")

        Company seller = new Company()
        seller.setTaxIdentifier("1000000000")
        seller.setName("Seller 1")
        seller.setAddress(sellerAddress)

        Address buyerAddress = new Address()
        buyerAddress.setCity("Warszawa")
        buyerAddress.setPostalCode("22-222")
        buyerAddress.setStreetName("Warszawska")
        buyerAddress.setStreetNumber("20")


        Company buyer = new Company()
        buyer.setTaxIdentifier("2000000000")
        buyer.setName("Buyer 1")
        buyer.setAddress(buyerAddress)

        Car car = new Car()
        car.setRegistrationNumber("AA 11111")
        car.setPersonalUsage(true)

        Invoice invoice = new Invoice()

        InvoiceEntry invoiceEntry = new InvoiceEntry()
        invoiceEntry.setDescription("First entry")
        invoiceEntry.setPrice(100.00)
        invoiceEntry.setVatValue(23.00)
        invoiceEntry.setVatRate(Vat.VAT_23)
        invoiceEntry.setCarRelatedExpense(car)
        invoiceEntry.setInvoice(invoice)
        List<InvoiceEntry> invoiceEntries = new ArrayList<>()
        invoiceEntries.add(invoiceEntry)

        invoice.setDateAt(LocalDate.now())
        invoice.setNumber("FA/001/2022")
        invoice.setSeller(seller)
        invoice.setBuyer(buyer)

        invoice.setEntries(invoiceEntries)

        return invoiceRepository.save(invoice)
    }

    def "testSaveId"() {
        when:
        def invoice = saveInvoice()
        def invoiceToTest = invoiceRepository.findById(invoice.getInvoiceId())
        invoiceToTest.get().setNumber("FA/1000")
        def savedInvoice = invoiceRepository.save(invoiceToTest.get())

        then:
        invoiceToTest.get().getInvoiceId() == savedInvoice.getInvoiceId()

    }

    def "save"() {
        when:
        saveInvoice()

        then:
        invoiceRepository.findAll().size() == 1
    }

    def "getById"() {
        when:
        def invoice = saveInvoice()

        then:
        invoiceRepository.findById(invoice.getInvoiceId()).isPresent()
        invoiceRepository.findById(invoice.getInvoiceId()).get().getBuyer().getName() == "Buyer 1"
        invoiceRepository.findById(invoice.getInvoiceId()).get().getEntries().get(0).getCarRelatedExpense().getRegistrationNumber() == "AA 11111"
    }

    def "getAll"() {
        when:
        saveInvoice()

        then:
        invoiceRepository.findAll().size() == 1
    }

    def "delete"() {
        when:
        def invoice = saveInvoice()
        def invoiceId = invoice.getInvoiceId()
        invoiceRepository.deleteById(invoiceId)

        then:
        invoiceRepository.findAll().size() == 0
    }


}

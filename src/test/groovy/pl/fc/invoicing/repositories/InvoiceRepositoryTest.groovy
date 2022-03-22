package pl.fc.invoicing.repositories

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.fc.invoicing.model.Address
import pl.fc.invoicing.model.Company
import pl.fc.invoicing.model.Invoice
import pl.fc.invoicing.model.InvoiceEntry
import spock.lang.Specification

import java.time.LocalDate


@SpringBootTest
class InvoiceRepositoryTest extends Specification {

    @Autowired
    InvoiceRepository invoiceRepository

    def setup() {
        invoiceRepository.deleteAll()
    }

    def "save"() {
        given:

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

        Invoice invoice = new Invoice()

        InvoiceEntry invoiceEntry = new InvoiceEntry()
        invoiceEntry.setDescription("First entry")
        invoiceEntry.setPrice(100.00)
        invoiceEntry.setVatValue(23.00)
        invoiceEntry.setVatRate(0.23f)
        invoiceEntry.setInvoice(invoice)
        List<InvoiceEntry> invoiceEntries = new ArrayList<>()
        invoiceEntries.add(invoiceEntry)

//        invoice.setDateAt(LocalDate.now())
        invoice.setNumber("FA/001/2022")
        invoice.setSeller(seller)
        invoice.setBuyer(buyer)

        invoice.setEntries(invoiceEntries)

        invoiceRepository.save(invoice)
        var invoices = invoiceRepository.findAll()
        println "###################################################################"
        println invoices
    }

    def "getById"() {
        when:
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

        Invoice invoice = new Invoice()

        InvoiceEntry invoiceEntry = new InvoiceEntry()
        invoiceEntry.setDescription("First entry")
        invoiceEntry.setPrice(100.00)
        invoiceEntry.setVatValue(23.00)
        invoiceEntry.setVatRate(0.23f)
        invoiceEntry.setInvoice(invoice)
        List<InvoiceEntry> invoiceEntries = new ArrayList<>()
        invoiceEntries.add(invoiceEntry)

        invoice.setDateAt(LocalDate.now())
        invoice.setNumber("FA/001/2022")
        invoice.setSeller(seller)
        invoice.setBuyer(buyer)

        invoice.setEntries(invoiceEntries)

        invoiceRepository.save(invoice)

        then:
        invoiceRepository.findById(invoice.getInvoiceId()).isPresent()
    }
}

package pl.fc.invoicing.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.fc.invoicing.model.Address
import pl.fc.invoicing.model.Company
import pl.fc.invoicing.model.Invoice
import pl.fc.invoicing.model.InvoiceEntry
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class InvoiceControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    private ObjectMapper objectMapper = new ObjectMapper()

    def "Save"() {
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
        // invoice.setDateAt(LocalDate.parse("2020-01-08"))
        invoice.setNumber("FA/001/2022")
        invoice.setSeller(seller)
        invoice.setBuyer(buyer)

        InvoiceEntry invoiceEntry = new InvoiceEntry()

        invoiceEntry.setDescription("First entry")
        invoiceEntry.setPrice(100.00)
        invoiceEntry.setVatValue(23.00)
        invoiceEntry.setVatRate(0.23f)
        invoiceEntry.setInvoice(invoice)
        List<InvoiceEntry> invoiceEntries = new ArrayList<>()
        invoiceEntries.add(invoiceEntry)

        invoice.setEntries(invoiceEntries)

        String result = null

        try {
            var content = objectMapper.writeValueAsString(invoice)
            println "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
            println content
            result = mockMvc.perform(post("/invoice")
                    .content(objectMapper.writeValueAsString(invoice))
                    .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString()
        } catch (Exception e) {
            e.printStackTrace()
        }

        Invoice invoice1 = null
        try {
            invoice1 = objectMapper.readValue(result, Invoice.class)
        } catch (JsonProcessingException e) {
            e.printStackTrace()
        }

        println "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"
        println invoice1
    }
}

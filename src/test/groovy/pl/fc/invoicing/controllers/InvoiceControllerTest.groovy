package pl.fc.invoicing.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.fc.invoicing.dto.InvoiceDto
import pl.fc.invoicing.helpers.TestHelpers
import pl.fc.invoicing.model.Invoice
import pl.fc.invoicing.services.JsonService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class InvoiceControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private JsonService jsonService

    private InvoiceDto invoiceDto1 = TestHelpers.invoiceDto(1)
    private InvoiceDto invoiceDto2 = TestHelpers.invoiceDto(3)
    private InvoiceDto invoiceDto3 = TestHelpers.invoiceDto(5)

    def setup() {
            mockMvc.perform(get("/invoice/deleteAll"))
                    .andExpect(status().isNoContent())
                    .andReturn()
                    .response
                    .contentAsString

    }

    private InvoiceDto saveInvoiceDto(InvoiceDto invoiceDto) {
        String result = null

        try {
            result = mockMvc.perform(post("/invoice")
                    .content(jsonService.toJson(invoiceDto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString()
        } catch (Exception e) {
            e.printStackTrace()
        }

        InvoiceDto invoice1 = null
        try {
            invoice1 = jsonService.toObject(result, InvoiceDto)
        } catch (JsonProcessingException e) {
            e.printStackTrace()
        }
        return invoice1
    }

    private List<Invoice> getAllInvoices() {
        def result = mockMvc.perform(get("/invoice"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        return jsonService.toObject(result, Invoice[])
    }

    def "Save"() {
        given:
        def invoice1 = saveInvoiceDto(invoiceDto1)

        expect:
        invoice1.getNumber() == "FA/1"
        invoice1.getEntries().get(0).getCarRelatedExpense().getRegistrationNumber() == "DW 5G881"
    }

    def "GetById"() {
        given:
        def invoice1 = saveInvoiceDto(invoiceDto1)

        when:
        def result = mockMvc.perform(get("/invoice/" + invoice1.getInvoiceId()))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        then:
        result.contains("Abra 1")
    }

    def "GetById not found"() {
        when:
        def result = mockMvc.perform(get("/invoice/91d48c95-b90b-4d0b-9409-9ff158890bc4"))
                .andReturn()
                .response
                .contentAsString

        then:
        result.contains("Invoice id: 91d48c95-b90b-4d0b-9409-9ff158890bc4 not found.")
    }

    def "GetAll"() {
        given:
        saveInvoiceDto(invoiceDto1)
        saveInvoiceDto(invoiceDto2)
        saveInvoiceDto(invoiceDto3)

        expect:
        getAllInvoices().size() == 3
    }

    def "Update"() {
        given:
        def invoice1 = saveInvoiceDto(invoiceDto1)
        invoice1.setNumber("FA/15")

        when:
        def result = mockMvc.perform(patch("/invoice/" + invoice1.getInvoiceId())
                .content(jsonService.toJson(invoice1))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        then:
        result.contains("FA/15")
    }

    def "Delete"() {
        given:
        def invoice1 = saveInvoiceDto(invoiceDto1)

        when:
        mockMvc.perform(delete("/invoice/" + invoice1.getInvoiceId()))

        then:
        getAllInvoices().size() == 0
    }

    def "Return message when id not found"() {
        when:
        def result = mockMvc.perform(delete("/invoice/91d48c95-b90b-4d0b-9409-9ff158890bc4"))
                .andReturn()
                .response
                .contentAsString

        then:
        result.contains("Invoice id: 91d48c95-b90b-4d0b-9409-9ff158890bc4 not found.")
    }
}

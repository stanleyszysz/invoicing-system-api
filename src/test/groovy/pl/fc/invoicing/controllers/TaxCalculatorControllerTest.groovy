package pl.fc.invoicing.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.fc.invoicing.dto.CompanyDto
import pl.fc.invoicing.dto.InvoiceDto
import pl.fc.invoicing.helpers.TestHelpers
import pl.fc.invoicing.model.*
import pl.fc.invoicing.services.JsonService
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class TaxCalculatorControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private JsonService jsonService

    private ModelMapper modelMapper = new ModelMapper()

    private InvoiceDto invoiceDto1 = TestHelpers.invoiceDto(1)
    private InvoiceDto invoiceDto2 = TestHelpers.invoiceDto(3)
    private UUID id

    def setup() {
        mockMvc.perform(get("/invoice/deleteAll"))
                .andExpect(status().isNoContent())
                .andReturn()
                .response
                .contentAsString

        invoiceDto1 = saveInvoiceDto(invoiceDto1)
        invoiceDto2 = saveInvoiceDto(invoiceDto2)
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

        InvoiceDto invoice = null
        try {
            invoice = jsonService.toObject(result, InvoiceDto)
            id = invoice.getInvoiceId()
        } catch (JsonProcessingException e) {
            e.printStackTrace()
        }
        return invoice
    }

    private TaxCalculatorResult taxCalculatorResult(CompanyDto companyDto) {
        def result = mockMvc.perform(post("/tax")
                .content(jsonService.toJson(companyDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        jsonService.toObject(result, TaxCalculatorResult)
    }

    def "should returned zeros when company id is zero"() {
        when:
        def taxCalculating = taxCalculatorResult(TestHelpers.companyDto(0))

        then:
        taxCalculating.income == 0
        taxCalculating.costs == 0
        taxCalculating.incomeMinusCosts == 0
        taxCalculating.pensionInsurance == 0
        taxCalculating.incomeMinusCostsMinusPensionInsurance == 0
        taxCalculating.incomeMinusCostsMinusPensionInsuranceRounded == 0
        taxCalculating.incomeTax == 0.00
        taxCalculating.healthInsurancePaid == 0
        taxCalculating.healthInsuranceToDeduction == 0.00
        taxCalculating.incomeTaxMinusHealthInsurance == 0.0
        taxCalculating.finalIncomeTax == 0
        taxCalculating.incomingVat == 0
        taxCalculating.outgoingVat == 0
        taxCalculating.vatToPay == 0
    }

    def "should returned all results when seller tax identifier is matching"() {
        when:
        def taxCalculating = taxCalculatorResult(TestHelpers.companyDto(1))

        then:
        taxCalculating.income == 15000
        taxCalculating.costs == 0
        taxCalculating.incomeMinusCosts == 15000
        taxCalculating.pensionInsurance == 1000
        taxCalculating.incomeMinusCostsMinusPensionInsurance == 14000
        taxCalculating.incomeMinusCostsMinusPensionInsuranceRounded == 14000
        taxCalculating.incomeTax == 2660.00
        taxCalculating.healthInsurancePaid == 500
        taxCalculating.healthInsuranceToDeduction == 430.56
        taxCalculating.incomeTaxMinusHealthInsurance == 2229.44
        taxCalculating.finalIncomeTax == 2229
        taxCalculating.incomingVat == 3450
        taxCalculating.outgoingVat == 0
        taxCalculating.vatToPay == 3450
    }

    def "should returned all results when buyer tax identifier is matching"() {
        when:
        def taxCalculating = taxCalculatorResult(TestHelpers.companyDto(2))

        then:
        taxCalculating.income == 0
        taxCalculating.costs == 15000
        taxCalculating.incomeMinusCosts == -15000
        taxCalculating.pensionInsurance == 2000
        taxCalculating.incomeMinusCostsMinusPensionInsurance == -17000.0
        taxCalculating.incomeMinusCostsMinusPensionInsuranceRounded == -17000
        taxCalculating.incomeTax == -3230.0
        taxCalculating.healthInsurancePaid == 1000
        taxCalculating.healthInsuranceToDeduction == 861.11
        taxCalculating.incomeTaxMinusHealthInsurance == -4091.11
        taxCalculating.finalIncomeTax == -4091
        taxCalculating.incomingVat == 0
        taxCalculating.outgoingVat == 3450
        taxCalculating.vatToPay == -3450
    }

    def "tax calculation should be correctly when the car is used for private"() {
        given:
        def invoiceDto = InvoiceDto.builder()
                // .id(UUID.randomUUID())
                .dateAt(LocalDate.now())
                .seller(modelMapper.map(TestHelpers.companyDto(5), Company.class))
                .buyer(modelMapper.map(TestHelpers.companyDto(6), Company.class))
                .entries(List.of(
                        InvoiceEntry.builder()
                                .price(BigDecimal.valueOf(1000))
                                .vatValue(BigDecimal.valueOf(230))
                                .carRelatedExpense(
                                        Car.builder()
                                                .personalUsage(true)
                                                .build()
                                )
                                .build()
                ))
                .build()

        saveInvoiceDto(invoiceDto)

        when:
        def taxCalculating = taxCalculatorResult(modelMapper.map(invoiceDto.getBuyer(), CompanyDto.class))

        then:
        taxCalculating.income == 0
        taxCalculating.costs == 1115.00
        taxCalculating.incomeMinusCosts == -1115.00
        taxCalculating.incomingVat == 0
        taxCalculating.outgoingVat == 115.00
        taxCalculating.vatToPay == -115.00
    }

    def "tax settlement are executed correctly"() {
        given:
        def companyToTaxSettlement = CompanyDto.builder()
                .taxIdentifier("9999999999")
                .pensionInsurance(1000.00)
                .healthInsurance(500.00)
                .build()

        def salesInvoice = InvoiceDto.builder()
                .seller(modelMapper.map(companyToTaxSettlement, Company.class))
                .buyer(modelMapper.map(TestHelpers.companyDto(1), Company.class))
                .entries(List.of(TestHelpers.invoiceEntry(1)))
                .build()

        def purchaseInvoice = InvoiceDto.builder()
                .seller(modelMapper.map(TestHelpers.companyDto(2), Company.class))
                .buyer(modelMapper.map(companyToTaxSettlement, Company.class))
                .entries(List.of(TestHelpers.invoiceEntry(2)))
                .build()

        saveInvoiceDto(salesInvoice)
        saveInvoiceDto(purchaseInvoice)

        when:
        def taxCalculatorResponse = taxCalculatorResult(companyToTaxSettlement)

        then:
        with(taxCalculatorResponse) {
            income == 1000
            costs == 2000
            incomeMinusCosts == -1000
            pensionInsurance == 1000.00
            incomeMinusCostsMinusPensionInsurance == -2000
            incomeMinusCostsMinusPensionInsuranceRounded == -2000
            incomeTax == -380.00
            healthInsurancePaid == 500.00
            healthInsuranceToDeduction == 430.5556
            incomeTaxMinusHealthInsurance == -810.5556
            finalIncomeTax == -810

            incomingVat == 230.0
            outgoingVat == 460.0
            vatToPay == -230.0
        }
    }
}

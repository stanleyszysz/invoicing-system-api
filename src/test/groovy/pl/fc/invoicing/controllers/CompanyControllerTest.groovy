package pl.fc.invoicing.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.fc.invoicing.dto.CompanyDto
import pl.fc.invoicing.dto.CompanyListDto
import pl.fc.invoicing.helpers.TestHelpers
import pl.fc.invoicing.services.JsonService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private JsonService jsonService

    private CompanyDto companyDto = TestHelpers.companyDto(1)

    def setup() {
        mockMvc.perform(get("/company/deleteAll"))
                .andExpect(status().isNoContent())
                .andReturn()
                .response
                .contentAsString
    }

    private CompanyDto saveCompanyDto(CompanyDto companyDto) {
        String result = null

        try {
            result = mockMvc.perform(post("/company")
                    .content(jsonService.toJson(companyDto))
                    .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString()
        } catch (Exception e) {
            e.printStackTrace()
        }

        CompanyDto company1 = null
        try {
            company1 = jsonService.toObject(result, CompanyDto)
        } catch (JsonProcessingException e) {
            e.printStackTrace()
        }
        return company1
    }

    private List<CompanyListDto> getAllCompanies() {
        def result = mockMvc.perform(get("/company"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        return jsonService.toObject(result, CompanyDto[])
    }

    def "Save"() {
        given:
        def company = saveCompanyDto(companyDto)

        expect:
        company.getName() == "Abra 1"
    }

    def "GetById"() {
        given:
        def company = saveCompanyDto(companyDto)

        when:
        def result = mockMvc.perform(get("/company/" + company.getCompanyId()))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        then:
        result.contains("Abra 1")
    }

    def "GetAll"() {
        given:
        saveCompanyDto(companyDto)

        expect:
        getAllCompanies().size() == 1
    }

    def "Update"() {
        given:
        def company = saveCompanyDto(companyDto)
        company.setName("Company 1")

        when:
        def result = mockMvc.perform(patch("/company/" + company.getCompanyId())
                .content(jsonService.toJson(company))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        then:
        result.contains("Company 1")
    }

    def "Delete"() {
        given:
        def company = saveCompanyDto(companyDto)

        when:
        mockMvc.perform(delete("/company/" + company.getCompanyId()))

        then:
        getAllCompanies().size() == 0
    }
}

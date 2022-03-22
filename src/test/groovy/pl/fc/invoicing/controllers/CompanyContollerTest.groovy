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
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class CompanyContollerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    private ObjectMapper objectMapper = new ObjectMapper()

    def "Save"() {
        given:
        Address address = new Address()
        address.setCity("Wrocław")
        address.setPostalCode("11-111")
        address.setStreetName("Wrocławska")
        address.setStreetNumber("10")

        Company company = new Company()
        company.setTaxIdentifier("1000000111")
        company.setName("Seller company")
        company.setAddress(address)

        String result = null

        try {
            var content = objectMapper.writeValueAsString(company)
            println "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCc"
            println content
            result = mockMvc.perform(post("/company")
                    .content(objectMapper.writeValueAsString(company))
                    .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString()
        } catch (Exception e) {
            e.printStackTrace()
        }

        Company company1 = null
        try {
            company1 = objectMapper.readValue(result, Company.class)
        } catch (JsonProcessingException e) {
            e.printStackTrace()
        }

        println "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD"
        println company1
    }
}

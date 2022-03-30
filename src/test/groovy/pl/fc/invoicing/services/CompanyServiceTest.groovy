package pl.fc.invoicing.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.fc.invoicing.dto.CompanyDto
import pl.fc.invoicing.helpers.TestHelpers
import spock.lang.Specification

@SpringBootTest
class CompanyServiceTest extends Specification {

    @Autowired
    CompanyService companyService

    private CompanyDto companyDto = TestHelpers.companyDto(1)

    def setup() {
        companyService.clear()
    }

    def "Save"() {
        when:
        companyService.save(companyDto)

        then:
        companyService.getAll().size() == 1
    }

    def "GetById"() {
        when:
        def savedCompanyDto = companyService.save(companyDto)

        then:
        def companyId = savedCompanyDto.getCompanyId()
        companyService.getById(companyId).get().getName() == "Abra 1"
    }

    def "GetAll"() {
        when:
        companyService.save(companyDto)

        then:
        companyService.getAll().size() == 1
    }

    def "Update"() {
        when:
        def savedCompanyDto = companyService.save(companyDto)
        savedCompanyDto.setName("Company 10")
        companyService.update(savedCompanyDto.getCompanyId(), savedCompanyDto)

        then:
        companyService.getById(savedCompanyDto.getCompanyId()).get().getName() == "Company 10"
    }

    def "Delete"() {
        when:
        def savedCompanyDto = companyService.save(companyDto)
        def companyId = savedCompanyDto.getCompanyId()
        companyService.delete(companyId)

        then:
        companyService.getAll().size() == 0
    }
}

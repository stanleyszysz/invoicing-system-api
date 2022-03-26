package pl.fc.invoicing.repositories

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.fc.invoicing.model.Address
import pl.fc.invoicing.model.Company
import spock.lang.Specification

@SpringBootTest
class CompanyRepositoryTest extends Specification {

    @Autowired
    CompanyRepository companyRepository

    def setup() {
        companyRepository.deleteAll()
    }

    def saveCompany() {
        Address address = new Address()
        address.setCity("Wrocław")
        address.setPostalCode("11-222")
        address.setStreetName("Wrocławska")
        address.setStreetNumber("110")

        Company company = new Company()
        company.setTaxIdentifier("1100000000")
        company.setName("Company 10")
        company.setAddress(address)

        return companyRepository.save(company)
    }

    def "save"() {
        when:
        saveCompany()

        then:
        companyRepository.findAll().size() == 1
    }

    def "getById"() {
        when:
        def company = saveCompany()

        then:
        companyRepository.findById(company.getCompanyId()).isPresent()
        companyRepository.findById(company.getCompanyId()).get().getName() == "Company 10"
    }

    def "getAll"() {
        when:
        saveCompany()

        then:
        companyRepository.findAll().size() == 1
    }

    def "delete"() {
        when:
        def company = saveCompany()
        def companyId = company.getCompanyId()
        companyRepository.deleteById(companyId)

        then:
        companyRepository.findAll().size() == 0
    }

}

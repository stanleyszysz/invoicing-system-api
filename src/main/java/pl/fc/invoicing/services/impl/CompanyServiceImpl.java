package pl.fc.invoicing.services.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fc.invoicing.dto.CompanyDto;
import pl.fc.invoicing.dto.CompanyListDto;
import pl.fc.invoicing.exceptions.handlers.IdNotFoundException;
import pl.fc.invoicing.model.Company;
import pl.fc.invoicing.repositories.CompanyRepository;
import pl.fc.invoicing.services.CompanyService;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyDto save(CompanyDto company) {
        Company companyModel = Company.of(company);
        Company savedCompany = companyRepository.save(companyModel);
        return CompanyDto.of(savedCompany);
    }

    @Override
    public Optional<CompanyDto> getById(UUID id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            return Optional.of(CompanyDto.of(company.get()));
        } else {
            throw new IdNotFoundException("Company id: " + id + " not found.");
        }

    }

    @Override   
    public List<CompanyListDto> getAll() {
        return companyRepository.findAll().stream().map(item ->
            CompanyListDto.builder()
                .taxIdentifier(item.getTaxIdentifier())
                .name(item.getName())
                .build()).collect(Collectors.toList());
    }

    @Override
    public CompanyDto update(UUID id, CompanyDto updatedCompany) {
        if (companyRepository.findById(id).isPresent()) {
            updatedCompany.setCompanyId(id);
            Company companyModel = Company.of(updatedCompany);
            companyRepository.save(companyModel);
            return updatedCompany;
        } else {
            throw new NoSuchElementException("Company with id: " + id + " doesn't exist.");
        }
    }

    @Override
    public void delete(UUID id) {
        companyRepository.deleteById(id);
    }

    @Override
    public void clear() {
        companyRepository.deleteAll();
    }
}

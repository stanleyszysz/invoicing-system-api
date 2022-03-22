package pl.fc.invoicing.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import pl.fc.invoicing.dto.CompanyDto;
import pl.fc.invoicing.dto.CompanyListDto;

public interface CompanyService {

    CompanyDto save(CompanyDto company);

    Optional<CompanyDto> getById(UUID id);

    //    List<CompanyListDto> getAll(Pageable pageable);
    List<CompanyListDto> getAll();

    CompanyDto update(UUID id, CompanyDto updatedCompany);

    void delete(UUID id);
}

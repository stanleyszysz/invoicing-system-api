package pl.fc.invoicing.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.fc.invoicing.model.Company;

public interface CompanyRepository extends PagingAndSortingRepository<Company, UUID> {

    List<Company> findAll();
    // Company findByTaxIdentifier(String taxIdentifier);
    Optional<Company> findByTaxIdentifier(String taxIdentifier);
}


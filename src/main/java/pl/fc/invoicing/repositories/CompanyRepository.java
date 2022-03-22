package pl.fc.invoicing.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.fc.invoicing.model.Company;

public interface CompanyRepository extends PagingAndSortingRepository<Company, UUID> {

    List<Company> findAll();
}


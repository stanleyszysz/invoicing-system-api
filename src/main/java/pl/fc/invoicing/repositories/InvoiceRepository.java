package pl.fc.invoicing.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.fc.invoicing.model.Invoice;

public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, UUID> {

    List<Invoice> findAll();
}

package pl.fc.invoicing.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.fc.invoicing.dto.InvoiceDto;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "invoiceId", length = 16, updatable = false, nullable = false)
    @Schema(description = "Invoice id", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
    private UUID invoiceId;
    // @Schema(description = "Date of invoice", example = "2022-02-03", required = false)
    // private LocalDate dateAt;
    @Schema(description = "Invoice number", example = "2022/02/17/001", required = true)
    private String number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller")
    @Schema(description = "Seller", required = true)
    private Company seller;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buyer")
    @Schema(description = "Buyer", required = true)
    private Company buyer;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "invoice", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Schema(description = "Product name", required = true)
    private List<InvoiceEntry> entries;

    public Invoice(InvoiceDto invoiceDto) {
        this.invoiceId = invoiceDto.getInvoiceId();
        // this.dateAt = invoiceDto.getDateAt();
        this.number = invoiceDto.getNumber();
        this.seller = invoiceDto.getSeller();
        this.buyer = invoiceDto.getBuyer();
        this.entries = invoiceDto.getEntries();
    }

}


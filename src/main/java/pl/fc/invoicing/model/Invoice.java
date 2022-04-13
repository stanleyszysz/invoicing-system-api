package pl.fc.invoicing.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "invoice_id", length = 16, updatable = false, nullable = false)
    @Schema(description = "Invoice id", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
    private UUID invoiceId;
    @Basic
    @Schema(description = "Date of invoice", example = "2022-02-03", required = true)
    private LocalDate dateAt;
    @Column(unique = true)
    @Schema(description = "Invoice number", example = "2022/02/17/001", required = true)
    private String number;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "seller")
    @Schema(description = "Seller", required = true)
    private Company seller;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "buyer")
    @Schema(description = "Buyer", required = true)
    private Company buyer;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "invoice", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Schema(description = "Product name", required = true)
    private List<InvoiceEntry> entries;

    public void updateRelations() {
        if (entries != null) {
            entries.forEach(invoiceEntry -> invoiceEntry.setInvoice(this));
        }
    }

}

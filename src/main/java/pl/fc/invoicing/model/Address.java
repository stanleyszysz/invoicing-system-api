package pl.fc.invoicing.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "addressId", length = 16, updatable = false, nullable = false)
    @Schema(description = "Address id", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", required = true)
    private UUID addressId;
    @Schema(description = "City", example = "Solec", required = true)
    private String city;
    @Schema(description = "Postal Code", example = "05-532", required = true)
    private String postalCode;
    @Schema(description = "Street Name", example = "Słonecznikowa", required = true)
    private String streetName;
    @Schema(description = "Street Number", example = "8", required = true)
    private String streetNumber;

    @OneToOne(mappedBy = "address")
    @JsonBackReference
    @ToString.Exclude
    private Company company;
}

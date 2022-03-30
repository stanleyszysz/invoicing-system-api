package pl.fc.invoicing.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaxCalculatorResult {

    private final BigDecimal income;
    private final BigDecimal costs;
    private final BigDecimal incomeMinusCosts;
    private final BigDecimal pensionInsurance;
    private final BigDecimal incomeMinusCostsMinusPensionInsurance;
    private final BigDecimal incomeMinusCostsMinusPensionInsuranceRounded;
    private final BigDecimal incomeTax;
    private final BigDecimal healthInsurancePaid;
    private final BigDecimal healthInsuranceToDeduction;
    private final BigDecimal incomeTaxMinusHealthInsurance;
    private final BigDecimal finalIncomeTax;

    private final BigDecimal incomingVat;
    private final BigDecimal outgoingVat;
    private final BigDecimal vatToPay;
}

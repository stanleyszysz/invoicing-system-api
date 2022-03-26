package pl.fc.invoicing.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Vat {

    VAT_0(0),
    VAT_5(0.05f),
    VAT_8(0.08f),
    VAT_23(0.23f);

    public final float rate;
}

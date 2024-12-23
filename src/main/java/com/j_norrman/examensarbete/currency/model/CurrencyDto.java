package com.j_norrman.examensarbete.currency.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {
    private String currencyTypeName;
    private double chaosEquivalent;
    private String icon;
    private Long currencyId;
}

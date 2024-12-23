package com.j_norrman.examensarbete.currency.model;

import lombok.Data;

import java.util.List;

@Data
public class CurrencyResponse {
    private List<CurrencyLine> lines;
    private List<CurrencyDetail> currencyDetails;

    @Data
    public static class CurrencyLine {
        private double chaosEquivalent;
        private String currencyTypeName;
        private PayOrReceive pay;
        private PayOrReceive receive;
        private String detailsId;
    }

    @Data
    public static class PayOrReceive {
        private Long get_currency_id;
    }

    @Data
    public static class CurrencyDetail {
        private Long id;
        private String icon;
        private String name;
        private String tradeId;
    }
}

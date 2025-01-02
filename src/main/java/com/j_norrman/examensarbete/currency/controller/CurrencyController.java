package com.j_norrman.examensarbete.currency.controller;

import com.j_norrman.examensarbete.currency.model.Currency;
import com.j_norrman.examensarbete.currency.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/api/currency")
@Slf4j
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/store")
    public ResponseEntity<Void> fetchCurrencies(@RequestParam String league) {
        try {
            currencyService.fetchAndSaveCurrencies(league);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("Error while fetching or saving currencies for league: " + league);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/latest")
    public ResponseEntity<List<Currency>> getLatestCurrencies() {
        List<Currency> currencies = currencyService.getAllCurrencies();
        return ResponseEntity.ok(currencies);
    }
}

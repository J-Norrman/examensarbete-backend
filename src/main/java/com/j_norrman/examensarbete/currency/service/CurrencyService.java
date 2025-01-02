package com.j_norrman.examensarbete.currency.service;

import com.j_norrman.examensarbete.currency.model.Currency;
import com.j_norrman.examensarbete.currency.model.CurrencyDto;
import com.j_norrman.examensarbete.currency.model.CurrencyResponse;
import com.j_norrman.examensarbete.currency.repository.CurrencyRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }
    public List<CurrencyDto> fetchCurrencyData(String league) {
        String url = "https://poe.ninja/api/data/currencyoverview?league=" + league + "&type=Currency";
        System.out.println("Fetching currencies from: " + url);

        CurrencyResponse response = restTemplate.getForObject(url, CurrencyResponse.class);
        if (response.getLines() == null || response.getCurrencyDetails() == null) {
            System.out.println("No currency data found.");
            return List.of();
        }

        Map<String, CurrencyResponse.CurrencyDetail> detailMap = response.getCurrencyDetails().stream()
                .collect(Collectors.toMap(CurrencyResponse.CurrencyDetail::getName, detail -> detail));

        return response.getLines().stream()
                .map(line -> {
                    CurrencyResponse.CurrencyDetail detail = detailMap.get(line.getCurrencyTypeName());
                    if (detail == null) {
                        System.out.println("No matching details for: " + line.getCurrencyTypeName());
                        return null;
                    }
                    return new CurrencyDto(
                            line.getCurrencyTypeName(),
                            line.getChaosEquivalent(),
                            detail.getIcon(),
                            detail.getId()
                    );
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void saveCurrencies(List<CurrencyDto> currencies) {
        for (CurrencyDto dto : currencies) {
            if (dto.getCurrencyTypeName() == null) {
                log.warn("Skipping null currency name");
                continue;
            }
            Currency existingCurrency = currencyRepository.findByCurrencyTypeName(dto.getCurrencyTypeName());
            if (existingCurrency != null) {
                existingCurrency.setChaosEquivalent(dto.getChaosEquivalent());
                existingCurrency.setIcon(dto.getIcon());
                existingCurrency.setCurrencyId(dto.getCurrencyId());
                existingCurrency.setLastUpdated(LocalDateTime.now());
                log.info("Updating existing currency: {}", existingCurrency.getCurrencyTypeName());
                currencyRepository.save(existingCurrency);
            } else {
                Currency newCurrency = new Currency();
                newCurrency.setCurrencyTypeName(dto.getCurrencyTypeName());
                newCurrency.setChaosEquivalent(dto.getChaosEquivalent());
                newCurrency.setIcon(dto.getIcon());
                newCurrency.setCurrencyId(dto.getCurrencyId());
                newCurrency.setLastUpdated(LocalDateTime.now());
                log.info("Saving new currency: {}", newCurrency.getCurrencyTypeName());
                currencyRepository.save(newCurrency);
            }
        }
    }

    public void fetchAndSaveCurrencies(String league) {
        List<CurrencyDto> currencies = fetchCurrencyData(league);
        saveCurrencies(currencies);
        long count = currencyRepository.count();
        System.out.println("Total currencies in DB: " + count);
    }

    @Scheduled(fixedRate = 6000000) // cron = "0 0 0 * * ?"
    public void scheduledFetchAndStore() {
        String league = "Settlers";
        log.info("Scheduled task: Fetching and saving currencies for league: {}", league);
        try {
            fetchAndSaveCurrencies(league);
            log.info("Currencies updated successfully.");
        } catch (Exception e) {
            log.error("Error during scheduled fetch and save", e);
        }
    }
    @Transactional
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}

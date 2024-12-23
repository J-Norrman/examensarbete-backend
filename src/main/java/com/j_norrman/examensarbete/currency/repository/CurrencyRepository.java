package com.j_norrman.examensarbete.currency.repository;

import com.j_norrman.examensarbete.currency.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByCurrencyTypeName(String currencyTypeName);
}

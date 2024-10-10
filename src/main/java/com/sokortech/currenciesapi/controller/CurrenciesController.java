package com.sokortech.currenciesapi.controller;

import java.math.BigDecimal;
import java.util.List;
import com.sokortech.currenciesapi.dto.internal.CurrencyConversionDto;
import com.sokortech.currenciesapi.service.CurrenciesClient;
import com.sokortech.currenciesapi.service.RatesCalculatorService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currencies")
public class CurrenciesController {
    private final RatesCalculatorService ratesCalculatorService;

    public CurrenciesController(RatesCalculatorService ratesCalculatorService) {
        this.ratesCalculatorService = ratesCalculatorService;
    }

    @GetMapping("/convert")
    public List<CurrencyConversionDto> getConversions(
            @RequestParam BigDecimal amount,
            @RequestParam String baseCurrency,
            @RequestParam List<String> currencies
            ) {
        return ratesCalculatorService.getConversions(
                amount,
                baseCurrency,
                currencies);
    }
}

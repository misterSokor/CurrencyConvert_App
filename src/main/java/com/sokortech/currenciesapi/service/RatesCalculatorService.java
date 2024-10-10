package com.sokortech.currenciesapi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.sokortech.currenciesapi.dto.external.CurrencyRateObject;
import com.sokortech.currenciesapi.dto.internal.CurrencyConversionDto;
import org.springframework.stereotype.Service;

@Service
public class RatesCalculatorService {
    private final CurrenciesClient client;


    public RatesCalculatorService(CurrenciesClient client) {
        this.client = client;
    }

    public List<CurrencyConversionDto> getConversions(BigDecimal amount,
                                                      String baseCurrency,
                                                      List<String> currencies) {
        List<CurrencyRateObject> currenciesRates =
                client.getCurrenciesRate(baseCurrency, currencies);

        // using loop
        List<CurrencyConversionDto> dtoList = new ArrayList<>();
        for (CurrencyRateObject rate : currenciesRates) {
            CurrencyConversionDto currencyConversionDto = new CurrencyConversionDto(
                    baseCurrency,
                    rate.code(),
                    amount,
                    rate.value(),
                    rate.value().multiply(amount)
            );
            dtoList.add(currencyConversionDto);
        }

        // using lambda
        /*
                List<CurrencyConversionDto> dtoList = currenciesRates.stream()
                .map(rate -> new CurrencyConversionDto(
                        baseCurrency,
                        rate.code(),
                        amount,
                        rate.value(),
                        rate.value().multiply(amount)
                ))
                .toList();
         */

        return dtoList;

    }
}


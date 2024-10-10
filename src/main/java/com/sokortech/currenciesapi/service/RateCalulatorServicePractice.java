package com.sokortech.currenciesapi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.sokortech.currenciesapi.dto.external.CurrencyRateObject;
import com.sokortech.currenciesapi.dto.external.CurrencyResponseDataDto;
import com.sokortech.currenciesapi.dto.internal.CurrencyConversionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RateCalulatorServicePractice {

    private final CurrenciesClient client;

    public List<CurrencyConversionDto> getConversions(
            BigDecimal amount,
            String baseCurrency,
            List<String> currencies
    ) {
         List<CurrencyRateObject> objectList =
                client.getCurrenciesRate(baseCurrency, currencies);

         List<CurrencyConversionDto> dtoList = new ArrayList<>();
         for (CurrencyRateObject rate : objectList) {
             CurrencyConversionDto conversionDto =
                     new CurrencyConversionDto(
                             baseCurrency,
                             rate.code(),
                             amount,
                             rate.value(),
                             rate.value().multiply(amount)
                     );
             dtoList.add(conversionDto);
         }
        return dtoList;

    }
}

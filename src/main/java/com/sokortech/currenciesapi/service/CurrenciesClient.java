package com.sokortech.currenciesapi.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sokortech.currenciesapi.dto.external.CurrencyRateObject;
import com.sokortech.currenciesapi.dto.external.CurrencyResponseDataDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CurrenciesClient {
    // base url for the currency api
    private static final String BASE_URL =
            "https://api.currencyapi.com/v3/latest?apikey=%s&currencies=%s&base_currency=%s";
    // separator for currencies in the url
    private static final String CURRENCIES_SEPARATOR = "%2C";
    // object mapper for json serialization/deserialization
    private final ObjectMapper objectMapper;
    // api key for the currency api
    @Value("${com.sokortech.currencies.api.key}")
    private String apiKey;

    public CurrenciesClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // get the currencies rate method
    public List<CurrencyRateObject> getCurrenciesRate(
            String baseCurrencies,
            List<String> currencies
    ) {

        // 1 create a new http client
        HttpClient httpClient = HttpClient.newHttpClient();

        // 2 create the url + format;
        String url = BASE_URL.formatted(
                apiKey,
                String.join(CURRENCIES_SEPARATOR, currencies),
                baseCurrencies
        );

        // 3 create a new http request
        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        // 4 send the request and get the response
        try {
            HttpResponse<String> response = httpClient.send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());

            // 5 parse the response with objectMapper
            CurrencyResponseDataDto dataDto = objectMapper.readValue(
                    response.body(),
                    CurrencyResponseDataDto.class);

            // 6 get the currency rate objects
            // using loop chain
            /*
            List<CurrencyRateObject> currencyRateObjects = new ArrayList<>();
            for (CurrencyRateObject currencyRateObject : dataDto
                    .getData()
                    .values()) {
                currencyRateObjects.add(currencyRateObject);
            }
            */

            // using lambda
            List<CurrencyRateObject> currencyRateObjects = dataDto
                    .getData()
                    .values()
                    .stream()
                    .toList();

            // 7 return the currency rate objects
            return currencyRateObjects;

            // 8 handle exceptions (IOException, InterruptedException)
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}



/*

package com.sokortech.currenciesapi.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sokortech.currenciesapi.dto.external.CurrencyRateObject;
import com.sokortech.currenciesapi.dto.external.CurrencyResponseDataDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CurrenciesClient {
    // base url for the currency api
    private static final String BASE_URL =
            "https://api.currencyapi.com/v3/latest?apikey=%s&currencies=%s&base_currency=%s";

    // object mapper for json serialization/deserialization
    private final ObjectMapper objectMapper;

    public CurrenciesClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // separator for currencies in the url
    private static final String CURRENCIES_SEPARATOR = "%2C";

    // api key for the currency api
    @Value("${com.sokortech.currencies.api.key}")
    private String apiKey;

    // get the currencies rate method
    public List<CurrencyRateObject> getCurrenciesRate(
                            String baseCurrency,
                            List<String> currencies) {
        // 1 create a new http client
        HttpClient httpClient = HttpClient.newHttpClient();

        // 2 create the url + format;
        String url = BASE_URL.formatted(
                apiKey,
                String.join(CURRENCIES_SEPARATOR, currencies),
                baseCurrency);

        // 3 create a new http request
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        // 4 send the request and get the response
        try {
            HttpResponse<String> response = httpClient.send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());

            // 5 parse the response with objectMapper
            CurrencyResponseDataDto dataDto = objectMapper.readValue(
                    response.body(),
                    CurrencyResponseDataDto.class);

            // 6 get the currency rate objects
            List<CurrencyRateObject> currencyRateObjects = dataDto
                    .getData()
                    .values()
                    .stream()
                    .toList();
            // 7 return the currency rate objects
            return currencyRateObjects;

            // 8 handle exceptions (IOException, InterruptedException)
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


 */
package com.sokortech.currenciesapi.dto.external;

import java.math.BigDecimal;

public record CurrencyRateObject(String code, BigDecimal value) {
}

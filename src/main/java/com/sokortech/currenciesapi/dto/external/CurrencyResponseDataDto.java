package com.sokortech.currenciesapi.dto.external;

import java.util.Map;
import lombok.Data;

@Data
public class CurrencyResponseDataDto {
    private CurrencyMetadataDto meta;
    private Map<String, CurrencyRateObject> data;
}

package com.sokortech.currenciesapi.dto.external;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrencyMetadataDto {
    @JsonProperty("last_updated_at")
    private LocalDateTime lastUpdatedAt;
}

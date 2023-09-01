package com.company.app.core.tool.json;

import lombok.Builder;
import lombok.Data;

/**
 * Don't forget initialize by default objectMapper values.
 */
@Data
@Builder
public class MapperSettings {

    private boolean failOnUnknownProperties = true;
}

package com.company.app.common.tool.json;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * Don't forget initialize by default objectMapper values.
 */
@Getter
@Setter
@Accessors(chain = true)
public class MapperSettings {

    private boolean failOnUnknownProperties = true;

}

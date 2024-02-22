package com.company.app.common.selenium.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.devtools.v121.network.model.RequestId;


@Getter
@Setter
@Accessors(chain = true)
public class Response {

    private RequestId requestId;
    private String url;
    private String body;
    private String partOfUrl;

    public boolean isResponseReady(RequestId currentRequestId) {
        if (requestId == null) {
            return false;
        } else if (body != null) {
            return false;
        } else {
            try {
                BigDecimal bigDecimal1 = new BigDecimal(currentRequestId.toString());
                BigDecimal bigDecimal2 = new BigDecimal(requestId.toString());
                return bigDecimal1.compareTo(bigDecimal2) >= 0;
            } catch (Exception e) {
                return false;
            }
        }
    }

}
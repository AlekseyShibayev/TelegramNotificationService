package com.company.app.common.selenium.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Response {

    private Request request;
    private String fullUrl;

}
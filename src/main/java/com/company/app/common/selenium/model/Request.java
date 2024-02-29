package com.company.app.common.selenium.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class Request {

    private String entityView;
    private String url;
    private String partOfUrl;

}
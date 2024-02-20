package com.company.app.telegram.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class UserInfoDto {

    private String name;
    private String role;
    private String gender;

}

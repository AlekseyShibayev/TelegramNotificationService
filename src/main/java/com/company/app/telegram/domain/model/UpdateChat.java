package com.company.app.telegram.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UpdateChat {

    String chatName;
    String mode;

}

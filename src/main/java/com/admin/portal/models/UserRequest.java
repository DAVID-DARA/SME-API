package com.admin.portal.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRequest {

    @ApiModelProperty(example = "darafam")
    private String userId;
}

package com.admin.portal.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReassignmentRequest {

    @ApiModelProperty(example = "darafam")
    public String userId;

    @ApiModelProperty(example = "10")
    public Long request_Item_Number;
}

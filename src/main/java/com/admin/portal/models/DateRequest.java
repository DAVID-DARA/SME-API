package com.admin.portal.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DateRequest {
    @ApiModelProperty(example = "11/01/2023")
    private String startDate;
    @ApiModelProperty(example = "31/05/2023")
    private String endDate;
    @ApiModelProperty(example = "kk")
    private String userId;

}
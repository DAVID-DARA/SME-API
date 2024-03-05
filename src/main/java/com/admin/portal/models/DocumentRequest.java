package com.admin.portal.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DocumentRequest {

    @ApiModelProperty(example = "ID")
    private String docType;

    @ApiModelProperty(example = "2")
    private Long itemNo;
}

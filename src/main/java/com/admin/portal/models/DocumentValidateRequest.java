package com.admin.portal.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DocumentValidateRequest {

    @ApiModelProperty(example = "darafam")
    private String userId;

    @ApiModelProperty(example = "ID")
    private String docType;

    @ApiModelProperty(example = "3")
    private Long entryId;

    @ApiModelProperty(example = "APPROVE")
    private String approvalMessage;

    @ApiModelProperty(example = "Accurate Document")
    private String itemComment;
}
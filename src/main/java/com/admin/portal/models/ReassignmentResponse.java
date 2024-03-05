package com.admin.portal.models;

import lombok.Data;

@Data
public class ReassignmentResponse {

    private String userId;
    private Long itemNumber;
    private String message;
}

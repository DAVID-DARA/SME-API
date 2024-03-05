package com.admin.portal.models;

import lombok.Data;

@Data
public class RequestResponse {

    private Long itemNo;
    private String firstName;
    private String lastName;
    private String accountName;
    private String accountNumber;
    private String BusinessName;
    private String typeOfID;
    private String Request_Comment;
}
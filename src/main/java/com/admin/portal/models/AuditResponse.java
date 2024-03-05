package com.admin.portal.models;

import com.admin.portal.entities.Audit;
import lombok.Data;

@Data
public class AuditResponse {

    private String responseMessage;
    private Audit audit;

}
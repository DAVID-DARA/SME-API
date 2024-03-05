package com.admin.portal.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DocumentResponse {
    private String message;
    private List<?> documentForRequest;
}

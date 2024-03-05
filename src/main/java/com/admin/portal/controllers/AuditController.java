package com.admin.portal.controllers;

import com.admin.portal.entities.Audit;
import com.admin.portal.models.AuditResponse;
import com.admin.portal.models.DateRequest;
import com.admin.portal.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public List<Audit> findAllAudits () {
        return this.auditService.findAllAudits();
    }

    @GetMapping("/id/{id}")
    public AuditResponse findAuditByRowNumber (@PathVariable(name = "id") Long number) {
        return this.auditService.findAuditByRowNumber(number);
    }

    @PostMapping("/date-range")
    public List<Audit> findAuditByDateRange (@RequestBody DateRequest request) {
        return this.auditService.findAuditByDateRange(request);
    }

}
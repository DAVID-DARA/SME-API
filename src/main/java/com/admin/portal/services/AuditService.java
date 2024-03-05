package com.admin.portal.services;

import com.admin.portal.entities.Audit;
import com.admin.portal.entities.User;
import com.admin.portal.models.AuditResponse;
import com.admin.portal.models.DateRequest;
import com.admin.portal.repositories.AuditRepository;
import com.admin.portal.utilities.Action;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditRepository auditRepository;

    public String auditPersist (Action action, User user) {
        Audit audit = new Audit();

        try {
            audit.setDate(new Date());
            audit.setUser(user);
            audit.setAction(action);
            auditRepository.save(audit);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    public List<Audit> findAllAudits () {
        return this.auditRepository.findAll();
    }

    public AuditResponse findAuditByRowNumber (Long number) {
        AuditResponse response = new AuditResponse();
        Optional<Audit> auditOptional = this.auditRepository.findById(number);


        if (auditOptional.isPresent()) {
            response.setAudit(auditOptional.get());
            response.setResponseMessage("success");
        } else {
            response.setAudit(null);
            response.setResponseMessage("no such row found");
        }
        return response;
    }

    public List<Audit> findAuditByDateRange (DateRequest request) {
        String[] start = request.getStartDate().split("/");
        String[] end = request.getEndDate().split("/");
        LocalDate startLocalDate = LocalDate.of(Integer.parseInt(start[2]), Integer.parseInt(start[1]), Integer.parseInt(start[0]));
        Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate endLocalDate = LocalDate.of(Integer.parseInt(end[2]), Integer.parseInt(end[1]), Integer.parseInt(end[0]));
        Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return this.auditRepository.findByDateBetweenAndUserUserId(startDate, endDate, request.getUserId());
    }

}
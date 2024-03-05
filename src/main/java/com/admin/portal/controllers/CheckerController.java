package com.admin.portal.controllers;

import com.admin.portal.entities.Request;
import com.admin.portal.models.DocumentRequest;
import com.admin.portal.models.DocumentResponse;
import com.admin.portal.models.DocumentValidateRequest;
import com.admin.portal.models.DocumentValidateResponse;
import com.admin.portal.services.SupervisorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/checker")
@RequiredArgsConstructor
public class CheckerController {

    private final SupervisorService supervisorService;

    @GetMapping("/request")
    public List<Request> allRequestPendingApprover () {
        return this.supervisorService.requestsForApproval();
    }

    @PostMapping("/getDocs")
    public DocumentResponse findDocumentForRequest(@RequestBody DocumentRequest documentRequest) {
        return this.supervisorService.findDocumentsForRequest(documentRequest);
    }

    @PostMapping("/validate")
    public DocumentValidateResponse checkerValidate (@RequestBody DocumentValidateRequest documentValidateRequest) {
        return this.supervisorService.approveDocument(documentValidateRequest);
    }


}

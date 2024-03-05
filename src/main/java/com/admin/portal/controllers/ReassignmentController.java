package com.admin.portal.controllers;

import com.admin.portal.entities.Request;
import com.admin.portal.entities.User;
import com.admin.portal.models.ReassignmentRequest;
import com.admin.portal.models.ReassignmentResponse;
import com.admin.portal.services.ReassignmentService;
import com.admin.portal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/assignment")
public class ReassignmentController {

    private final ReassignmentService reassignmentService;
    private final UserService userService;


    @GetMapping("/requestsForReassignment")
    public List<Request> findAllRequestForReassignment () {
        return this.reassignmentService.getRequestsForReassignment();
    }

    @GetMapping("/availableReviewers")
    public List<User> getAllMakerUsers () {
        return this.userService.findAllMakers();
    }

    @PostMapping("/enteruserandrequest")
    public ReassignmentResponse assignRequest (@RequestBody ReassignmentRequest reassignmentRequest) {
        return reassignmentService.setReviewerToUnassignedRequest(reassignmentRequest);
    }
}

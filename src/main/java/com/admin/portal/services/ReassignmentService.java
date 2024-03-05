package com.admin.portal.services;

import com.admin.portal.entities.Request;
import com.admin.portal.entities.User;
import com.admin.portal.models.ReassignmentRequest;
import com.admin.portal.models.ReassignmentResponse;
import com.admin.portal.repositories.RequestRepository;
import com.admin.portal.repositories.UserRepository;
import com.admin.portal.utilities.Action;
import com.admin.portal.utilities.StatusRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReassignmentService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;
    private final UserService userService;

    public void findRequestsForReassignment() {
        List<Request> closedRequests = requestRepository.findByStatus(StatusRequests.CLOSED);
        List<Request> openRequests = requestRepository.findByStatus(StatusRequests.OPEN);

        for (Request closedRequest : closedRequests) {
            for (Request openRequest : openRequests) {
                try {
                    if (closedRequest.getProfileID().equals(openRequest.getProfileID()) && closedRequest.getBusinessName().equals(openRequest.getBusinessName())) {
                        if (openRequest.getReviewer().equals(null)) {
                            openRequest.setItemComment(closedRequest.getItemComment());
                            openRequest.setStatus(StatusRequests.REASSIGN);
                            openRequest.setDateAssigned(null);
                            openRequest.setReviewer(null);
                            requestRepository.save(openRequest);
                        }
                    }
                } catch (Exception e) {
                    // Handles the exception
                    System.out.println(e.getLocalizedMessage());
                    System.out.println("No request for reassignment");
                }
            }
        }
    }

    public List<Request> getRequestsForReassignment() {
        return this.requestRepository.findByStatus(StatusRequests.REASSIGN);
    }

    public ReassignmentResponse setReviewerToUnassignedRequest(ReassignmentRequest reassignmentRequest) {
        ReassignmentResponse reassignmentResponse = new ReassignmentResponse();
        boolean isRunning = true;
        User makerUser = this.userRepository.findByUserId(reassignmentRequest.getUserId());
        System.out.println(makerUser);

        List<User> rpcMakerUsers = this.userService.findAllMakers();

        if (makerUser == null){
            reassignmentResponse.setUserId(reassignmentRequest.getUserId());
            reassignmentResponse.setMessage("User not found");
            isRunning = false;
        } else {
            for (User rpcMakerUser : rpcMakerUsers) {
                if (makerUser == rpcMakerUser) {
                    isRunning = true;
                    break;
                } else {
                    reassignmentResponse.setUserId(reassignmentRequest.getUserId());
                    reassignmentResponse.setMessage("User is not an RPC_MAkER");
                    isRunning = false;
                }
            }
        }

        if (isRunning) {
            try {
                List<Request> requestsForReassignment = this.requestRepository.findByStatus(StatusRequests.REASSIGN);
                for (Request request : requestsForReassignment) {
                    if (reassignmentRequest.getRequest_Item_Number().equals(request.getItemNumber())) {
                        request.setReviewer(makerUser);
                        request.setDateAssigned(new Date());
                        request.setStatus(StatusRequests.OPEN);
                        reassignmentResponse.setItemNumber(reassignmentRequest.getRequest_Item_Number());
                        reassignmentResponse.setUserId(reassignmentRequest.getUserId());
                        auditService.auditPersist(Action.REASSIGN, makerUser);
                        reassignmentResponse.setMessage("Request Successfully Reassigned");
                        this.requestRepository.save(request);
                        System.out.println(request);
                        break;
                    } else {
                        reassignmentResponse.setUserId("NULL");
                        reassignmentResponse.setMessage("Incorrect Request Item Number");
                    }
                }

            } catch (Exception e) {
//                e.getMessage();
                System.out.println("\n No request for Reassignment \n");
            }
        }
        return reassignmentResponse;
    }
}

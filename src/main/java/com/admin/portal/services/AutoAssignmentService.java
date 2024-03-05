package com.admin.portal.services;

import com.admin.portal.entities.Request;
import com.admin.portal.entities.User;
import com.admin.portal.repositories.RequestRepository;
import com.admin.portal.repositories.RoleUsersRepository;
import com.admin.portal.repositories.UserRepository;
import com.admin.portal.utilities.StatusRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutoAssignmentService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final RoleUsersRepository roleUsersRepository;
    private final ReassignmentService reassignmentService;
    private final UserService userService;

    @Scheduled(fixedDelay = 10000)
    public void testMethod () {
        assignReviewers();
    }


    @Transactional
    private void assignReviewers() {
        try {
            // AutoAssignment Service Start Message
            System.out.println("Auto Assignment Service Started");

            //Check request for reassignment
            this.reassignmentService.findRequestsForReassignment();

            // Get List of makerIds;
            List<User> userMakerRole = this.userService.findAllMakers();
            List<String> makerIds = new ArrayList<>();
            for (User user : userMakerRole) {
                makerIds.add(user.getUserId());
            }

            // List of open request
            List<Request> freeReviewalRequests = this.requestRepository.findByStatus(StatusRequests.OPEN);

            //Loop for checking free request in
            Iterator<Request> iterator = freeReviewalRequests.iterator();
            while (iterator.hasNext()) {
                Request request = iterator.next();
                if (request.getReviewer() != null) {
                    iterator.remove();
                }
            }

            // Console Notification of requests
            System.out.println("Number of requests assigned: " + freeReviewalRequests.size());
            System.out.println(freeReviewalRequests + "\n\n");

            int freeReviewalRequestsCount = freeReviewalRequests.size();
            boolean isRunning = true;
            while (isRunning) {
                for (String user : makerIds) {
                    if (freeReviewalRequests.isEmpty()) {
                        isRunning = false;
                        break;
                    }
                    Request request = freeReviewalRequests.get(0);
                    request.setReviewer(userRepository.findByUserId(user));
                    request.setDateAssigned(new Date());
                    requestRepository.save(request);

                    if (request.getReviewer() != null) {
                        freeReviewalRequests.remove(request);
                        freeReviewalRequestsCount--;
                    }
                }
                if (freeReviewalRequestsCount == 0) {
                    isRunning = false;
                }
            }
        } catch (Exception e) {
            e.getMessage();
            System.out.println("No unassigned request for reviewal");
        }
    }

//    public void assignApprover () {
//        // Get list of checkerId
//        List<User> userCheckerRole = this.userRepository.findByRole(Role.CHECKER);
//
//        List<String> checkerIds = new ArrayList<>();
//        for (User user : userCheckerRole) {
//            checkerIds.add(user.getUserId());
//        }
//
//
//        try {
//            System.out.println("Request with no approver");
//
//            //Get list of null approver request
//            List<Request> freeApproverRequests = this.requestRepository.findByStatus(StatusRequests.PENDING_APPROVAL);
//            for (Request request : freeApproverRequests) {
//                if (request.getApprover() != null) {
//                    freeApproverRequests.remove(request);
//                }
//            }
//            System.out.println(freeApproverRequests);
//            int freeApprovalRequestCount = freeApproverRequests.size();
//                boolean isRunning = true;
//                while (isRunning) {
//                    for (String user : checkerIds) {
//                        Request request = freeApproverRequests.get(0);
//                        request.setReviewer(userRepository.findByUserId(user));
//                        request.setDateAssigned(new Date());
//                        requestRepository.save(request);
//                        if (request.getReviewer() != null) {
//                            freeApproverRequests.remove(request);
//                            freeApprovalRequestCount--;
//                        }
//                    }
//                    if (freeApprovalRequestCount == 0) {
//                        isRunning = false;
//                    }
//                }
//            } catch (Exception e) {
//                e.getMessage();
//                System.out.println("No unassigned request for approval");
//            }
//        }
}
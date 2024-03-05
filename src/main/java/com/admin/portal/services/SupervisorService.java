package com.admin.portal.services;

import com.admin.portal.entities.*;
import com.admin.portal.models.*;
import com.admin.portal.repositories.*;
import com.admin.portal.utilities.Action;
import com.admin.portal.utilities.StatusDocuments;
import com.admin.portal.utilities.StatusRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupervisorService {
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final ReviewIdRepository reviewIdRepository;
    private final ReviewDocRepository reviewDocRepository;
    private final ReviewUtilityRepository reviewUtilityRepository;
    private final UserService userService;
    private final AuditService auditService;
    private final NotificationService notificationService;

    public List<Request> requestsForApproval() {
        List<Request> openRequests = this.requestRepository.findByStatus(StatusRequests.OPEN);
        List<Request> requestsForApproval = new ArrayList<>();

        for (Request request : openRequests) {
            Reference refNo = request.getReferenceNumber();
            System.out.println(refNo);
            List<ReviewId> idRequests = this.reviewIdRepository.findByReferenceNumber(refNo);
            List<ReviewDoc> docRequests = this.reviewDocRepository.findByReferenceNumber(refNo);
            List<ReviewUtility> utilityRequests = this.reviewUtilityRepository.findByReferenceNumber(refNo);

            boolean allDocumentsPendingApprovalOrRejection = true; // Flag to track status conditions

            for (int i = 0; i < docRequests.size(); i++) {
                ReviewId idRequest = idRequests.get(i);
                ReviewDoc docRequest = docRequests.get(i);
                ReviewUtility utilityRequest = utilityRequests.get(i);

                if (!(idRequest.getIdStatus().equals(StatusDocuments.PENDING_APPROVAL) || idRequest.getIdStatus().equals(StatusDocuments.PENDING_REJECTION))
                        || !(docRequest.getDocStatus().equals(StatusDocuments.PENDING_APPROVAL) || docRequest.getDocStatus().equals(StatusDocuments.PENDING_REJECTION))
                        || !(utilityRequest.getUtilityStatus().equals(StatusDocuments.PENDING_APPROVAL) || utilityRequest.getUtilityStatus().equals(StatusDocuments.PENDING_REJECTION))) {
                    allDocumentsPendingApprovalOrRejection = false; // Set flag to false if any status condition is not met
                    break; // Exit the loop since we don't need to check further
                }
            }

            if (allDocumentsPendingApprovalOrRejection) {
                requestsForApproval.add(request);
            }
        }
        return requestsForApproval;
    }

    public DocumentResponse findDocumentsForRequest (DocumentRequest documentRequest) {
        DocumentResponse documentResponse = new DocumentResponse();
        String docType = documentRequest.getDocType().toUpperCase();
        Optional<Request> requestNeeded = this.requestRepository.findById(documentRequest.getItemNo());
        Long requestReference = requestNeeded.get().getReferenceNumber().getReferenceNumber();
        if (requestNeeded.isPresent()) {
            if (!docType.equals(null)) {
                switch (docType) {
                    case "ID":
                        documentResponse.setMessage("Request ID Documents");
                        List<ReviewId> reviewIds = this.reviewIdRepository.findByReferenceNumber(requestNeeded.get().getReferenceNumber());
                        List<ReviewId> idsForRequest = new ArrayList<>();
                        for (ReviewId id : reviewIds) {
                            if (id.getReferenceNumber().getReferenceNumber().equals(requestReference)) {
                                idsForRequest.add(id);
                            } else {
                                break;
                            }
                        }
                        documentResponse.setDocumentForRequest(idsForRequest);
                        break;

                    case "UTILITY":
                        documentResponse.setMessage("Request UTILITY Documents");
                        List<ReviewUtility> reviewUtilities = this.reviewUtilityRepository.findByReferenceNumber(requestNeeded.get().getReferenceNumber());
                        List<ReviewUtility> utilitiesForRequest = new ArrayList<>();
                        for (ReviewUtility utility : reviewUtilities) {
                            if (utility.getReferenceNumber().getReferenceNumber().equals(requestReference)) {
                                utilitiesForRequest.add(utility);
                            } else {
                                break;
                            }
                        }
                        documentResponse.setDocumentForRequest(utilitiesForRequest);
                        break;

                    case "DOCUMENT":
                        documentResponse.setMessage("Request DOC Documents");
                        List<ReviewDoc> reviewDocuments = this.reviewDocRepository.findByReferenceNumber(requestNeeded.get().getReferenceNumber());
                        List<ReviewDoc> documentsForRequest = new ArrayList<>();
                        for (ReviewDoc document : reviewDocuments) {
                            if (document.getReferenceNumber().getReferenceNumber().equals(requestReference)) {
                                documentsForRequest.add(document);
                            } else {
                                break;
                            }
                        }
                        documentResponse.setDocumentForRequest(documentsForRequest);
                        break;
                    case "ALL":
                        break;

                    default:
                        documentResponse.setMessage("No valid docType");
                }
            }
        } else if (!requestNeeded.isPresent()){
            documentResponse.setMessage("Invalid Request Item Number.");
        }
        return documentResponse;
    }

    public DocumentValidateResponse approveDocument (DocumentValidateRequest documentValidateRequest) {
        boolean isRunning = true;
        DocumentValidateResponse documentValidateResponse = new DocumentValidateResponse();
        String userId = documentValidateRequest.getUserId();
        User rpcSupervisor = this.userRepository.findByUserId(userId);
        List<User> rpcSupervisorUsers = this.userService.findAllCheckers();

        User rpcSupervisorUser = null;
        for (User supervisorUser : rpcSupervisorUsers) {
            if (supervisorUser.equals(rpcSupervisor)) {
                rpcSupervisorUser = rpcSupervisor;
                isRunning = true;
                break;
            } else {
                documentValidateResponse.setMessage("User is not a RPC_SUPERVISOR");
                isRunning = false;
            }
        }

        System.out.println(rpcSupervisorUser);
        if (isRunning == true) {
            if (rpcSupervisorUser != null) {
                documentValidateResponse.setUserId(documentValidateRequest.getUserId());
                String docType = documentValidateRequest.getDocType().toUpperCase();
                switch (docType) {
                    case "ID":
                        Optional<ReviewId> idForApproval = this.reviewIdRepository.findById(documentValidateRequest.getEntryId());
                        if (idForApproval.isPresent()) {
                            ReviewId idDocNeeded = idForApproval.get();
                            if (!idDocNeeded.equals(null)) {
                                String approvalMessage = documentValidateRequest.getApprovalMessage().toUpperCase();
                                switch (approvalMessage) {
                                    case "APPROVE":
                                        idDocNeeded.setIdStatus(StatusDocuments.APPROVED);
                                        idDocNeeded.setApprovedBy(rpcSupervisorUser);
                                        idDocNeeded.setDateApproved(new Date());
                                        idDocNeeded.setIdComment(documentValidateRequest.getItemComment());
                                        documentValidateResponse.setMessage(documentValidateRequest.getDocType() + " type document: "+ documentValidateRequest.getApprovalMessage());
                                        reviewIdRepository.save(idDocNeeded);
                                        auditService.auditPersist(Action.APPROVE, rpcSupervisorUser);
                                        break;

                                    case "REJECT":
                                        idDocNeeded.setIdStatus(StatusDocuments.REJECTED);
                                        idDocNeeded.setApprovedBy(rpcSupervisorUser);
                                        idDocNeeded.setDateApproved(new Date());
                                        idDocNeeded.setIdComment(documentValidateRequest.getItemComment());
                                        documentValidateResponse.setMessage(documentValidateRequest.getDocType() + " type document: "+ documentValidateRequest.getApprovalMessage());
                                        reviewIdRepository.save(idDocNeeded);
                                        notificationService.notificationPersist(documentValidateRequest);
                                        auditService.auditPersist(Action.REJECT, rpcSupervisorUser);
                                        break;

                                    default:
                                        documentValidateResponse.setMessage("ApprovalMessage must be either APPROVE OR REJECT");
                                }
                            }
                        } else {
                            documentValidateResponse.setMessage("Invalid entryId");
                        }
                        break;

                    case "UTILITY":
                        Optional<ReviewUtility> utilityForApproval = this.reviewUtilityRepository.findById(documentValidateRequest.getEntryId());
                        if (utilityForApproval.isPresent()) {
                            ReviewUtility utilityDocNeeded = utilityForApproval.get();
                            if (!utilityDocNeeded.equals(null)) {
                                String approvalMessage = documentValidateRequest.getApprovalMessage().toUpperCase();
                                switch (approvalMessage) {
                                    case "APPROVE":
                                        utilityDocNeeded.setUtilityStatus(StatusDocuments.APPROVED);
                                        utilityDocNeeded.setApprovedBy(rpcSupervisorUser);
                                        utilityDocNeeded.setDateApproved(new Date());
                                        utilityDocNeeded.setUtilityComment(documentValidateRequest.getItemComment());
                                        documentValidateResponse.setMessage(documentValidateRequest.getDocType() + " type document: "+ documentValidateRequest.getApprovalMessage());
                                        reviewUtilityRepository.save(utilityDocNeeded);
                                        auditService.auditPersist(Action.APPROVE, rpcSupervisorUser);
                                        break;

                                    case "REJECT":
                                        utilityDocNeeded.setUtilityStatus(StatusDocuments.REJECTED);
                                        utilityDocNeeded.setApprovedBy(rpcSupervisorUser);
                                        utilityDocNeeded.setDateApproved(new Date());
                                        utilityDocNeeded.setUtilityComment(documentValidateRequest.getItemComment());
                                        documentValidateResponse.setMessage(documentValidateRequest.getDocType() + " type document: "+ documentValidateRequest.getApprovalMessage());
                                        reviewUtilityRepository.save(utilityDocNeeded);
                                        notificationService.notificationPersist(documentValidateRequest);
                                        auditService.auditPersist(Action.REJECT, rpcSupervisorUser);
                                        break;

                                    default:
                                        documentValidateResponse.setMessage("ApprovalMessage must be either APPROVE OR REJECT");
                                }
                            }
                        } else {
                            documentValidateResponse.setMessage("Invalid entryId");
                        }
                        break;

                    case "DOCUMENT":
                        Optional<ReviewDoc> docForApproval = this.reviewDocRepository.findById(documentValidateRequest.getEntryId());
                        if (docForApproval.isPresent()) {
                            ReviewDoc docDocNeeded = docForApproval.get();
                            if (!docDocNeeded.equals(null)) {
                                String approvalMessage = documentValidateRequest.getApprovalMessage().toUpperCase();
                                switch (approvalMessage) {
                                    case "APPROVE":
                                        docDocNeeded.setDocStatus(StatusDocuments.APPROVED);
                                        docDocNeeded.setApprovedBy(rpcSupervisorUser);
                                        docDocNeeded.setDateApproved(new Date());
                                        docDocNeeded.setDocComment(documentValidateRequest.getItemComment());
                                        documentValidateResponse.setMessage(documentValidateRequest.getDocType() + " type document: "+ documentValidateRequest.getApprovalMessage());
                                        reviewDocRepository.save(docDocNeeded);
                                        auditService.auditPersist(Action.APPROVE, rpcSupervisorUser);
                                        break;

                                    case "REJECT":
                                        docDocNeeded.setDocStatus(StatusDocuments.REJECTED);
                                        docDocNeeded.setApprovedBy(rpcSupervisorUser);
                                        docDocNeeded.setDateApproved(new Date());
                                        docDocNeeded.setDocComment(documentValidateRequest.getItemComment());
                                        documentValidateResponse.setMessage(documentValidateRequest.getDocType() + " type document: "+ documentValidateRequest.getApprovalMessage());
                                        reviewDocRepository.save(docDocNeeded);
                                        notificationService.notificationPersist(documentValidateRequest);
                                        auditService.auditPersist(Action.APPROVE, rpcSupervisorUser);
                                        break;

                                    default:
                                        documentValidateResponse.setMessage("ApprovalMessage must be either APPROVE OR REJECT");
                                }
                            }
                        } else {
                            documentValidateResponse.setMessage("Invalid entryId");
                        }
                        break;

                    default:
                        documentValidateResponse.setMessage("DOCTYPE MUST EITHER BE ID, DOCUMENT, OR UTILITY");
                }
            } else {
                documentValidateResponse.setMessage("User isn't an RPC Supervisor");
            }
        }
        return documentValidateResponse;
    }
}

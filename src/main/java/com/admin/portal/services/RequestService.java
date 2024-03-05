package com.admin.portal.services;

import com.admin.portal.entities.Reference;
import com.admin.portal.entities.Request;
import com.admin.portal.models.RequestResponse;
import com.admin.portal.repositories.ReferenceRepository;
import com.admin.portal.repositories.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final ReferenceRepository referenceRepository;

    public List<Request> findAllRequests () {
        return  this.requestRepository.findAll();
    }

    public List<Request> findNullReviewers () {
        return this.requestRepository.findByReviewerIsNull();
    }


    public RequestResponse findRequestByRefNo(Long refNo) {
//        Long refNo = requestReferenceRequest.getRequestRefNo();
        RequestResponse requestResponse = new RequestResponse();

        Optional<Reference> ref = this.referenceRepository.findById(refNo);
        if (!ref.isPresent()) {
            requestResponse.setItemNo(null);
            requestResponse.setRequest_Comment("Incorrect Reference Number");
        } else {
            Request request = this.requestRepository.findByReferenceNumber(ref.get());
            requestResponse.setItemNo(request.getItemNumber());
            requestResponse.setFirstName(request.getFirstName());
            requestResponse.setLastName(request.getLastName());
            requestResponse.setBusinessName(request.getBusinessName());
            requestResponse.setAccountNumber(request.getAccountNo());
            requestResponse.setTypeOfID(request.getTypeOfID());
            requestResponse.setAccountName(request.getAccountName());
            requestResponse.setRequest_Comment("Request for " + requestResponse.getBusinessName());
        }

        return requestResponse;
    }
}

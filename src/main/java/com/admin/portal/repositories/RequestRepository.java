package com.admin.portal.repositories;

import com.admin.portal.entities.Reference;
import com.admin.portal.entities.Request;
import com.admin.portal.utilities.StatusRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByReviewerIsNull();
    List<Request> findByReviewerIsNotNull();

    List<Request> findByStatus(StatusRequests statusRequests);

    Request findByReferenceNumber(Reference ref);


    Request findByItemNumber(Long itemNumber);

//    Request findByReferenceNumber(Reference)

//    @Query("SELECT r.reviewer.userID, COUNT(r.itemNumber) FROM Request r GROUP BY r.reviewer.userID")
//    List<Object[]> findUserIDsWithRequestCount();

//    default Map<String, Long> getReviewerIDsWithRequestCount() {
//        List<Object[]> results = findUserIDsWithRequestCount();
//        Map<String, Long> reviewerIDsWithCount = new HashMap<>();
//
//        for (Object[] result : results) {
//            String reviewerID = (String) result[0]; // Assuming reviewerID is of type User
//            Long requestCount = (Long) result[1];
//            reviewerIDsWithCount.put(reviewerID, requestCount);
//        }
//
//        return reviewerIDsWithCount;
//    }
}
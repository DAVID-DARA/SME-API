package com.admin.portal.repositories;

import com.admin.portal.entities.Reference;
import com.admin.portal.entities.ReviewUtility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewUtilityRepository extends JpaRepository<ReviewUtility, Long> {
    List<ReviewUtility> findByReferenceNumber(Long referenceNumber);


    List<ReviewUtility> findByReferenceNumber(Reference reference);


}

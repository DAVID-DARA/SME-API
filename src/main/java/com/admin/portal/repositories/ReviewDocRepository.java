package com.admin.portal.repositories;

import com.admin.portal.entities.Reference;
import com.admin.portal.entities.ReviewDoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewDocRepository extends JpaRepository<ReviewDoc, Long> {
    List<ReviewDoc> findByReferenceNumber(Long referenceNumber);

    List<ReviewDoc> findByReferenceNumber(Reference ref);

}

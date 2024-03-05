package com.admin.portal.repositories;

import com.admin.portal.entities.Reference;
import com.admin.portal.entities.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewIdRepository extends JpaRepository<ReviewId, Long> {
    List<ReviewId> findByReferenceNumber(Reference ref);

    List<ReviewId> findByReferenceNumber(Long referenceNumber);

}

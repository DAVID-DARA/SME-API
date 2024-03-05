package com.admin.portal.repositories;

import com.admin.portal.entities.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AuditRepository extends JpaRepository<Audit, Long> {

    List<Audit> findByDateBetweenAndUserUserId(Date startDate, Date endDate, String user_id);

}
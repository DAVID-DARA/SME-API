package com.admin.portal.services;

import com.admin.portal.entities.Notification;
import com.admin.portal.entities.ReviewId;
import com.admin.portal.entities.ReviewUtility;
import com.admin.portal.entities.User;
import com.admin.portal.models.DocumentValidateRequest;
import com.admin.portal.models.NotificationResponse;
import com.admin.portal.repositories.*;

import com.admin.portal.utilities.NotificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final ReviewIdRepository reviewIdRepository;
    private final ReviewUtilityRepository reviewUtilityRepository;
    private final ReviewDocRepository reviewDocRepository;

    public String notificationPersist (DocumentValidateRequest documentValidateRequest) {
        Notification notification = new Notification();
        try {
            notification.setStatus("N");
            notification.setUser(userRepository.findByUserId(documentValidateRequest.getUserId()));
            notification.setMessage("\nRPC_SUPERVISOR: " + documentValidateRequest.getUserId() + ".\nDeclined "+ documentValidateRequest.getDocType() + " type document of customer with profileId (" +documentValidateRequest.getUserId() + ").\nReason: " + documentValidateRequest.getItemComment());
            notificationRepository.save(notification);
            return "Success";
        } catch (Exception e) {
//            e.getMessage();
            return "Failed";
        }
    }

    public Notification getNotificationById (Long id) {
        Notification notification = new Notification();
        Optional<Notification> notificationGotten = this.notificationRepository.findById(id);
        if (notificationGotten.isPresent()) {
            notification.setId(notificationGotten.get().getId());
            notification.setUser(notificationGotten.get().getUser());
            notification.setStatus("Y");
            notification.setMessage(notificationGotten.get().getMessage());
            notificationRepository.save(notification);
        } else {

            notification.setMessage("Invalid Id");
        }
        return notification;
    }

    public List<Notification> getAllNotifications() {
        List<Notification> allNotifications = this.notificationRepository.findAll();
        List<NotificationResponse> notificationResponseList = new ArrayList<>();

        return allNotifications;
    }
}

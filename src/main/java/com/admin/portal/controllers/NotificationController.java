package com.admin.portal.controllers;

import com.admin.portal.entities.Notification;
import com.admin.portal.models.NotificationResponse;
import com.admin.portal.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    @GetMapping("/getAllNotifications")
    public List<Notification> getAllNotifications () {
        return this.notificationService.getAllNotifications();
    }

    @GetMapping("/getNotification")
    public Notification getNotificationById (@RequestParam Long id) {
        return this.notificationService.getNotificationById(id);
    }
}

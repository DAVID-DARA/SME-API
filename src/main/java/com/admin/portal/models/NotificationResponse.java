package com.admin.portal.models;

import com.admin.portal.utilities.NotificationStatus;
import lombok.Data;

@Data
public class NotificationResponse {

    private Long id;
    private String userId;
    private String message;
    private String notificationStatus;
}

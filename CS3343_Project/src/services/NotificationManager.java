package services;

import models.Notification;
import java.util.List;

public class NotificationManager {
    private List<Notification> notifications;

    public boolean addNotifications(Notification notification) {
        return notifications.add(notification);
    }

    public boolean deleteNotifications(Notification notification) {
        return notifications.remove(notification);
    }
}

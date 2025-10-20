package Services;

import java.util.List;

import Objects.Notification;

public class NotificationManager {
    private List<Notification> notifications;

    public boolean addNotifications(Notification notification) {
        return notifications.add(notification);
    }

    public boolean deleteNotifications(Notification notification) {
        return notifications.remove(notification);
    }
}

package Services;

import java.util.ArrayList;
import java.util.List;

import Objects.Notification;

public class NotificationManager {
    private List<Notification> notifications;

    public NotificationManager() {
        this.notifications = new ArrayList<>();
    }

    public boolean addNotifications(Notification notification) {
        return notifications.add(notification);
    }

    public boolean deleteNotifications(Notification notification) {
        return notifications.remove(notification);
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
}

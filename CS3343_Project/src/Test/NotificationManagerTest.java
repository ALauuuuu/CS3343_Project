package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import Services.NotificationManager;
import Objects.Notification;

public class NotificationManagerTest {
	
	Date date = new Date();
    private NotificationManager manager;
    private Notification notification1;
    private Notification notification2;

    @BeforeEach
    void setUp() {
        manager = new NotificationManager();
        notification1 = new Notification(date, "Message 1");
        notification2 = new Notification(date, "Message 2");
    }

    @Test
    void AddNotifications() {
        assertTrue(manager.addNotifications(notification1));
        assertTrue(manager.getNotifications().contains(notification1));
    }

    @Test
    void DeleteNotifications() {
        manager.addNotifications(notification1);
        assertTrue(manager.deleteNotifications(notification1));
        assertFalse(manager.getNotifications().contains(notification1));
    }

    @Test
    void GetNotifications() {
        manager.addNotifications(notification1);
        manager.addNotifications(notification2);
        List<Notification> notifications = manager.getNotifications();
        assertEquals(2, notifications.size());
        assertTrue(notifications.contains(notification1));
        assertTrue(notifications.contains(notification2));
    }
}

package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import Objects.Notification;
import Services.NotificationManager;

public class NotificationTest {
    
    private Notification notification;
    private Date testDate;

    @BeforeEach
    public void setUp() {
        testDate = new Date();
        notification = new Notification(testDate, "Test notification message");
    }

    @Test
    public void ConstructorAndGetMessage() {
        assertEquals("Test notification message", notification.getMessage());
    }

    @Test
    public void ConstructorAndGetDate() {
        assertEquals(testDate, notification.getDate());
    }

    @Test
    public void ToString() {
        String result = notification.toString();
        assertTrue(result.contains("Date: " + testDate));
        assertTrue(result.contains("Message: Test notification message"));
    }

    @Test
    public void ToStringFormat() {
        String expected = "Date: " + testDate + ", Message: Test notification message";
        assertEquals(expected, notification.toString());
    }

    @Test
    public void NotificationWithEmptyMessage() {
        Notification emptyNotif = new Notification(testDate, "");
        assertEquals("", emptyNotif.getMessage());
        assertEquals(testDate, emptyNotif.getDate());
    }

    @Test
    public void NotificationWithLongMessage() {
        String longMessage = "This is a very long notification message that contains a lot of text to test if the notification can handle lengthy messages properly.";
        Notification longNotif = new Notification(testDate, longMessage);
        assertEquals(longMessage, longNotif.getMessage());
    }

    @Test
    public void MultipleNotifications() {
        Date date1 = new Date(System.currentTimeMillis() - 10000);
        Date date2 = new Date(System.currentTimeMillis() - 5000);
        
        Notification notif1 = new Notification(date1, "First notification");
        Notification notif2 = new Notification(date2, "Second notification");
        
        assertEquals("First notification", notif1.getMessage());
        assertEquals("Second notification", notif2.getMessage());
        assertNotEquals(notif1.getMessage(), notif2.getMessage());
    }

    @Test
    public void NotificationManagerAddNotification() {
        NotificationManager manager = new NotificationManager();
        Notification notif = new Notification(new Date(), "Manager test");
        
        boolean result = manager.addNotifications(notif);
        assertTrue(result);
        assertEquals(1, manager.getNotifications().size());
    }

    @Test
    public void NotificationManagerGetNotifications() {
        NotificationManager manager = new NotificationManager();
        Notification notif1 = new Notification(new Date(), "First");
        Notification notif2 = new Notification(new Date(), "Second");
        
        manager.addNotifications(notif1);
        manager.addNotifications(notif2);
        
        assertEquals(2, manager.getNotifications().size());
        assertTrue(manager.getNotifications().contains(notif1));
        assertTrue(manager.getNotifications().contains(notif2));
    }

    @Test
    public void NotificationManagerDeleteNotification() {
        NotificationManager manager = new NotificationManager();
        Notification notif = new Notification(new Date(), "To be deleted");
        
        manager.addNotifications(notif);
        assertEquals(1, manager.getNotifications().size());
        
        boolean result = manager.deleteNotifications(notif);
        assertTrue(result);
        assertEquals(0, manager.getNotifications().size());
    }

    @Test
    public void NotificationManagerDeleteNonExistentNotification() {
        NotificationManager manager = new NotificationManager();
        Notification notif1 = new Notification(new Date(), "Existing");
        Notification notif2 = new Notification(new Date(), "Non-existent");
        
        manager.addNotifications(notif1);
        
        boolean result = manager.deleteNotifications(notif2);
        assertFalse(result);
        assertEquals(1, manager.getNotifications().size());
    }

    @Test
    public void NotificationManagerEmptyList() {
        NotificationManager manager = new NotificationManager();
        assertNotNull(manager.getNotifications());
        assertEquals(0, manager.getNotifications().size());
    }

    @Test
    public void NotificationWithSpecialCharacters() {
        String specialMessage = "Special chars: !@#$%^&*()_+-=[]{}|;':\"<>?,./";
        Notification notif = new Notification(testDate, specialMessage);
        
        assertEquals(specialMessage, notif.getMessage());
        assertTrue(notif.toString().contains(specialMessage));
    }
}

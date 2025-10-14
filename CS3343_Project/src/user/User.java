package user;

public abstract class User {
    private static int nextUserId = 1;
    protected String username;
    protected int userId;
    protected boolean isLoggedIn;

    public User(String username) {
        this.username = username;
        this.userId = nextUserId++;
        this.isLoggedIn = false;
    }

    public void login() {
        this.isLoggedIn = true;
        System.out.println("Logged in successfully as " + username);
    }

    public void logout() {
        this.isLoggedIn = false;
        System.out.println("Logged out successfully");
    }

    public abstract void viewNotifications();

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}

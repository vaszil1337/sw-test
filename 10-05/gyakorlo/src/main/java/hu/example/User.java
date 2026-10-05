package hu.example;

public class User {
    private String userName;
    private String password;
    private int id;
    private int loginCount;
    private boolean loggedIn;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void login() {
        loggedIn = true;
        loginCount++;
    }

    public void logout() {
        loggedIn = false;
    }

    public boolean updatePwd(String newPassword, boolean isLoggedIn) {
        throw new UnsupportedOperationException("Password update is not implemented");
    }
}

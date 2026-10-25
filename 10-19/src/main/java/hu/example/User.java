package hu.example;

public class User {
    private String userName;
    private String password;
    private int id;
    private int loginCount;
    private boolean loggedIn;
    private UserDatabase userDatabase;

    public User() {
    }

    public User(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public User(int id, String userName, String password, boolean loggedIn, UserDatabase userDatabase) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.loggedIn = loggedIn;
        this.userDatabase = userDatabase;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public void setUserDatabase(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public void login() {
        loggedIn = true;
        loginCount++;
    }

    public void logout() {
        loggedIn = false;
    }

    public boolean updatePwd(String newPassword, boolean isLoggedIn) {
        if (!isLoggedIn) {
            return false;
        }
        if (userDatabase == null) {
            throw new IllegalStateException("UserDatabase is not initialized");
        }
        userDatabase.setPassword(this.id, newPassword);
        this.password = newPassword;
        return true;
    }

    public boolean updatePwd(String newPassword) {
        return updatePwd(newPassword, this.loggedIn);
    }
}

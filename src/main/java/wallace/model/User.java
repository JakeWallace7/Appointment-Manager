package wallace.model;

/**
 * Plain java class representing user objects
 */
public class User {
    /**
     * Sets a variable for the users id
     */
    private int userId;
    /**
     * Sets a variable for the users name
     */
    private String userName;
    /**
     * Sets a variable for the users password
     */
    private String password;

    /**
     * @param userId the user ID
     * @param userName the user name
     * @param password the user password
     */
    public User(int userId, String userName, String password){
        this.userId=userId;
        this.userName=userName;
        this.password=password;
    }

    /**
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the user name to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Overridden toString class used to print the user name rather than memory location.
     * @return the user name
     */
    @Override
    public String toString(){
        return userName;
    }
}

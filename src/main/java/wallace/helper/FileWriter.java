package wallace.helper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Helper class to log user login activity
 */
public class FileWriter {

    /**
     * Appends a new line to the text file src/main/java/wallace/login_activity.txt with login information.  All times in UTC
     * @param loginStatus the login status
     * @param username the attempted user name
     * @param password the attempted user password
     * @throws IOException
     */
    public static void recordLogin(String loginStatus, String username, String password) throws IOException {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime currentDateTime = TimeTravel.toUTCTime(LocalDateTime.now());

        java.io.FileWriter writer = new java.io.FileWriter("src/main/java/login_activity.txt", true);
        writer.write(dateFormat.format(currentDateTime) + " UTC | " + loginStatus + " | Username: " + username + " | Password: " + password + "\n");
        writer.close();
    }
}

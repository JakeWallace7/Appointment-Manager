package wallace.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wallace.DAO.FromDB;
import wallace.Main;
import wallace.helper.FileWriter;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for the login screen.  FXML login.FXML
 */
public class LoginController implements Initializable {

    /**
     * Sets a variable for the login button
     */
    public Button login_button;
    /**
     * Sets a variable for the username text-field
     */
    public TextField username_textField;
    /**
     * Sets a variable for the password text-field
     */
    public TextField password_textField;
    /**
     * Sets a variable for the zone label
     */
    public Label zoneId_label;
    /**
     * Sets a variable for the login status label
     */
    public Label login_status;
    /**
     * Sets a variable for the username label
     */
    public Label username_label;
    /**
     * Sets a variable for the password label
     */
    public Label password_label;
    /**
     * Sets a variable for the current language label
     */
    public Label current_language_label;
    /**
     * Sets a variable for the Stage
     */
    Stage stage;
    /**
     * Sets a variable for the Scene
     */
    Parent scene;
    /**
     * Sets a variable for the current users locale
     */
    Locale locale = Locale.getDefault();
    /**
     * Sets a variable for the resource bundle based on the locale
     */
    ResourceBundle bundle = ResourceBundle.getBundle("locale", locale);

    /**
     * Initializes the login screen and translates the application language to French or English based on system language settings.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zoneId = ZoneId.systemDefault();
        zoneId_label.setText("Zone: " + zoneId);
        username_label.setText(bundle.getString("Username"));
        password_label.setText(bundle.getString("Password"));
        login_button.setText(bundle.getString("Login"));
        current_language_label.setText(bundle.getLocale().getDisplayLanguage());
    }

    /**
     * Validates the user's credentials by checking the users table from the database.  Gives a login error message upon
     * login failure.  Logs all activity in src/main/java/wallace/login_activity.txt
     * @param actionEvent triggers on button event
     * @throws SQLException
     * @throws IOException
     */
    public void on_login_button(ActionEvent actionEvent) throws SQLException, IOException {
        String username = username_textField.getText();
        String password = password_textField.getText();

        if (FromDB.validateUser(username, password)){
            login_status.setText(bundle.getString("LoginStatusSuccess"));
            ResourceBundle en_bundle = ResourceBundle.getBundle("locale", Locale.ENGLISH); //translating back to english for logging purposes
            String ls = en_bundle.getString("LoginStatusSuccess");
            FileWriter.recordLogin(ls, username, password);

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/mainPage.fxml")));
            Scene scene2 = new Scene(scene, 1000, 700);
            stage.setScene(scene2);
            stage.show();
        } else {
            login_status.setText(bundle.getString("LoginStatusFail"));
            ResourceBundle en_bundle = ResourceBundle.getBundle("locale", Locale.ENGLISH);
            String ls = en_bundle.getString("LoginStatusFail");
            FileWriter.recordLogin(ls, username, password);
        }
    }
}

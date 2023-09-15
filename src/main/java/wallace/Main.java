package wallace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wallace.DAO.JDBC;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * AUTHOR: Jacob Wallace
 * Built for WGU C195 - Software II
 * Main class that runs the program.
 */
public class Main extends Application {

    /**
     * Sets the locale of the program based on the users language settings.  Loads the JavaFX scene and starts the program.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        Locale userLocale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("locale", userLocale);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/login.fxml"), bundle);
        Scene scene = new Scene(fxmlLoader.load(), 600, 350);
        stage.setTitle(bundle.getString("AppointmentScheduler"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args launches the program and opens the database connection.  Closes the connection on program exit.
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}

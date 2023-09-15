package wallace.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import wallace.DAO.FromDB;
import wallace.Main;
import wallace.helper.Lookup;
import wallace.helper.TimeTravel;
import wallace.model.*;
import wallace.helper.Combo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for the addAppointment screen.  FXML: addAppointment.FXML
 */
public class AddAppointmentController implements Initializable {

    /**
     * Sets a variable for the title text-field
     */
    public TextField title_textField;
    /**
     * Sets a variable for the description text-field
     */
    public TextField description_textField;
    /**
     * Sets a variable for the type text-field
     */
    public TextField type_textField;
    /**
     * Sets a variable for the location text-field
     */
    public TextField location_textField;
    /**
     * Sets a variable for the contact combo-box
     */
    public ComboBox<Contact> contactCombo;
    /**
     * Sets a variable for the start date date-picker
     */
    public DatePicker startDate;
    /**
     * Sets a variable for the end date date-picker
     */
    public DatePicker endDate;
    /**
     * Sets a variable for the start hour combo-box
     */
    public ComboBox<String> startHour;
    /**
     * Sets a variable for the end minute combo-box
     */
    public ComboBox<String> endMinute;
    /**
     * Sets a variable for the start minute combo-box
     */
    public ComboBox<String> startMinute;
    /**
     * Sets a variable for the end hour combo-box
     */
    public ComboBox<String> endHour;
    /**
     * Sets a variable for the save button
     */
    public Button save_button;
    /**
     * Sets a variable for the cancel button
     */
    public Button cancel_button;
    /**
     * Sets a variable for the customer combo-box
     */
    public ComboBox<Customer> customerCombo;
    /**
     * Sets a variable for the user combo-box
     */
    public ComboBox<User> userCombo;
    /**
     * Sets a variable for the title error label
     */
    public Label title_error;
    /**
     * Sets a variable for the description error label
     */
    public Label description_error;
    /**
     * Sets a variable for the location error label
     */
    public Label location_error;
    /**
     * Sets a variable for the for the type error label
     */
    public Label type_error;
    /**
     * Sets a variable for the start error label
     */
    public Label start_error;
    /**
     * Sets a variable for the end error label
     */
    public Label end_error;
    /**
     * Sets a variable for the contact error label
     */
    public Label contact_error;
    /**
     * Sets a variable for the customer error label
     */
    public Label customer_error;
    /**
     * Sets a variable for the user error label
     */
    public Label user_error;

    /**
     * Sets a variable for the Stage
     */
    Stage stage;
    /**
     * Sets a variable for the Scene
     */
    Parent scene;

    /**
     * Initializes the addAppointment screen and populates the combo-boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<Contact> allContacts = FromDB.getContacts();
            ObservableList<Customer> allCustomers = FromDB.getCustomers();
            ObservableList<User> allUsers = FromDB.getUsers();

            contactCombo.setItems(allContacts);
            customerCombo.setItems(allCustomers);
            userCombo.setItems(allUsers);

            Combo.addNumbersToComboBox(startHour, 0, 23);
            Combo.addNumbersToComboBox(endHour, 0, 23);
            Combo.addNumbersToComboBox(startMinute, 0, 59);
            Combo.addNumbersToComboBox(endMinute, 0, 59);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates each input field and checks the appointment time for overlaps and business hours.  Creates a new appointment
     * and writes it to the database.  Redirects to main screen.
     * @param actionEvent triggers on button event
     * @throws SQLException
     * @throws IOException
     */
    public void on_save_button(ActionEvent actionEvent) throws SQLException, IOException {
        validateFields();

        if (validateFields()) {
            int startHr = Integer.parseInt(startHour.getValue());
            int startMin = Integer.parseInt(startMinute.getValue());
            int endHr = Integer.parseInt(endHour.getValue());
            int endMin = Integer.parseInt(endMinute.getValue());

            String title = title_textField.getText();
            String description = description_textField.getText();
            String location = location_textField.getText();
            String type = type_textField.getText();
            LocalDateTime start = TimeTravel.combineDate(startDate.getValue(), startHr, startMin);
            LocalDateTime end = TimeTravel.combineDate(endDate.getValue(), endHr, endMin);
            int customerId = customerCombo.getSelectionModel().getSelectedItem().getCustomerId();
            int userId = userCombo.getSelectionModel().getSelectedItem().getUserId();
            int contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();

            if (TimeTravel.isOutsideOfficeHours(start, end)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "This appointment is outside our office hours, please choose" +
                        " another time within 8:00 - 22:00 EST");
                alert.showAndWait();
            } else if (TimeTravel.IsAddConflict(start, end, customerId)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This appointment overlaps with an existing appointment. " +
                        "Please pick another date/time, or assign another customer with availability.");
                alert.showAndWait();
            } else if(start.isAfter(end)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure that the start time comes before the end time");
                alert.showAndWait();
            } else {
                Appointment appointment = new Appointment(0, title, description, location, type, start, end, customerId, userId, contactId);
                FromDB.addAppointment(appointment);

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/mainPage.fxml")));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    /**
     * Checks each input field with the appropriate method.
     * @return boolean true if all fields have acceptable input
     */
    private boolean validateFields() {
        boolean isValid = true;
        if (Lookup.ifTextIsEmpty(title_textField, title_error, "Title is required")) {
            isValid = false;
        }
        if (Lookup.ifTextIsEmpty(description_textField, description_error, "Description is required")) {
            isValid = false;
        }
        if (Lookup.ifTextIsEmpty(location_textField, location_error, "Location is required")) {
            isValid = false;
        }
        if (Lookup.ifTextIsEmpty(type_textField, type_error, "Contact is required")) {
            isValid = false;
        }
        if (Lookup.ifDatePickerIsEmpty(startDate, start_error, "Start date & time are required")) {
            isValid = false;
        }
        if (Lookup.ifComboIsEmpty(startHour, start_error, "Start date & time are required")) {
            isValid = false;
        }
        if (Lookup.ifComboIsEmpty(startMinute, start_error, "Start date & time are required")) {
            isValid = false;
        }
        if (Lookup.ifDatePickerIsEmpty(endDate, end_error, "End date & time are required")) {
            isValid = false;
        }
        if (Lookup.ifComboIsEmpty(endHour, end_error, "End date & time are required")) {
            isValid = false;
        }
        if (Lookup.ifComboIsEmpty(endMinute, end_error, "End date & time are required")) {
            isValid = false;
        }
        if (Lookup.ifComboIsEmpty(customerCombo, customer_error, "Customer is required")) {
            isValid = false;
        }
        if (Lookup.ifComboIsEmpty(contactCombo, contact_error, "Contact is required")) {
            isValid = false;
        }
        if (Lookup.ifComboIsEmpty(userCombo, user_error, "User is required")) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Cancels the addition of a new appointment and redirects to main screen.
     * @param actionEvent triggers on button event
     * @throws IOException
     */
    public void on_cancel_button(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/mainPage.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

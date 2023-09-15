package wallace.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wallace.DAO.FromDB;
import wallace.Main;
import wallace.helper.TimeTravel;
import wallace.model.Appointment;
import wallace.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.*;

/**
 * Controller class for the mainPage screen.  FXML: mainPage.FXML
 */
public class MainPageController implements Initializable {

    /**
     * Sets a variable for the appointment table-view
     */
    public TableView<Appointment>appointment_tableview;
    /**
     * Sets a variable for the appointment id column in the appointment table-view
     */
    public TableColumn<Appointment, Integer> appointment_id_col;
    /**
     * Sets a variable for the appointment title column in the appointment table-view
     */
    public TableColumn<Appointment, String>appointment_title_col;
    /**
     * Sets a variable for the appointment description column in the appointment table-view
     */
    public TableColumn<Appointment, String> appointment_description_col;
    /**
     * Sets a variable for the appointment location column in the appointment table-view
     */
    public TableColumn<Appointment, String> appointment_location_col;
    /**
     * Sets a variable for the appointment type column in the appointment table-view
     */
    public TableColumn<Appointment, String> appointment_type_col;
    /**
     * Sets a variable for the appointment start time column in the appointment table-view
     */
    public TableColumn<Appointment, LocalDateTime> appointment_start_col;
    /**
     * Sets a variable for the appointment end time column in the appointment table-view
     */
    public TableColumn<Appointment, LocalDateTime> appointment_end_col;
    /**
     * Sets a variable for the appointment customer id column in the appointment table-view
     */
    public TableColumn<Appointment, Integer> appointment_customerId_col;
    /**
     * Sets a variable for the appointment user id column in the appointment table-view
     */
    public TableColumn<Appointment, Integer> appointment_userId_col;
    /**
     * Sets a variable for the appointment contact id column in the appointment table-view
     */
    public TableColumn<Appointment, Integer> appointment_contactId_col;
    /**
     * Sets a variable for the customer table-view
     */
    public TableView<Customer> customer_tableview;
    /**
     * Sets a variable for the customer id column in the customer table-view
     */
    public TableColumn<Customer, Integer>customer_id_col;
    /**
     * Sets a variable for the customer name column in the customer table-view
     */
    public TableColumn<Customer, String> customer_name_col;
    /**
     * Sets a variable for the customer address column in the customer table-view
     */
    public TableColumn<Customer, String> customer_address_col;
    /**
     * Sets a variable for the customer postal code column in the customer table-view
     */
    public TableColumn<Customer, String> customer_postalCode_col;
    /**
     * Sets a variable for the customer phone number column in the customer table-view
     */
    public TableColumn<Customer, String> customer_phone_col;
    /**
     * Sets a variable for the division id column in the customer table-view
     */
    public TableColumn<Customer, Integer>customer_divisionId_col;
    /**
     * Sets a variable for the add appointment button
     */
    public Button add_appointment_button;
    /**
     * Sets a variable for the modify appointment button
     */
    public Button modify_appointment_button;
    /**
     * Sets a variable for the delete appointment button
     */
    public Button delete_appointment_button;
    /**
     * Sets a variable for the sort-by-week radio button
     */
    public RadioButton week_radiobutton;
    /**
     * Sets a variable for the sort-by-month radio button
     */
    public RadioButton month_radiobutton;
    /**
     * Sets a variable for the sort-by-id radiobutton
     */
    public RadioButton id_radiobutton;
    /**
     * Sets a variable for the add customer button
     */
    public Button add_customer_button;
    /**
     * Sets a variable for the delete customer button
     */
    public Button delete_customer_button;
    /**
     * Sets a variable for the modify customer button
     */
    public Button modify_customer_button;
    /**
     * Sets a variable for the appointment alert label
     */
    public Label appointment_alert_label;
    /**
     * Sets a variable for the customer alert label
     */
    public Label customer_alert_label;
    /**
     * Sets a variable for the Stage
     */
    Stage stage;
    /**
     * Sets a variable for the Scene
     */
    Parent scene;
    /**
     * Sets a variable for the selected customer that was passed from the main screen
     */
    private static Customer customerToModify;
    /**
     * Sets a variable for the selected appointment that was passed from the main screen
     */
    private static Appointment appointmentToModify;

    /**
     * Initializes the mainPage screen and populates the customer and appointment tableviews.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointment_tableview.setItems(FromDB.getAppointments());

            appointment_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            appointment_title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
            appointment_description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
            appointment_location_col.setCellValueFactory(new PropertyValueFactory<>("location"));
            appointment_type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
            appointment_start_col.setCellValueFactory(new PropertyValueFactory<>("start"));
            appointment_end_col.setCellValueFactory(new PropertyValueFactory<>("end"));
            appointment_customerId_col.setCellValueFactory(new PropertyValueFactory<>("assignedCustomer"));
            appointment_userId_col.setCellValueFactory(new PropertyValueFactory<>("assignedUser"));
            appointment_contactId_col.setCellValueFactory(new PropertyValueFactory<>("assignedContact"));

            customer_tableview.setItems(FromDB.getCustomers());

            customer_id_col.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customer_name_col.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customer_address_col.setCellValueFactory(new PropertyValueFactory<>("address"));
            customer_postalCode_col.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customer_phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
            customer_divisionId_col.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

            appointment_alert_label.setText(TimeTravel.hasUpcomingAppointment());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Redirects to the addAppointment screen.
     * @param actionEvent triggers on button event
     * @throws IOException
     */
    public void on_add_appointment_button(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/addAppointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Redirects to the modifyAppointment screen.  Displays an error message if no appointment is selected.
     * @param actionEvent triggers on button event
     * @throws IOException
     */
    public void on_modify_appointment_button(ActionEvent actionEvent) throws IOException {
        appointmentToModify = appointment_tableview.getSelectionModel().getSelectedItem();

        if (appointmentToModify != null) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/modifyAppointment.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to modify");
            alert.showAndWait();
        }

    }

    /**
     * Deletes the selected appointment.  Displays an error message if no appointment is selected.  If an appointment is
     * selected the user is asked to confirm before deleting.
     * @param actionEvent triggers on button event
     * @throws SQLException
     */
    public void on_delete_appointment_button(ActionEvent actionEvent) throws SQLException{
        Appointment selectedAppointment = appointment_tableview.getSelectionModel().getSelectedItem();

        if(selectedAppointment == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to delete.");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want delete this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                appointment_tableview.getItems().remove(selectedAppointment);
                FromDB.deleteAppointment(selectedAppointment);
                appointment_alert_label.setText("Appointment Deleted.  ID: " + selectedAppointment.getId() + " Type: " + selectedAppointment.getType());
            }
        }
    }

    /**
     * Redirects to the addCustomer screen.
     * @param actionEvent triggers on button event
     * @throws IOException
     */
    public void on_add_customer_button(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/addCustomer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes the selected customer.  Displays an error message if no customer is selected.  If a customer is selected
     * the user is asked to confirm before deleting
     * @param actionEvent triggers on button event
     * @throws SQLException
     */
    public void on_delete_customer_button(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = customer_tableview.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to delete.");
            alert.showAndWait();
        } else if (hasAppointments(selectedCustomer)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "This customer has active appointments and cannot be deleted.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want delete this customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                customer_tableview.getItems().remove(selectedCustomer);
                FromDB.deleteCustomer(selectedCustomer);
                customer_alert_label.setText("Customer Deleted.  ID: " + selectedCustomer.getCustomerId() + " Name: " + selectedCustomer.getCustomerName());
            }
        }
    }

    /**
     * Redirects to the modifyCustomer screen.  Displays an error message if no customer is selected.
     * @param actionEvent triggers on button event
     * @throws IOException
     */
    public void on_modify_customer_button(ActionEvent actionEvent) throws IOException {
        customerToModify = customer_tableview.getSelectionModel().getSelectedItem();

        if (customerToModify != null) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/modifyCustomer.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to modify");
            alert.showAndWait();
        }
    }

    /**
     * @return the selected customer
     */
    public static Customer passCustomer(){
        return customerToModify;
    }

    /**
     * @return the selected appointment
     */
    public static Appointment passAppointment(){
        return appointmentToModify;
    }

    /**
     * Checks if a given customer has existing appointments
     * @param customer the customer to check
     * @return true if appointments exist for the given customer
     */
    public boolean hasAppointments(Customer customer) {
        for (Appointment a : appointment_tableview.getItems()) {
            if (a.getCustomerId() == customer.getCustomerId()){
                return true;
            }
        }
        return false;
    }

    /**
     * LAMBDA: Uses a lambda function to sort the tableview by week, year. Using the .sort() method on the tableview items,
     * a comparator object is set and the comparingInt() method called.  The lambda then takes the year and multiplies it
     * by 52 using IsoFields week_based_year.  Multiplying by 52 allows us to convert the year into weeks.  This is what
     * allows us to also consider the year in our sort.  The same is repeated for the week value (multiplication by 52 is
     * not necessary here) and the two are added together. These numerical representations of the week and year values
     * are then compared allowing for an ordering.  This allows for a streamlined way to sort the tableview, cutting back
     * on several lines of code.
     * @param actionEvent triggers on radio-button event
     */
    public void on_week_radiobutton(ActionEvent actionEvent) {
        appointment_tableview.getItems().sort(Comparator.comparingInt(a -> a.getStart().get(IsoFields.WEEK_BASED_YEAR) * 52 + a.getStart().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)));
        appointment_tableview.refresh();
    }

    /**
     * LAMBDA: Uses a lambda function to sort the tableview by month, year. Using the .sort() method on the tableview items,
     * a comparator object is set and the comparingInt() method called.  The lambda then takes the year and multiplies it
     * by 12.  Multiplying by 12 allows us to convert the year into months.  This is what allows us to also consider the year
     * in our sort.  The same is repeated for the week value (multiplication by 12 is not necessary here) and the two are
     * added together. These numerical representations of the month and year values are then compared allowing for an
     * ordering.  This allows for a streamlined way to sort the tableview, cutting back on several lines of code.
     * @param actionEvent triggers on radio-button event
     */
    public void on_month_radiobutton(ActionEvent actionEvent) {
        appointment_tableview.getItems().sort(Comparator.comparingInt(a -> a.getStart().getYear() * 12 + a.getStart().getMonthValue()));
        appointment_tableview.refresh();
    }

    /**
     * Query's the database and sets the tableview to all appointments in default order
     * @param actionEvent triggers on radio-button event
     * @throws SQLException
     */
    public void on_all_radiobutton(ActionEvent actionEvent) throws SQLException {
        appointment_tableview.setItems(FromDB.getAppointments());
    }

    /**
     * Exits the program
     * @param actionEvent triggers on button event
     */
    public void on_exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Redirects to the reports page
     * @param actionEvent triggers on button event
     * @throws IOException
     */
    public void on_reports(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/reports.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

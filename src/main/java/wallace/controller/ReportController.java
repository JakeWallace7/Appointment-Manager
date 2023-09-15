package wallace.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wallace.DAO.FromDB;
import wallace.Main;
import wallace.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for the report screen.  FXML: report.FXML
 */
public class ReportController implements Initializable {
    /**
     * Sets a variable for the report table-view
     */
    public TableView<Appointment> reports_tableview;
    /**
     * Sets a variable for the reports appointment id column
     */
    public TableColumn<Appointment, Integer> report_appointment_id_col;
    /**
     * Sets a variable for the reports appointment title column
     */
    public TableColumn<Appointment, String> report_appointment_title_col;
    /**
     * Sets a variable for the reports appointment description column
     */
    public TableColumn<Appointment, String> report_appointment_description_col;
    /**
     * Sets a variable for the reports appointment location column
     */
    public TableColumn<Appointment, String> report_appointment_location_col;
    /**
     * Sets a variable for the reports appointment type column
     */
    public TableColumn<Appointment, String> report_appointment_type_col;
    /**
     * Sets a variable for the reports appointment start time column
     */
    public TableColumn<Appointment, LocalDateTime> report_appointment_start_col;
    /**
     * Sets a variable for the reports appointment end time column
     */
    public TableColumn<Appointment, LocalDateTime> report_appointment_end_col;
    /**
     * Sets a variable for the reports appointment customer id column
     */
    public TableColumn<Appointment, Integer> report_appointment_customerId_col;
    /**
     * Sets a variable for the customer combo-box
     */
    public ComboBox<Customer> report_customer_combo;
    /**
     * Sets a variable for the month combo-box
     */
    public ComboBox<Month> report_month_combo;
    /**
     * Sets a variable for the type combo-box
     */
    public ComboBox<String> report_type_combo;
    /**
     * Sets a variable for the user combo-box
     */
    public ComboBox<User> report_user_combo;
    /**
     * Sets a variable for the filter reset button
     */
    public Button filter_reset;
    /**
     * Sets a variable for the cancel button
     */
    public Button cancel_button;
    /**
     * Sets a variable for the Stage
     */
    Stage stage;
    /**
     * Sets a variable for the Scene
     */
    Parent scene;

    /**
     * Initializes the report screen and populates the report tableview and combo-boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            reports_tableview.setItems(FromDB.getAppointments());

            report_appointment_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            report_appointment_title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
            report_appointment_description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
            report_appointment_location_col.setCellValueFactory(new PropertyValueFactory<>("location"));
            report_appointment_type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
            report_appointment_start_col.setCellValueFactory(new PropertyValueFactory<>("start"));
            report_appointment_end_col.setCellValueFactory(new PropertyValueFactory<>("end"));
            report_appointment_customerId_col.setCellValueFactory(new PropertyValueFactory<>("assignedCustomer"));

            List<Month> months = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                months.add(Month.of(i));
            }

            List<String> types = new ArrayList<>();
            for (Appointment appointment : FromDB.getAppointments()) {
                String type = appointment.getType();
                if (!types.contains(type)) {
                    types.add(type);
                }
            }

            ObservableList<Month> allMonths = FXCollections.observableArrayList(months);
            ObservableList<Customer> allCustomers = FromDB.getCustomers();
            ObservableList<String> allTypes = FXCollections.observableArrayList(types);
            ObservableList<User> allUsers = FromDB.getUsers();

            report_month_combo.setItems(allMonths);
            report_customer_combo.setItems(allCustomers);
            report_type_combo.setItems(allTypes);
            report_user_combo.setItems(allUsers);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * LAMBDA: After checking that the selected value is not null, a lambda is used to filter the tableview by customer.
     * this is done by getting the customer ID of each appointment and comparing it to the selected customer ID.  If
     * there is a match they are added to the filtered list.  The tableview is then set to the filtered list of appointments.
     * @param actionEvent triggers on combo-box event
     * @throws SQLException
     */
    public void on_report_customer_combo(ActionEvent actionEvent) throws SQLException {
        Customer customerSelection = report_customer_combo.getValue();
        if (customerSelection == null) {
            reports_tableview.setItems(FromDB.getAppointments());
        } else {
            FilteredList<Appointment> filteredAppointments = new FilteredList<>(FromDB.getAppointments(), i -> i.getCustomerId() == customerSelection.getCustomerId());
            report_type_combo.getSelectionModel().clearSelection();
            report_user_combo.getSelectionModel().clearSelection();
            report_month_combo.getSelectionModel().clearSelection();
            reports_tableview.setItems(filteredAppointments);
        }
    }

    /**
     * LAMBDA: After checking that the selected value is not null, a lambda is used to filter the tableview by month.
     * this is done by getting the month of each appointment and comparing it to the selected month.  If there is a
     * match they are added to the filtered list.  The tableview is then set to the filtered list of appointments.
     * @param actionEvent triggers on combo-box event
     * @throws SQLException
     */
    public void on_report_month_combo(ActionEvent actionEvent) throws SQLException {
        Month monthSelection = report_month_combo.getValue();
        if (monthSelection == null) {
            reports_tableview.setItems(FromDB.getAppointments());
        } else {
            FilteredList<Appointment> filteredAppointments = new FilteredList<>(FromDB.getAppointments(), i -> i.getStart().getMonth() == monthSelection);
            report_type_combo.getSelectionModel().clearSelection();
            report_user_combo.getSelectionModel().clearSelection();
            report_customer_combo.getSelectionModel().clearSelection();
            reports_tableview.setItems(filteredAppointments);
        }
    }

    /**
     * LAMBDA: After checking that the selected value is not null, a lambda is used to filter the tableview by type.
     * this is done by getting the type of each appointment and comparing it to the selected type.  If there is a
     * match they are added to the filtered list.  The tableview is then set to the filtered list of appointments.
     * @param actionEvent triggers on combo-box event
     * @throws SQLException
     */
    public void on_report_type_combo(ActionEvent actionEvent) throws SQLException {
        String typeSelection = report_type_combo.getValue();
        if (typeSelection == null || typeSelection.isEmpty()) {
            reports_tableview.setItems(FromDB.getAppointments());
        } else {
            FilteredList<Appointment> filteredAppointments = new FilteredList<>(FromDB.getAppointments(), i -> i.getType().equals(typeSelection));
            report_user_combo.getSelectionModel().clearSelection();
            report_customer_combo.getSelectionModel().clearSelection();
            report_month_combo.getSelectionModel().clearSelection();
            reports_tableview.setItems(filteredAppointments);
        }
    }

    /**
     * LAMBDA: After checking that the selected value is not null, a lambda is used to filter the tableview by user.
     * this is done by getting the user ID of each appointment and comparing it to the selected user ID.  If there is a
     * match they are added to the filtered list.  The tableview is then set to the filtered list of appointments.
     * @param actionEvent triggers on combo-box event
     * @throws SQLException
     */
    public void on_report_user_combo(ActionEvent actionEvent) throws SQLException {
        User userSelection = report_user_combo.getValue();
        if (userSelection == null) {
            reports_tableview.setItems(FromDB.getAppointments());
        } else {
            FilteredList<Appointment> filteredAppointments = new FilteredList<>(FromDB.getAppointments(), i -> i.getId() == userSelection.getUserId());
            report_type_combo.getSelectionModel().clearSelection();
            report_customer_combo.getSelectionModel().clearSelection();
            report_month_combo.getSelectionModel().clearSelection();
            reports_tableview.setItems(filteredAppointments);
        }
    }

    /**
     * Resets all report filters to null, resulting in the default tableview.
     * @param actionEvent triggers on button event
     */
    public void on_filter_reset(ActionEvent actionEvent) {
        report_type_combo.getSelectionModel().clearSelection();
        report_user_combo.getSelectionModel().clearSelection();
        report_customer_combo.getSelectionModel().clearSelection();
        report_month_combo.getSelectionModel().clearSelection();
    }

    /**
     * Redirects to main screen.
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

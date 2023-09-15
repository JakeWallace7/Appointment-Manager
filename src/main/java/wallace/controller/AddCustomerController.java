package wallace.controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import wallace.model.Country;
import wallace.model.Customer;
import wallace.model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Controller class for the addCustomer screen.  FXML: addCustomer.FXML
 */
public class AddCustomerController implements Initializable {

    /**
     * Sets a variable for the name text-field
     */
    public TextField name_textField;
    /**
     * Sets a variable for the address text-field
     */
    public TextField address_textField;
    /**
     * Sets a variable for the postal-code text-field
     */
    public TextField postalCode_textField;
    /**
     * Sets a variable for the phone number text-field
     */
    public TextField phone_textField;
    /**
     * Sets a variable for the divisions combo-box
     */
    public ComboBox<Division>divisionCombo;
    /**
     * Sets a variable for the save button
     */
    public Button save_button;
    /**
     * Sets a variable for the cancel button
     */
    public Button cancel_button;
    /**
     * Sets a variable for the country combo-box
     */
    public ComboBox<Country>countryCombo;
    /**
     * Sets a variable for the name error label
     */
    public Label name_error;
    /**
     * Sets a variable for the address error label
     */
    public Label address_error;
    /**
     * Sets a variable for the country error label
     */
    public Label country_error;
    /**
     * Sets a variable for the division error label
     */
    public Label division_error;
    /**
     * Sets a variable for the zip error label
     */
    public Label zip_error;
    /**
     * Sets a variable for the phone number error label
     */
    public Label phone_error;
    /**
     * Sets a variable for the Stage
     */
    Stage stage;
    /**
     * Sets a variable for the Scene
     */
    Parent scene;

    /**
     * Initializes the addCustomer screen and populates the combo-boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Country> allCountries = FromDB.getCountries();
            ObservableList<Division> allDivisions = FromDB.getDivisions();

            countryCombo.setItems(allCountries);
            divisionCombo.setItems(allDivisions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * LAMBDA:  Uses a lambda function to set a filtered list of divisions based on the country ID.  The lambda function
     * checks the selected country ID and checks each division.  If the division country ID is a match it adds it to the
     * filtered list and sets the division combo box with the matching divisions.
     * @param actionEvent triggers on combo-box event
     * @throws SQLException
     */
    public void on_countryCombo(ActionEvent actionEvent) throws SQLException {
        ObservableList<Division> allDivisions = FromDB.getDivisions();
        Country selectedCountry = countryCombo.getSelectionModel().getSelectedItem();
        FilteredList<Division> filteredDivisions = new FilteredList<>(allDivisions, i -> i.getCountryId() == selectedCountry.getCountryId());
        divisionCombo.setItems(filteredDivisions);
    }

    /**
     * Validates each input field.  Creates a new customer and writes it to the database.  Redirects to main screen.
     * @param actionEvent triggers on button event
     * @throws SQLException
     * @throws IOException
     */
    public void on_save_button(ActionEvent actionEvent) throws IOException, SQLException {
        validateFields();
        if(validateFields()) {
            String name = name_textField.getText();
            String address = address_textField.getText();
            String postalCode = postalCode_textField.getText();
            String phone = phone_textField.getText();
            int divisionId = divisionCombo.getSelectionModel().getSelectedItem().getDivisionId();

            Customer customer = new Customer(0, name, address, postalCode, phone, divisionId);
            FromDB.addCustomer(customer);

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/mainPage.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Checks each input field with the appropriate method.
     * @return boolean true if all fields have acceptable input
     */
    private boolean validateFields() {
        boolean isValid = true;

        if (Lookup.ifTextIsEmpty(name_textField, name_error, "Name is required")) {
            isValid = false;
        }
        if (Lookup.ifTextIsEmpty(address_textField, address_error, "Address is required")) {
            isValid = false;
        }
        if (Lookup.ifComboIsEmpty(countryCombo, country_error, "Country is required")) {
            isValid = false;
        }
        if (Lookup.ifComboIsEmpty(divisionCombo, division_error, "State/Province is required")) {
            isValid = false;
        }
        if (Lookup.ifTextIsEmpty(postalCode_textField, zip_error, "Postal Code is required")) {
            isValid = false;
        }
        if (Lookup.ifTextIsEmpty(phone_textField, phone_error, "Phone Number is required")) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Cancels the addition of a new customer and redirects to main screen.
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

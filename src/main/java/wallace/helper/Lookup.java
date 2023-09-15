package wallace.helper;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import wallace.DAO.FromDB;
import wallace.model.Contact;
import wallace.model.Customer;
import wallace.model.User;

import java.sql.SQLException;

/**
 * A helper class for validation and other checks
 */
public class Lookup {
    /**
     * Looks up a contact by ID and returns the contact name
     * @param id contact ID
     * @return contact name
     * @throws SQLException
     */
    public static Contact lookupContact(int id) throws SQLException {
        ObservableList<Contact> allContacts = FromDB.getContacts();
        for(Contact c : allContacts){
            if(c.getContactId() == id){
                return c;
            }
        }
        return null;
    }

    /**
     * Looks up a user by ID and returns the user name
     * @param id the user ID
     * @return the user name
     * @throws SQLException
     */
    public static User lookupUser(int id) throws SQLException {
        ObservableList<User> allUsers = FromDB.getUsers();
        for(User u : allUsers){
            if(u.getUserId() == id){
                return u;
            }
        }
        return null;
    }

    /**
     * Looks up the customer by ID and returns the customer name
     * @param id the customer ID
     * @return the customer name
     * @throws SQLException
     */
    public static Customer lookupCustomer(int id) throws SQLException {
        ObservableList<Customer> allCustomers = FromDB.getCustomers();
        for(Customer u : allCustomers){
            if(u.getCustomerId() == id){
                return u;
            }
        }
        return null;
    }

    /**
     * Checks if the provided text-field is empty and sets an appropriate error message if necessary.
     * @param textField the text-field to check
     * @param errorLabel the label that will display the error message
     * @param errorMessage the error message to display
     * @return boolean true if empty
     */
    public static boolean ifTextIsEmpty(TextField textField, Label errorLabel, String errorMessage) {
        boolean isEmpty = textField.getText().trim().isEmpty();
        errorLabel.setText(isEmpty ? errorMessage : "");
        return isEmpty;
    }

    /**
     * Checks if the provided combo-box is empty and sets an appropriate error message if necessary.
     * @param comboBox the combo-box to check
     * @param errorLabel the label to display the error message
     * @param errorMessage the error message to display
     * @return boolean true if empty
     */
    public static boolean ifComboIsEmpty(ComboBox comboBox, Label errorLabel, String errorMessage) {
        boolean isEmpty = comboBox.getValue() == null;
        errorLabel.setText(isEmpty ? errorMessage : "");
        return isEmpty;
    }

    /**
     * Checks if the provided date-picker is empty and sets an appropriate error message using a label if necessary.
     * @param datePicker the date-picker to check
     * @param errorLabel the label to display the error message
     * @param errorMessage the error message to display
     * @return boolean true if empty
     */
    public static boolean ifDatePickerIsEmpty(DatePicker datePicker, Label errorLabel, String errorMessage) {
        boolean isEmpty = datePicker.getValue() == null;
        errorLabel.setText(isEmpty ? errorMessage : "");
        return isEmpty;
    }
}

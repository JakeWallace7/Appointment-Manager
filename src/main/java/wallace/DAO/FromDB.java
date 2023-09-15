package wallace.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wallace.helper.TimeTravel;
import wallace.model.*;

/**
 * A class that handles database crud operations
 */
public abstract class FromDB {

    /**
     * Queries the database and retrieves all appointments
     * @return an observable list of appointments
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointments() throws SQLException {
        String sql = "SELECT appointment_id, title, description, location, type, start, end, customer_id, user_id, contact_id FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Appointment> appointments = new ArrayList<>();
        while (rs.next()) {
            int appointmentId = rs.getInt("appointment_id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String location = rs.getString("location");
            String type = rs.getString("type");
            LocalDateTime start = TimeTravel.toUserLocalTime(rs.getTimestamp("start").toLocalDateTime());
            LocalDateTime end = TimeTravel.toUserLocalTime(rs.getTimestamp("end").toLocalDateTime());
            int customerId = rs.getInt("customer_id");
            int userId = rs.getInt("user_id");
            int contactId = rs.getInt("contact_id");
            Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
            appointments.add(appointment);
        }
        return FXCollections.observableArrayList(appointments);
    }


    /**
     * Inserts a new appointment into the database
     * @param appointment the appointment to add
     * @throws SQLException
     */
    public static void addAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (title, description, location, type, start, end, customer_id, user_id, contact_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(TimeTravel.toUTCTime(appointment.getStart())));
        ps.setTimestamp(6, Timestamp.valueOf(TimeTravel.toUTCTime(appointment.getEnd())));
        ps.setInt(7, appointment.getCustomerId());
        ps.setInt(8, appointment.getUserId());
        ps.setInt(9, appointment.getContactId());
        ps.executeUpdate();
    }

    /**
     * Updates the appointment in the database
     * @param appointment the appointment to update
     * @throws SQLException
     */
    public static void updateAppointment(Appointment appointment) throws SQLException{
        String sql = "UPDATE appointments SET title = ?, description = ?, location = ?, type = ?, start = ?, end = ?, customer_id = ?, user_id = ?, contact_id = ? WHERE appointment_id = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(TimeTravel.toUTCTime(appointment.getStart())));
        ps.setTimestamp(6, Timestamp.valueOf(TimeTravel.toUTCTime(appointment.getEnd())));
        ps.setInt(7, appointment.getCustomerId());
        ps.setInt(8, appointment.getUserId());
        ps.setInt(9, appointment.getContactId());
        ps.setInt(10, appointment.getId());
        ps.executeUpdate();
    }

    /**
     * Deletes an appointment from the database
     * @param appointment the appointment to delete
     * @throws SQLException
     */
    public static void deleteAppointment(Appointment appointment) throws SQLException {
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointment.getId());
        ps.executeUpdate();
    }

    /**
     * Queries the database and retrieves all customers
     * @return observable list of customers
     * @throws SQLException
     */
    public static ObservableList<Customer> getCustomers() throws SQLException {
        String sql = "SELECT customer_id, customer_name, address, postal_code, phone, division_id FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            int customerId = rs.getInt("customer_id");
            String customerName = rs.getString("customer_name");
            String address = rs.getString("address");
            String postalCode = rs.getString("postal_code");
            String phone = rs.getString("phone");
            int divisionId = rs.getInt("division_id");
            Customer customer = new Customer(customerId, customerName, address, postalCode, phone, divisionId);
            customers.add(customer);
        }
        return FXCollections.observableArrayList(customers);
    }


    /**
     * Inserts a customer into the database
     * @param customer the customer to add
     * @throws SQLException
     */
    public static void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (customer_name, address, postal_code, phone, division_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, customer.getDivisionId());
        ps.executeUpdate();
    }

    /**
     * Updates a customer in the database
     * @param customer the customer to update
     * @throws SQLException
     */
    public static void updateCustomer(Customer customer) throws SQLException{
        String sql = "UPDATE customers SET customer_name = ?, address = ?, postal_code = ?, phone = ?, division_id = ? WHERE customer_id = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, customer.getDivisionId());
        ps.setInt(6, customer.getCustomerId());
        ps.executeUpdate();
    }

    /**
     * Deletes a customer from the database
     * @param customer the customer to delete
     * @throws SQLException
     */
    public static void deleteCustomer(Customer customer) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer.getCustomerId());
        ps.executeUpdate();
    }

    /**
     * Queries the database and retrieves all countries
     * @return observable list of counties
     * @throws SQLException
     */
    public static ObservableList<Country> getCountries() throws SQLException {
        String sql = "SELECT country_id, country FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Country> countries = new ArrayList<>();
        while (rs.next()) {
            int countryId = rs.getInt("country_id");
            String countryName = rs.getString("country");
            Country country = new Country(countryId, countryName);
            countries.add(country);
        }
        return FXCollections.observableArrayList(countries);
    }

    /**
     * Queries the database and retrieves all divisions
     * @return observable list of divisions
     * @throws SQLException
     */
    public static ObservableList<Division> getDivisions() throws SQLException {
        String sql = "SELECT division_id, division, country_id FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Division> divisions = new ArrayList<>();
        while (rs.next()) {
            int divisionId = rs.getInt("division_id");
            String divisionName = rs.getString("division");
            int countryId = rs.getInt("country_id");

            Division division = new Division(divisionId, divisionName, countryId);
            divisions.add(division);
        }
        return FXCollections.observableArrayList(divisions);
    }

    /**
     * Queries the database and retrieves all contacts
     * @return observable list of contacts
     * @throws SQLException
     */
    public static ObservableList<Contact> getContacts() throws SQLException{
        String sql = "SELECT contact_id, contact_name, email FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Contact> contacts = new ArrayList<>();
        while (rs.next()){
            int contactId = rs.getInt("contact_id");
            String contactName = rs.getString("contact_name");
            String email = rs.getString("email");
            Contact contact = new Contact(contactId, contactName, email);
            contacts.add(contact);
        }
        return FXCollections.observableArrayList(contacts);
    }

    /**
     * Queries the database and retrieves all users
     * @return observable list of users
     * @throws SQLException
     */
    public static ObservableList<User> getUsers() throws SQLException{
        String sql = "SELECT user_id, user_name, password FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<User> users = new ArrayList<>();
        while (rs.next()){
            int userId = rs.getInt("user_id");
            String userName = rs.getString("user_name");
            String password = rs.getString("password");
            User user = new User(userId, userName, password);
            users.add(user);
        }
        return FXCollections.observableArrayList(users);
    }

    /**
     * Queries the database for matching user name and password
     * @param userName the user name to check
     * @param password the user password to check
     * @return boolean true is user name and password is valid
     * @throws SQLException
     */
    public static boolean validateUser(String userName, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE USER_NAME = ? AND PASSWORD = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}

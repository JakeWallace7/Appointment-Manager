package wallace.model;

import wallace.DAO.FromDB;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Plain java class representing appointment objects
 */
public class Appointment {
    /**
     * Sets a variable for the appointments id
     */
    private int id;
    /**
     * Sets a variable for the appointments title
     */
    private String title;
    /**
     * Sets a variable for the appointments description
     */
    private String description;
    /**
     * Sets a variable for the appointments location
     */
    private String location;
    /**
     * Sets a variable for the appointments type
     */
    private String type;
    /**
     * Sets a variable for the appointments start time
     */
    private LocalDateTime start;
    /**
     * Sets a variable for the appointments end time
     */
    private LocalDateTime end;
    /**
     * Sets a variable for the appointments customer id
     */
    private int customerId;
    /**
     * Sets a variable for the appointments user id
     */
    private int userId;
    /**
     * Sets a variable for the appointments contact id
     */
    private int contactId;

    /**
     * Constructs an appointment
     * @param appointmentId the appointment ID
     * @param title the appointment title
     * @param description the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param start the appointment start time
     * @param end the appointment end time
     * @param customerId the associated customer ID
     * @param userId the associated user ID
     * @param contactId the associated contact ID
     */
    public Appointment(int appointmentId, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId){
        this.id = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the start time
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * @param start the start time to set
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * @return the end time
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * @param end the end time to set
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * @return the associated customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the associated customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the associated user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the associated user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the associated contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param contactId the associated contact ID to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Used to display the user name rather than the user ID in table columns.
     * @return the user name of the associated user ID
     * @throws SQLException
     */
    public String getAssignedUser() throws SQLException {
        for (User u : FromDB.getUsers()) {
            if (u.getUserId() == userId) {
                return u.getUserName();
            }
        }
        return null;
    }

    /**
     * Used to display the contact name rather than the contact ID in table columns.
     * @return the customer name of the associated customer ID
     * @throws SQLException
     */
    public String getAssignedCustomer() throws SQLException {
        for (Customer c : FromDB.getCustomers()) {
            if (c.getCustomerId() == customerId) {
                return c.getCustomerName();
            }
        }
        return null;
    }

    /**
     * Used to display the contact name rather than the contact ID in table columns.
     * @return the contact name of the associated contact ID
     * @throws SQLException
     */
    public String getAssignedContact() throws SQLException {
        for (Contact c : FromDB.getContacts()) {
            if (c.getContactId() == contactId) {
                return c.getContactName();
            }
        }
        return null;
    }

}

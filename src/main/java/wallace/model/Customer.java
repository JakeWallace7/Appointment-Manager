package wallace.model;

import wallace.DAO.FromDB;

import java.sql.SQLException;


/**
 * Plain java class representing customer objects
 */
public class Customer {
    /**
     * Sets a variable for the customers id
     */
    private int customerId;
    /**
     * Sets a variable for the customers name
     */
    private String customerName;
    /**
     * Sets a variable for the customers address
     */
    private String address;
    /**
     * Sets a variable for the customers postal code
     */
    private String postalCode;
    /**
     * Sets a variable for the customers phone number
     */
    private String phone;
    /**
     * Sets a variable for the customers division id
     */
    private int divisionId;

    /**
     * @param customerId the customer ID
     * @param customerName the customer name
     * @param address the customer address
     * @param postalCode the customer postal code
     * @param phone the customer phone number
     * @param divisionId the customer division ID
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId){
        this.customerId = customerId;
        this.customerName = customerName;
        this.address=address;
        this.postalCode=postalCode;
        this.phone=phone;
        this.divisionId=divisionId;
    }

    /**
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customer name to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the customer address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the customer postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the customer postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the customer phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the customer phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the associated division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId the associated division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Used to display the division name rather than the division ID in table columns.
     * @return the division name of the associated division ID
     * @throws SQLException
     */
    public String getDivisionName() throws SQLException {
        for (Division d : FromDB.getDivisions()) {
            if (d.getDivisionId() == divisionId) {
                return d.getDivision();
            }
        }

        return null;
    }

    /**
     * Overridden toString class used to print the customer name rather than memory location.
     * @return the customer name
     */
    @Override
    public String toString(){
        return customerName;
    }
}

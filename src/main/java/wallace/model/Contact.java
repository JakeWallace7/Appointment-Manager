package wallace.model;

/**
 * Plain java class representing contact objects
 */
public class Contact {
    /**
     * Sets a variable for the contacts id
     */
    private int contactId;
    /**
     * Sets a variable for the contacts name
     */
    private String contactName;
    /**
     * Sets a variable for the contacts email
     */
    private String email;

    /**
     * @param contactId the contact ID
     * @param contactName the contact name
     * @param email the contact email
     */
    public Contact(int contactId, String contactName, String email){
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }


    /**
     * @return the contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param contactId the contact ID to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName the contact name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Overridden toString class used to print the contact name rather than memory location.
     * @return the contact name
     */
    @Override
    public String toString(){
        return (contactName);
    }
}

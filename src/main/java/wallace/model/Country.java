package wallace.model;


/**
 * Plain java class representing country objects
 */
public class Country {
    /**
     * Sets a variable for the country's id
     */
    private int countryId;
    /**
     * Sets a variable for the country's name
     */
    private String country;

    /**
     * @param countryId the country ID
     * @param country the country
     */
    public Country(int countryId, String country){
        this.countryId= countryId;
        this.country=country;
    }

    /**
     * Overridden toString class used to print the country name rather than memory location.
     * @return the country name
     */
    @Override
    public String toString(){
        return (country);
    }

    /**
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the country ID to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
}

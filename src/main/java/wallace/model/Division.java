package wallace.model;

/**
 * Plain java class representing division objects
 */
public class Division {
    /**
     * Sets a variable for the divisions id
     */
    private int divisionId;
    /**
     * Sets a variable for the divisions name
     */
    private String division;
    /**
     * Sets a variable for the divisions country id
     */
    private int countryId;

    /**
     * @param divisionId the division ID
     * @param division the division
     * @param countryId the country ID
     */
    public Division(int divisionId, String division, int countryId){
        this.divisionId=divisionId;
        this.division=division;
        this.countryId=countryId;
    }

    /**
     * Overridden toString class used to print the division name rather than memory location.
     * @return the division name
     */
    @Override
    public String toString(){
        return (division);
    }

    /**
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId the division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division ID to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return the associated country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the associated country ID to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}

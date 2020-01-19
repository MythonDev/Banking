package banking_system;

public class CustomerData {
    private long personalID;

    private String firstName, lastName, dateOfBirth;

    public CustomerData(long personalID, String firstName, String lastName, String dateOfBirth){
        this.personalID = personalID;
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getPersonalID() {
        return personalID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("ID: " + personalID + "\n");
        str.append("Name: " + firstName + " " + lastName + "\n");
        str.append("Born: " + dateOfBirth + "\n");
        return str.toString();
    }
}

package banking_system;

public class WorkerData {

    private long workerNumber;
    private String firstName;
    private String lastName;

    public WorkerData(long workerNumber, String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.workerNumber = workerNumber;
    }

    public long getWorkerNumber() {
        return workerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String toString(){
        return firstName + " " + lastName + "(" + workerNumber + ")";
    }
}

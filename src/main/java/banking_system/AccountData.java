package banking_system;

public class AccountData {
    private long accountNumber;
    private double balance;
    private String currency;
    private String description;

    public AccountData(long accountNumber, double balance, String currency, String description){
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
        this.description = description;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public boolean changeBalance(double value){
        if(balance + value < 0) {
            return false;
        }
        balance += value;
        return true;
    }

    public boolean transfer(AccountData another, double value){
        return changeBalance(-value) && another.changeBalance(value);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("Number: ");
        builder.append(accountNumber + " ");
        builder.append("balance: ");
        builder.append(balance+ " ");
        builder.append("currency: ");
        builder.append(currency + " ");
        builder.append(description);
        return builder.toString();
    }
}

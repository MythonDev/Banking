package banking_system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseController{

    private Connection connection;
    private static DataBaseController instance = new DataBaseController();

    private DataBaseController(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Banking_system", "postgres", "Tokyo2017");
        }
        catch(ClassNotFoundException c){
            c.printStackTrace();
        }
        catch(SQLException s){
            s.printStackTrace();
        }
    }

    public static DataBaseController getInstance(){
        return instance;
    }

    public void createUser(long personalID, String firstName, String lastName, String date, String password){
        try(PreparedStatement userCreation = connection.prepareStatement(
                "INSERT INTO Customers VALUES( ?, ?, ?, TO_DATE(?, 'YYYY/MM/DD'), crypt(?, gen_salt('bf')))"))
        {  userCreation.setLong(1, personalID);
            userCreation.setString(2, firstName);
            userCreation.setString(3, lastName);
            userCreation.setString(4, date);
            userCreation.setString(5, password);
            userCreation.execute();
        }
        catch(SQLException s){
            s.printStackTrace();
        }
    }

    public String getAccountsListString(String lastName){
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers_accounts\n" +
                "WHERE owner_surname = ?\n" +
                "ORDER BY owner_number ASC")) {
            statement.setString(1, lastName);
            ResultSet set = statement.executeQuery();
            StringBuilder builder = new StringBuilder();
            while (set.next()) {
                builder.append("Customer number: " + set.getLong("owner_number") + " ");
                builder.append("Name: " + set.getString("owner_name") + " ");
                builder.append("Surname: " + set.getString("owner_surname") + " ");
                builder.append("Account number: " + set.getLong("account_number") + " ");
                builder.append("Balance: " + set.getString("balance") + " ");
                builder.append("Currency: " + set.getString("currency") + " ");
                builder.append(set.getString("product_name") + " </br> ");

            }
            return builder.toString();
        }
            catch(SQLException s){
                s.printStackTrace();
                return null;
            }
        }

    public CustomerData getUserData(long personalID){
        CustomerData customer;
        try(PreparedStatement userDataQuery = connection.prepareStatement("SELECT  customer_number, first_name, last_name, date_of_birth FROM Customers " +
                "WHERE customer_number = ?"))
        {
                    userDataQuery.setLong(1, personalID);
                    ResultSet result = userDataQuery.executeQuery();
                    if(!result.next())
                        return null;
                    customer = new CustomerData(personalID, result.getString("first_name"), result.getString("last_name"), result.getDate("date_of_birth").toString());
                    return customer;
        }
        catch(SQLException s){
            s.printStackTrace();
        }
        return null;
    }

    public boolean changeBalance(long accountNumber, double value){
        try(PreparedStatement changeBalance = connection.prepareStatement("BEGIN;\n" +
                "INSERT INTO "+(value < 0 ? "Withdrawals":"Incomes")+" VALUES(\n" +
                "nextval(?), ?, ?, current_date);\n" +
                "UPDATE Accounts\n" +
                "SET balance = balance + ?\n" +
                "WHERE account_number = ?\n;" +
                "COMMIT")){
            if(value < 0) {
                //changeBalance.setString(1, "withdrawals");
                changeBalance.setString(1, "withdrawal_numbers");
            }
            else{
               // changeBalance.setString(1, "incomes");
                changeBalance.setString(1, "income_numbers");
            }
            changeBalance.setLong(2, accountNumber);
            changeBalance.setDouble(3, Math.abs(value));
            changeBalance.setDouble(4, value);
            changeBalance.setLong(5, accountNumber);
            changeBalance.execute();
            return true;
        }
        catch(SQLException s){
            s.printStackTrace();
            return false;
        }
    }

    public WorkerData getWorkerData(long workerNumber){
        WorkerData worker;
        try(PreparedStatement workerDataQuery = connection.prepareStatement("SELECT worker_number, first_name, last_name FROM\n" +
                "workers WHERE worker_number = ?")){
            workerDataQuery.setLong(1, workerNumber);
            ResultSet result = workerDataQuery.executeQuery();
            if(!result.next())
                return null;
            worker = new WorkerData(workerNumber, result.getString("first_name"), result.getString("last_name"));
            return worker;
        }
        catch(SQLException s){
            s.printStackTrace();
        }
        return null;
    }

    public boolean createAccount(long personalID, String currency, String description){
        if(getUserData(personalID ) == null)
            return false;
        try(PreparedStatement userCreation = connection.prepareStatement(
                "INSERT INTO Accounts VALUES(\n" +
                        "nextval('Account_numbers'), ?, NULL, 0, ?, ?, 1000)"))
        {  userCreation.setLong(1, personalID);
            userCreation.setString(2, currency);
            userCreation.setString(3, description);
            userCreation.execute();
        }
        catch(SQLException s){
            s.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean findAccount(long number){
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Accounts\n" +
                "WHERE account_number = ?")){
            statement.setLong(1, number);
            ResultSet set = statement.executeQuery();
            if(set.next())
                return true;
            return false;
        }
        catch(SQLException s){
            s.printStackTrace();
            return false;
        }
    }

    public List<AccountData> getAccounts(long customerNumber){
        List<AccountData> accounts = new ArrayList<>();
        try(PreparedStatement get = connection.prepareStatement("SELECT * FROM Accounts\n" +
                "WHERE owner_number = ?")){
            get.setLong(1, customerNumber);
            ResultSet result = get.executeQuery();
            while(result.next()){
                accounts.add(new AccountData(result.getLong("account_number"), result.getDouble("balance"),
                        result.getString("currency"), result.getString("product_name")));
            }
        }
        catch(SQLException s){
            s.printStackTrace();
        }
        return accounts;
    }

    public boolean makeTransfer(long source, long destination, double value){
        String sql = "BEGIN;\n" +
                "INSERT INTO Transfers VALUES(\n" +
                "nextval('Transfer_numbers'), ?, ?, ?, current_date);\n" +
                "UPDATE Accounts\n" +
                "SET balance = balance + ?\n" +
                "WHERE account_number = ?;\n" +
                "UPDATE Accounts\n"+
                "SET balance = balance - ?\n" +
                "WHERE account_number = ?;\n"+
                "COMMIT;";
        try(PreparedStatement checkCurrency = connection.prepareStatement("SELECT (SELECT Currency FROM Accounts WHERE Account_number = ?) = " +
                "(SELECT Currency FROM Accounts WHERE Account_number = ?) AS Equal")){
            checkCurrency.setLong(1, source);
            checkCurrency.setLong(2, destination);
            ResultSet set = checkCurrency.executeQuery();
            set.next();
            boolean equal = set.getBoolean("Equal");
            if(!equal)
                return false;
        }
        catch(SQLException s){
            s.printStackTrace();
        }

        try(PreparedStatement transfer = connection.prepareStatement(sql))
        {
            transfer.setLong(1, source);
            transfer.setLong(7, source);
            transfer.setLong(2, destination);
            transfer.setLong(5, destination);
            transfer.setDouble(3, value);
            transfer.setDouble(4, value);
            transfer.setDouble(6, value);
            transfer.execute();
        }
        catch(SQLException s){
            s.printStackTrace();
            return false;
        }
        return true;
    }

    public String checkLogin(long login, String password){
        if(password == null)
            throw new NullPointerException();
        try(PreparedStatement checkUsers = connection.prepareStatement("SELECT crypt(?, customer_password) = customer_password AS correct FROM Customers\n" +
                "WHERE customer_number = ?")){
            checkUsers.setString(1, password);
            checkUsers.setLong(2, login);
            ResultSet result = checkUsers.executeQuery();
            if(result.next())
                if(result.getBoolean("correct"))
                    return "User";
                else
                    return "Invalid";
        }
        catch(SQLException s){
            s.printStackTrace();
            return "Error";
        }

        try(PreparedStatement checkWorkers = connection.prepareStatement("SELECT crypt(?, worker_password) = worker_password AS correct FROM Workers\n" +
                "WHERE worker_number = ?")){
            checkWorkers.setString(1, password);
            checkWorkers.setLong(2, login);
            ResultSet result = checkWorkers.executeQuery();
            if(result.next())
                if(result.getBoolean("correct"))
                    return "Worker";
                else
                    return "Invalid";
        }
        catch(SQLException s){
            s.printStackTrace();
            return "Error";
        }

        return "Invalid";
    }

    public List<TransactionData> getTransactions(long number){
        List<TransactionData> list = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Transfers WHERE source_number = ? OR destination_number = ?")){
            statement.setLong(1, number);
            statement.setLong(2, number);
            ResultSet set = statement.executeQuery();
            while(set.next()){
                list.add(new TransactionData(set.getLong("source_number"), set.getLong("destination_number"), set.getDate("transfer_date"), set.getDouble("transfer_value")));
            }
            return list;
        }
        catch(SQLException s){
            s.printStackTrace();
        }
        return null;
    }

    public void close(){  //TODO implement control of state of connection or delete it
        try{connection.close();}
        catch(SQLException e){
            e.printStackTrace();
        }
    }



    public static void main(String[] args){
        DataBaseController controller = getInstance();
        System.out.println(controller.checkLogin(98102700076L, "password"));
    }
}

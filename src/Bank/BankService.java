package Bank;

import DataBase.Conn;
import DataBase.DataBaseOperations;
import DataBase.Quarry;
import Operations.BankOperations;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class BankService implements BankOperations {
    private final int bankId = 54321;
    private final int pin = (int)000;
    Conn c = new Conn();
    DataBaseOperations dataBaseOperations=new DataBaseOperations();
    public boolean authenticate(int bankId, int pin) {
        boolean b = false;
        while (!b) {
            if (bankId == this.bankId && pin == this.pin) {
                b = true;
                System.out.println("you have logged in successfully !!!!\n");
            } else if (this.bankId != bankId) {
                System.out.println("Please enter the correct bank Id");
                break;
            } else if (pin != this.pin) {
                System.out.println("Please enter the correct pin");
                break;
            }
        }
        return b;
    }
    @Override
    public void createNewAccount() {

            Scanner scanner = new Scanner(System.in);
            Random random = new Random();
            System.out.println("Please enter the details to create a new account" + "\n");
                System.out.println("Enter the name:");
                String name = scanner.nextLine() + "\n";
                System.out.println("Enter the Father's name:");
                String fName = scanner.nextLine() + "\n";
                System.out.println("Enter the dob in 'YYYY-MM-DD' format");
                String dob = scanner.nextLine() + "\n";
                int accountNumber = random.nextInt(10000, 99999);
                int userPin = random.nextInt(100, 999);
                if (dataBaseOperations.createAccount(name, fName, dob, accountNumber, userPin) > 0) {
                    System.out.println("insertion is successful");
                    searchAccount(accountNumber, userPin);
                } else System.out.println("fail");
        c.closeConnection();
    }
    @Override
    public boolean deleteAccount(int accountNumber, int pin) {
        boolean b = false;
        try {
            PreparedStatement preparedStatement=c.connection.prepareStatement(Quarry.DELETE_ACCOUNT);
            preparedStatement.setString(1, String.valueOf(accountNumber));
            preparedStatement.setString(2, String.valueOf(pin));
           int deleted= preparedStatement.executeUpdate();
           if (deleted>0){
               System.out.println("successfully deleted");
               b=true;
           }
           else System.out.println("please enter the correct account number or pin");

        } catch (SQLException e) {
            System.out.println("error"+e.getMessage());
        }
        c.closeConnection();
        return b;
    }
    @Override
    public boolean searchAccount(int account, int pin) {
        try {
            PreparedStatement preparedStatement = c.connection.prepareStatement(Quarry.SEARCH_USER);
            preparedStatement.setInt(1, account);
            preparedStatement.setInt(2, pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Check if ResultSet has any rows
            if (resultSet.next()) {
                // Account found, retrieve and print account information
                String name = resultSet.getString("user_name");
                String fname = resultSet.getString("user_Fname");
                String dob = resultSet.getString("user_dob");
                int acc = resultSet.getInt("account_number");
                int p = resultSet.getInt("account_pin");
                double bal = resultSet.getDouble("balance");
                System.out.print("Name: " + name);
                System.out.print("father's Name: " + fname);
                System.out.println("Date of Birth: " + dob);
                System.out.println("Account Number: " + acc);
                System.out.println("PIN: " + p);
                System.out.println("Balance: " + bal);
                return true; // Return true to indicate account found
            } else {
                // No matching account found
                System.out.println("No account found for the provided account number and PIN.");
                return false; // Return false to indicate no account found
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            throw new RuntimeException(e);
        }
    }
    @Override
    public void allAccountsDetails() {
        try {
            int i=1;
            PreparedStatement preparedStatement=c.connection.prepareStatement(Quarry.ALL_USERS_DETAILS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("User:"+i+"\n");
                String name = resultSet.getString("user_name");
                String fname = resultSet.getString("user_Fname");
                String dob = resultSet.getString("user_dob");
                int acc = resultSet.getInt("account_number");
                int p = resultSet.getInt("account_pin");
                double bal = resultSet.getDouble("balance");
                System.out.print("Name: " + name);
                System.out.print("father's Name: " + fname);
                System.out.println("Date of Birth: " + dob);
                System.out.println("Account Number: " + acc);
                System.out.println("PIN: " + p);
                System.out.println("Balance: " + bal);
                System.out.println();
                i++;
            }
        } catch (SQLException e) {
            System.out.println("ERROR:"+e.getMessage());
        }
    }

}

package DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseOperations {
    Conn c = new Conn();

    public boolean updateUPin(int account,int oldPin,int newPin){
        PreparedStatement preparedStatement= null;
        try {
            preparedStatement = c.connection.prepareStatement(Quarry.UPDATE_PIN);
            preparedStatement.setString(1, String.valueOf(newPin));
            preparedStatement.setString(2, String.valueOf(account));
            preparedStatement.setString(3, String.valueOf(oldPin));
            int resultSet= preparedStatement.executeUpdate();
            return resultSet>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public double getBalance(int accountNumber) {
        double balance = 0.0;
        try {
            PreparedStatement preparedStatement = c.connection.prepareStatement(Quarry.CHECK_MONEY);
            preparedStatement.setString(1, String.valueOf(accountNumber));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                balance = resultSet.getInt("balance");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return balance;
    }
    public void makeDeposit(int accountNumber, double money) {
        try {
            PreparedStatement preparedStatement = c.connection.prepareStatement(Quarry.ADD_MONEY);
            preparedStatement.setString(1, String.valueOf(money));
            preparedStatement.setString(2, String.valueOf(accountNumber));
            int resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                System.out.println("money is successfully deposited");
            } else {
                System.out.println("failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void makeWithdraw(int accountNumber, double money) {
        try {
            PreparedStatement preparedStatement = c.connection.prepareStatement(Quarry.SUB_MONEY);
            preparedStatement.setString(1, String.valueOf(money));
            preparedStatement.setString(2, String.valueOf(accountNumber));
            int resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                System.out.println("successfully withdraw");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean userCheck(int accountNumber, int pin) {
        try {
            PreparedStatement preparedStatement = c.connection.prepareStatement(Quarry.User_check);
            preparedStatement.setString(1, String.valueOf(accountNumber));
            preparedStatement.setString(2, String.valueOf(pin));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int accountCount = resultSet.getInt("account_count");
                return (accountCount > 0);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false; // Returning false if no rows were returned
    }
    public int createAccount(String name,String fName,String dob,int accountNumber,int userPin){
        int affected=0;
        try {
            PreparedStatement preparedStatement = c.connection.prepareStatement(Quarry.INSERT_ACCOUNT_DETAILS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, fName);
            preparedStatement.setString(3, dob);
            preparedStatement.setString(4, String.valueOf(accountNumber));
            preparedStatement.setString(5, String.valueOf(userPin));
            preparedStatement.setString(6, String.valueOf(0.0));
            affected= preparedStatement.executeUpdate();//quarry used to execute insertion,update and deletion
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return affected;
    }
}

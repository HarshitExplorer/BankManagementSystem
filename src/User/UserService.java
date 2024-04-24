package User;

import DataBase.Conn;
import DataBase.DataBaseOperations;

import Operations.UserOperations;

import java.util.Scanner;

public class UserService implements UserOperations {
    DataBaseOperations dataBaseOperations = new DataBaseOperations();
    Conn c=new Conn();
    private int accountNumber;
    private int uPin;

    public boolean userLogin(int account, int pin) {
        boolean loggedIn = dataBaseOperations.userCheck(account, pin);
        if (loggedIn) {
            this.accountNumber = account; this.uPin = pin;
        }
        System.out.println(loggedIn ? "!!!logged in successfully!!!" : "Please enter the correct account number or pin");
        return loggedIn;
    }
    @Override
    public void withDraw(double amount) {
        double bal = dataBaseOperations.getBalance(accountNumber);
        if (amount >= bal) {
            System.out.println("Please enter a valid amount");
        } else {
            dataBaseOperations.makeWithdraw(accountNumber, amount);
        }
        c.closeConnection();
    }
    @Override
    public void deposit(double amount) {
        dataBaseOperations.makeDeposit(accountNumber,amount);
        c.closeConnection();
    }
    @Override
    public void transfer(double amount, int transferAccountNumber) {
        double bal = dataBaseOperations.getBalance(accountNumber);
        if (amount >= bal) {
            System.out.println("Can not transfer");
        } else {
            dataBaseOperations.makeWithdraw(accountNumber, amount);
            dataBaseOperations.makeDeposit(transferAccountNumber, amount);
        }
    }
    @Override
    public double balanceCheck() {
        return dataBaseOperations.getBalance(accountNumber);
    }

    @Override
    public boolean logOut() {
        c.closeConnection();
        return false;
    }

    @Override
    public void userPinChange() {
        System.out.println("Enter the new pin:");
        Scanner sc=new Scanner(System.in);
        System.out.println(dataBaseOperations.updateUPin(this.accountNumber,this.uPin, sc.nextInt())?"successfully updated":"failed");
    }
}
package Operations;

public interface UserOperations {
    void withDraw(double amount);
    void deposit(double amount);
    void transfer(double amount,int accountNumber);
    double balanceCheck();
    boolean logOut();
    void userPinChange();
}

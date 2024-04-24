package Operations;

public interface BankOperations {
    void createNewAccount();
    boolean deleteAccount(int accountNumber,int pin);
    boolean searchAccount(int account, int pin);
    void allAccountsDetails();
}

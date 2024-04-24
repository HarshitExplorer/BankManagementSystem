package DataBase;

public class Quarry {
    public static final String INSERT_ACCOUNT_DETAILS = "INSERT INTO account_details (user_name, user_Fname, user_dob, account_number, account_pin,balance) VALUES (?, ?, ?, ?, ?,?)";
    public static final String DELETE_ACCOUNT = "DELETE FROM account_details where account_number=? and account_pin=?";
    public static final String SEARCH_USER = "SELECT * FROM account_details where account_number=? and account_pin=?";
    public static final String ALL_USERS_DETAILS = "SELECT * FROM account_details";
    public static final String User_check = "SELECT COUNT(*) AS account_count FROM  account_details WHERE account_number = ? and account_pin=?";
    public static final String ADD_MONEY="UPDATE account_details SET balance=balance+? WHERE account_number = ?";
    public static final String SUB_MONEY="UPDATE account_details SET balance=balance-? WHERE account_number = ? ";
    public static final String CHECK_MONEY="SELECT balance from account_details WHERE account_number = ?";
    public static final String UPDATE_PIN="UPDATE account_details SET account_pin=? WHERE account_number = ? and account_pin=?";
}

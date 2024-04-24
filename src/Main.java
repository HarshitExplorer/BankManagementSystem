import Bank.BankService;
import User.UserService;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static BankService bankService = new BankService();
    private static int bankId;
    private static int pin;
    public static void bankLogin() {
        System.out.println("Fist login" + "\n");
        System.out.println("Enter the bank ID:");
        Main.bankId = scanner.nextInt();
        System.out.println("Enter the pin:");
        Main.pin = scanner.nextInt();
    }
    public static void userOperations() {
        UserService userService = new UserService();
        boolean programRunning = true;
        boolean b = false;
        while (!b) {
            System.out.println("Press 1 to continue and press 0 to exit ");
            if (scanner.nextInt()==0){
                b=true;
                programRunning=false;
            }
            else {
                System.out.println("Enter the account number");
                int a = scanner.nextInt();
                System.out.println("enter the pin");
                int p = scanner.nextInt();
                b = userService.userLogin(a, p);
            }
        }
        while (programRunning) {
            System.out.println("Please select an operation:\n" + "1. Transfer\n" + "2. Withdraw\n" + "3. Deposit\n"+"4. check balance\n"+"5.change pin\n"+"6.log out\n" + "Enter the number corresponding to your choice:");
            try {
                switch (scanner.nextInt()) {
                    case 1:
                        System.out.println("Enter the account number of transfer account:");
                        int a=scanner.nextInt();
                        System.out.println("Enter the amount :");
                        double m=scanner.nextDouble();
                        userService.transfer(m,a);

                        break;
                    case 2:
                        System.out.println("Enter the amount of money:");
                        userService.withDraw(scanner.nextDouble());
                        break;
                    case 3:
                            System.out.println("Enter the amount of money:");
                            userService.deposit(scanner.nextDouble());
                            break;
                    case 4:
                        System.out.println(STR."Balance :\{userService.balanceCheck()}\n");
                        break;
                    case 5:
                        userService.userPinChange();
                        break;
                    case 6:
                        programRunning= userService.logOut();
                        break;
                    default:
                        System.out.println("please select either 1, 2, 3 or 4 ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type. Please enter number.\n");
                scanner.nextLine();
            }
        }
    }
    public static boolean bankOperations() {

        boolean b1 = false;
        boolean b2 = false;
        boolean b2a = false;
        boolean b3 = false;
        boolean b3a = false;
        boolean b4 = false;
        boolean program=true;
        while (program) {
            System.out.print("""
                    1. Create new account
                    2. delete account
                    3. search bank account
                    4. all account details
                    5. exit
                     Choose the given option by number:""");
            switch (scanner.nextInt()) {
                case 1:
                    bankService.createNewAccount();
                    break;
                case 2:
                    while (!b2) {
                        bankLogin();
                        b2 = bankService.authenticate(bankId, pin);
                    }
                    while (!b2a) {
                        System.out.println("Enter your account number:");
                        int accountNumber = scanner.nextInt();
                        System.out.println("Enter your PIN:");
                        int Pin = scanner.nextInt();
                        b2a = bankService.deleteAccount(accountNumber, Pin);
                    }
                    break;
                case 3:
                    while (!b3) {
                        bankLogin();
                        b3 = bankService.authenticate(bankId, pin);
                    }
                    while (!b3a) {
                        System.out.println("Enter your account number:");
                        int accountNumber = scanner.nextInt();
                        System.out.println("Enter your PIN:");
                        int Pin = scanner.nextInt();
                        b3a = bankService.searchAccount(accountNumber, Pin);
                    }
                    break;
                case 4:
                    while (!b4) {
                        bankLogin();
                        b4 = bankService.authenticate(bankId, pin);
                    }
                    bankService.allAccountsDetails();
                    break;
                case 5:
                    program=false;
                    break;
                default:
                    System.out.println("you choose wrong number :please select correct number");
            }
        }
        return program;
    }

    public static void main(String[] args) {
        boolean programRunning = false;
        while (!programRunning) {
            try {
                System.out.print("1.User Operations \n2.Bank operations\nChoose the given option by number: ");
                int mainOperation = scanner.nextInt();
                switch (mainOperation) {
                    case 1:
                        userOperations();
                        programRunning = false;
                        break;
                    case 2:
                        programRunning =bankOperations() ;
                        break;
                    default:
                        System.out.println("Invalid input. Please enter either 1 or 2.");
                        scanner.nextLine();
                        System.out.println();
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input type. Please enter a number.");
                // Consume the invalid input
                scanner.nextLine();
                System.out.println();
            }
        }
        scanner.close();
    }
}

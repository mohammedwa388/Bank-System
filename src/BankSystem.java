import java.util.Scanner;

class BankSystem {
    static Scanner scanner = new Scanner(System.in);
    static int MAX_USERS = 50;

    static int[] accountNumbers = new int[MAX_USERS];
    static String[] accountNames = new String[MAX_USERS];
    static double[] balances = new double[MAX_USERS];
    static int[] accountTypes = new int[MAX_USERS]; // 1 = Checking, 2 = Savings
    static int[] withdrawalCounts = new int[MAX_USERS];
    static int[] numberOfDeposits = new int[MAX_USERS];
    //--------------------------------------------------------------------
    static String[] usernames = new String[MAX_USERS];
    static String[] passwords = new String[MAX_USERS];
    static int[] roles = new int[MAX_USERS];
    // 0 = Admin, 1 = Checking, 2 = Savings
//----------------------------------------------------------------------
    static int userCount = 0;
    static int nextAccountNumber = 1001;
    static int currentUserIndex;

    public static int login() {
        System.out.print("Username: ");
        String user = scanner.nextLine();

        System.out.print("Password: ");
        String pass = scanner.nextLine();

        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(user) &&
                    passwords[i].equals(pass)) {
                System.out.println("Login Successful!");
                return i;
            }
        }

        System.out.println("Invalid Username or Password");
        return -1;
    }

    static int findAccount(int accNo) {
        for (int i = 0; i < userCount; i++) {
            if (accountNumbers[i] == accNo)
                return i;
        }
        return -1;
    }

    public static void addUser(String name, String username, String password,
                        double balance, int accountType, int role) {

        accountNumbers[userCount] = nextAccountNumber++;
        accountNames[userCount] = name;
        usernames[userCount] = username;
        passwords[userCount] = password;
        balances[userCount] = balance;
        accountTypes[userCount] = accountType;
        roles[userCount] = role;
        withdrawalCounts[userCount] = 0;
        numberOfDeposits[userCount] = 0;

        userCount++;
    }

    public static void printAllUsers() {
        System.out.println("\n------ All Accounts ------");

        if (userCount == 0) {
            System.out.println("No accounts available.");
            return;
        }

        for (int i = 0; i < userCount; i++) {
            System.out.println(
                    "Account Number: " + accountNumbers[i] +
                            " | User Name: " + usernames[i] +
                            " | password: " + passwords[i] +
                            " | Name: " + accountNames[i] +
                            " | Balance: " + balances[i] +
                            " | Type: " + (accountTypes[i] == 1 ? "Checking" : "Savings") +
                            " | Withdrawals: " + withdrawalCounts[i] +
                            " | NumberOfDeposits: " + numberOfDeposits[i]
            );
        }

        System.out.println("--------------------------");
    }

    public static void printWitDep() {

        System.out.println("\n--- Withdraw & Deposit Count ---");

        if (userCount == 0) {
            System.out.println("No accounts available.");
            return;
        }

        System.out.print("Enter Account Number: ");
        int accNo = scanner.nextInt();
        int index = findAccount(accNo);
        if (index != -1) {
            System.out.println(
                    "Account Number: " + accountNumbers[index] +
                            " | Name: " + accountNames[index] +
                            " | Deposits: " + numberOfDeposits[index] +
                            " | Withdrawals: " + withdrawalCounts[index]
            );
            System.out.println("-----------------------------------------------------");
        } else {
            System.out.println("Account Not found");
        }

    }

    public static void createAccount() {
        System.out.print("Enter Account Name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Account Username");
        String userName = scanner.nextLine();

        System.out.println("Enter Account Password");
        String password = scanner.nextLine();

        System.out.println("Enter the permission (0 = Admin, 1 = Checking, 2 = Savings");
        int role = scanner.nextInt();
        if (role > 3) {
            System.out.println("This number is not valid");
            return;
        }

        System.out.print("Initial Deposit: ");
        double deposit = scanner.nextDouble();

        if (role == 2 && deposit < 100) {
            System.out.println("Savings account requires minimum $100");
            return;
        }
        int type = -1;
        if (role == 1) {
            type = role;
        }else if (role == 2){
            type =role;
            }

        addUser(name, userName, password, deposit, type, role);
        System.out.println("Account Created Successfully. Account Number: " + (nextAccountNumber - 1));
    }

    // ===================== Deposit =====================
    static void depositMoney() {
        System.out.print("Enter Account Number: ");
        int accNo = scanner.nextInt();
        int index = findAccount(accNo);

        if (index == -1) {
            System.out.println("Account Not Found");
            return;
        }

        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        balances[index] += amount;

        numberOfDeposits[index]++;
        System.out.println("Deposit Successful. New Balance: " + balances[index]);
    }

    // ===================== Withdraw =====================
    public static void withdrawMoney() {
        System.out.print("Enter Account Number: ");
        int accNo = scanner.nextInt();
        int index = findAccount(accNo);

        if (index == -1) {
            System.out.println("Account Not Found");
            return;
        }

        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();

        if (accountTypes[index] == 1) {

            if (balances[index] >= amount) {
                balances[index] -= amount;
                withdrawalCounts[index]++;
            } else {
                System.out.println("Insufficient Balance");
                return;
            }

        } else { //

            if (withdrawalCounts[index] >= 3) {
                System.out.println("Error: Limit Reached");
                return;
            }
            if (balances[index] - amount < 100) {
                System.out.println("Savings balance cannot go below $100");
                return;
            }
            balances[index] -= amount;
            withdrawalCounts[index]++;
        }

        System.out.println("Withdrawal Successful. New Balance: " + balances[index]);
    }

//    -------------------------------------------------------------------------------------------
public static void displayMyAccountInfo() {
    int i = currentUserIndex;
    String accountTypeName;

    if (accountTypes[i] == 1) {
        accountTypeName = "Checking";
    } else if (accountTypes[i] == 2) {
        accountTypeName = "Savings";
    } else {
        accountTypeName = "Unknown";
    }
    System.out.println("\n--- My Account Information ---");
    System.out.println("Account Number : " + accountNumbers[i]);
    System.out.println("Name           : " + accountNames[i]);
    System.out.println("Username       : " + usernames[i]);
    System.out.println("Account Type   : " + accountTypeName);
    System.out.println("Balance        : " + balances[i]);
    System.out.println("Withdrawals    : " + withdrawalCounts[i]);
    System.out.println("Deposits    : " + numberOfDeposits[i]);
    System.out.println("--------------------------------");
}

    // ===================== Transfer =====================
    public static void transferFunds() {
        System.out.print("Source Account Number: ");
        int source = scanner.nextInt();
        System.out.print("Destination Account Number: ");
        int dest = scanner.nextInt();

        int sIndex = findAccount(source);
        int dIndex = findAccount(dest);

        if (sIndex == -1 || dIndex == -1) {
            System.out.println("One or Both Accounts Not Found");
            return;
        }

        System.out.print("Amount: ");
        double amount = scanner.nextDouble();

        if (balances[sIndex] < amount) {
            System.out.println("Insufficient Funds");
            return;
        }

        balances[sIndex] -= amount;
        balances[dIndex] += amount;
        System.out.println("Transfer Successful");
    }

    // ===================== Interest =====================
    static double applyInterest() {
        double newBalance=0;
        for (int i = 0; i < userCount; i++) {
            if (accountTypes[i] == 2) {
                balances[i] += balances[i] * 0.02;
                newBalance =balances[i];
//                System.out.println("Interest applied to Account #" + accountNumbers[i] + " = " + balances[i]);
            }
        }
        return newBalance;
    }

    static void logout() {
        System.out.println("\nتم تسجيل الخروج بنجاح...");
        System.out.println("================================");
    }

    static void changeAdminCredentials() {
        System.out.println("Enter find Account Number");
        int accN=scanner.nextInt();
        if (findAccount(accN) == -1){
            System.out.println("Account Not Found");
        }else {
            int indexAcount=findAccount(accN);
            System.out.println("\n--- Change Admin Credentials ---");
            System.out.println("1. Change Username");
            System.out.println("2. Change Password");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter New Username: ");
                String newUsername = scanner.nextLine();

                usernames[indexAcount] = newUsername;
                System.out.println("Username changed successfully.");

            } else if (choice == 2) {
                System.out.print("Enter New Password: ");
                String newPassword = scanner.nextLine();

                passwords[indexAcount] = newPassword;
                System.out.println("Password changed successfully.");

            } else {
                System.out.println("Invalid Choice.");
            }
        }
    }

    //    --------------------------------------------------------------------------------------------------

    public static void adminMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
                System.out.println("1. Create Account");
                System.out.println("2. Display All Accounts");
                System.out.println("3. Apply Interest");
                System.out.println("4. Change Username / Password");
                System.out.println("5. Withdraw & Deposit Count");
                System.out.println("6. Logout");
                System.out.print("Choose: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1) {
                    createAccount();
                } else if (choice == 2) {
                    BankSystem.printAllUsers();
                } else if (choice == 3) {
                    BankSystem.applyInterest();
                } else if (choice == 4) {
                    BankSystem.changeAdminCredentials();
                } else if (choice == 5) {
                    BankSystem.printWitDep();
                }else if (choice == 6) {
                    BankSystem.logout();
                    loggedIn = false;
                } else {
                    System.out.println("=========== Invalid choice ================");
                }
        }
    }
    public static void userMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Display My Info");
            System.out.println("4. transferFunds");
            System.out.println("5. Logout");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                BankSystem.depositMoney();
            } else if (choice == 2) {
                BankSystem.withdrawMoney();
            } else if (choice == 3) {
                BankSystem.displayMyAccountInfo();
            } else if (choice == 4) {
                BankSystem.transferFunds();
            } else if (choice == 5) {
                BankSystem.logout();
                loggedIn = false;
            } else {
                System.out.println("=========== Invalid choice ================");
            }
        }
    }
//    ================================================================================================
}
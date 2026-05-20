import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner  scanner =new Scanner(System.in);

        BankSystem.addUser("Admin User", "admin", "123",
                100, 1, 0);

        BankSystem.addUser("Ahmed", "ahmed", "111",
                1000, 1, 1);

        BankSystem.addUser("Sara", "sara", "222",
                1500, 2, 2);

        new Frame();

//        while (true) {
//
//            int logIndex;
//            do {
//                logIndex = BankSystem.login();
//                BankSystem.currentUserIndex = logIndex;
//            } while (logIndex == -1);
//
//
//            System.out.println("\nWelcome, " + BankSystem.accountNames[logIndex]);
//
//            String accountTypeName;
//
//            if (BankSystem.accountTypes[logIndex] == 1) {
//                accountTypeName = "Checking";
//            } else if (BankSystem.accountTypes[logIndex] == 2) {
//                accountTypeName = "Savings";
//            } else {
//                accountTypeName = "Unknown";
//            }
//            System.out.println("Account Type: " + accountTypeName+"\n");
//
//            int role = BankSystem.roles[logIndex];
//            if (role==0){
//                BankSystem.adminMenu();
//                    } else {
//                    BankSystem.userMenu();
//                }
//            }

//--------------------------------------------------
      }
    }
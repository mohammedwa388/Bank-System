import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends JFrame {

    public AdminFrame() {
        this.setTitle("Admin Panel");
        this.setSize(400,350);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container c1 =this.getContentPane();
        c1.setLayout(new GridLayout(6,1,5,5));

        JButton c = new JButton("Create Account");
        JButton d = new JButton("Display All Accounts");
        JButton i = new JButton("Apply Interest");
        JButton ch = new JButton("Change Username/Password");
        JButton w = new JButton("Withdraw & Deposit Count");
        JButton l = new JButton("Logout");

        c1.add(c); c1.add(d); c1.add(i); c1.add(ch); c1.add(w); c1.add(l);

        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccountGUI();
            }
        });

        d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAll();
            }
        });

        i.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyInterestGUI();
            }
        });
        ch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCredentials();
            }
        });
        w.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                witDep();
            }
        });
        l.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Frame();
            }
        } );

        setVisible(true);
    }

    public void createAccountGUI(){
        String name = JOptionPane.showInputDialog("Name");
        if (name == null || name.trim().isEmpty())
            return;
        String user = JOptionPane.showInputDialog("Username");
        if (user == null || user.trim().isEmpty())
            return;
        String pass = JOptionPane.showInputDialog("Password");
        if (pass  == null || pass .trim().isEmpty())
            return;

        Integer type = getValidInt("1-Checking 2-Savings");
        if (type == null)
            return;

        Double bal = getValidDub("Initial Balance");
        if (bal == null)
            return;

        Integer role = getValidInt("Role (0 Admin /1 User)");
        if (role == null)
            return;

        BankSystem.addUser(name,user,pass,bal,type,role);
        JOptionPane.showMessageDialog(this,"Account Created");
    }

    public void displayAll(){
        String printAll=" ";
        for(int i=0;i<BankSystem.userCount;i++){
            printAll+="Account Number: " + BankSystem.accountNumbers[i] +
                            " | User Name: " + BankSystem.usernames[i] +
                            " | password: " + BankSystem.passwords[i] +
                            " | Name: " + BankSystem.accountNames[i] +
                            " | Balance: " + BankSystem.balances[i] +
                            " | Type: " + (BankSystem.accountTypes[i] == 1 ? "Checking" : "Savings") +
                            " | Withdrawals: " + BankSystem.withdrawalCounts[i] +
                            " | NumberOfDeposits: " + BankSystem.numberOfDeposits[i]+"\n";
        }
        JOptionPane.showMessageDialog(this,printAll);
    }

    public void applyInterestGUI(){
        int i =BankSystem.currentUserIndex;
        JOptionPane.showMessageDialog(this ,"Interest applied to Account"+" = "+BankSystem.applyInterest());
    }
    public void changeCredentials() {

        String input = JOptionPane.showInputDialog(this, "Account Number");
        if (input == null)
            return;

        if (input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid Account Number");
            return;
        }
//----------
        int acc;
        try {
            acc = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Account Number must be numeric");
            return;
        }
//------------
        int i = BankSystem.findAccount(acc);
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Not Found");
            return;
        }

        String[] op = {"Username", "Password"};
        int ch = JOptionPane.showOptionDialog(
                this,
                "Change",
                "Change Credentials",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                op,
                op[0] //الزارا الافتراضى
        );


        if (ch == -1)
            return;

        if (ch == 0) {
            String newUser = JOptionPane.showInputDialog(this, "New Username");
            if (newUser != null && !newUser.trim().isEmpty())
                BankSystem.usernames[i] = newUser;
        } else {
            String newPass = JOptionPane.showInputDialog(this, "New Password");
            if (newPass != null && !newPass.trim().isEmpty())
                BankSystem.passwords[i] = newPass;
        }
    }


    public void witDep(){

        String input = JOptionPane.showInputDialog(this, "Account Number");
        if (input == null)
            return;

        if (input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid Account Number");
            return;
        }
//----------
        int acc;
        try {
            acc = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Account Number must be numeric");
            return;
        }
//------------
        int i = BankSystem.findAccount(acc);
        if(i==-1) return;

        JOptionPane.showMessageDialog(this,
                "Deposits: "+BankSystem.numberOfDeposits[i]+
                        "\nWithdrawals: "+BankSystem.withdrawalCounts[i]);
    }

    public Integer getValidInt(String msg) {

        String input = JOptionPane.showInputDialog(this, msg);

        if (input == null)
            return null;

        if (input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid Account Number");
            return null;
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input must be an integer");
            return null;
        }
    }


    public Double getValidDub(String msg) {

        String input = JOptionPane.showInputDialog(this, msg);

        if (input == null)
            return null;

        if (input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid Account Number");
            return null;
        }

        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input must be a number");
            return null;
        }
    }
//    ----------------------------------------------------------------------------
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFrame extends JFrame {

    public UserFrame(){
        setTitle("User Panel");
        setSize(350,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container c = this.getContentPane();
        c.setLayout(new GridLayout(5,1,5,5));


        JButton d = new JButton("Deposit");
        JButton w = new JButton("Withdraw");
        JButton i = new JButton("My Info");
        JButton t = new JButton("Transfer");
        JButton l = new JButton("Logout");

        c.add(d); c.add(w); c.add(i); c.add(t); c.add(l);

        d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });

        w.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });

        i.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                info();
            }
        });
        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transfer();
            }
        });
        l.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Frame();
            }
        });

        setVisible(true);
    }

    public void deposit(){
        Double a =getValidDub("Amount");
        if (a == null)
            return;
        int i = BankSystem.currentUserIndex;
        BankSystem.balances[i]+=a;
        BankSystem.numberOfDeposits[i]++;
        JOptionPane.showMessageDialog(this,"Done");
    }

    public void withdraw(){
        Double a =getValidDub("Amount");
        if (a == null)
            return;
        int i = BankSystem.currentUserIndex;

//        if(BankSystem.balances[i]>=a){
//            BankSystem.balances[i]-=a;
//            BankSystem.withdrawalCounts[i]++;
//            JOptionPane.showMessageDialog(this,"Done");
//        }else{
//            JOptionPane.showMessageDialog(this,"Insufficient Balance");
//        }
//---
        if (BankSystem.accountTypes[i] == 1) {

            if (BankSystem.balances[i] >= a) {
                BankSystem.balances[i] -= a;
                BankSystem.withdrawalCounts[i]++;
            } else {
                JOptionPane.showMessageDialog(this,"Insufficient Balance");
                return;
            }

        } else { //

            if (BankSystem.withdrawalCounts[i] >= 3) {
                JOptionPane.showMessageDialog(this,"Error: Limit Reached");
                return;
            }
            if (BankSystem.balances[i] - a < 100) {
                JOptionPane.showMessageDialog(this,"Savings balance cannot go below $100");
                return;
            }
            BankSystem.balances[i] -= a;
            BankSystem.withdrawalCounts[i]++;
        }

        JOptionPane.showMessageDialog(this,"Withdrawal Successful. New Balance: " + BankSystem.balances[i]);
    }


//    }


//    -----------------------------------------------------------------------------------

    public void info(){
        int i = BankSystem.currentUserIndex;
        String accountTypeName;
        if (BankSystem.accountTypes[i] == 1) {
            accountTypeName = "Checking";
        } else if (BankSystem.accountTypes[i] == 2) {
            accountTypeName = "Savings";
        } else {
            accountTypeName = "Unknown";
        }
        JOptionPane.showMessageDialog(this,"--- My Account Information ---\n"+
                        "Account Number : " + BankSystem.accountNumbers[i]+"\n"+
                        "Name           : " + BankSystem.accountNames[i]+"\n"+
                        "Username       : " + BankSystem.usernames[i]+"\n"+
                        "Account Type   : " + accountTypeName+"\n"+
                        "Balance        : " + BankSystem.balances[i]+"\n"+
                        "Withdrawals    : " + BankSystem.withdrawalCounts[i]+"\n"+
                        "Deposits    : " + BankSystem.numberOfDeposits[i]+"\n"+
                        "--------------------------------");
    }

    public void transfer(){
        Integer to = getValidInt("Destination Acc");
        if (to == null)
            return;
        Double a = getValidDub("Amount");
        if (a == null)
            return;
        int s = BankSystem.currentUserIndex;
        int d = BankSystem.findAccount(to);

        if (BankSystem.balances[s] < a) {
            JOptionPane.showMessageDialog(this,"Insufficient Funds");
            return;
        }

        if(d==-1) return;
        BankSystem.balances[s]-=a;
        BankSystem.balances[d]+=a;
        JOptionPane.showMessageDialog(this,"Transfer Successful");

    }
//    -----------------------------------------------------------
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
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    JTextField user;
    JPasswordField pass;
    Frame(){
        this.setTitle("Bank System - Login");
        this.setResizable(false);
        setSize(300,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        Container c =this.getContentPane();

        c.setLayout(new GridLayout(5,1,5,5));

        user = new JTextField();
        pass = new JPasswordField();
        JButton login = new JButton("Login");

        c.add(new JLabel("Username"));
        c.add(user);
        c.add(new JLabel("Password"));
        c.add(pass);
        c.add(login);


        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        setVisible(true);
    }

    public void login(){
        String u = user.getText();
        String p = new String(pass.getPassword());

        for(int i=0;i<BankSystem.userCount;i++){
            if(BankSystem.usernames[i].equals(u)
                    && BankSystem.passwords[i].equals(p)){
                BankSystem.currentUserIndex = i;
                dispose();
                if(BankSystem.roles[i]==0)
                    new AdminFrame();
                else
                    new UserFrame();
                return;
            }
        }
        JOptionPane.showMessageDialog(this,"Invalid Login");
    }

    }

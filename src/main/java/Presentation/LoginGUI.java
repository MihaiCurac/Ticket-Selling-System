package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Arrays;

public class LoginGUI extends JFrame {

    private JButton loginBtn;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;

    public LoginGUI(Connection con){
        this.setTitle("Login - Ticket Selling System");
        this.setSize(550, 550);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        initialize(panel);
        this.setLocationRelativeTo(null);
        this.setContentPane(panel);
    }

    private void initialize(JPanel panel){
        Font bigFont = new Font("Arial", Font.PLAIN, 34);
        JLabel titleLabel = new JLabel("Login - Ticket Selling System");
        titleLabel.setFont(bigFont);
        titleLabel.setBounds(50, 20, 450, 80);
        usernameLabel = new JLabel("username");
        usernameLabel.setBounds(175, 120, 100, 30);
        usernameField = new JTextField();
        usernameField.setBounds(175, 150, 200, 30);
        passwordLabel = new JLabel("password");
        passwordLabel.setBounds(175, 190, 100, 30);
        passwordField = new JPasswordField();
        passwordField.setBounds(175, 220 ,200 ,30);
        passwordField.setEchoChar('*');
        loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(225 ,300 ,100 ,30);
        panel.add(titleLabel);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginBtn);
    }

    public void loginActionListener(ActionListener actionListener){
        loginBtn.addActionListener(actionListener);
    }

    public String getUsernameField(){
        return usernameField.getText();
    }

    public String getPasswordField(){
        return passwordField.getText();
    }

}

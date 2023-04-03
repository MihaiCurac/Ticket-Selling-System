package Presentation;

import Business.UsersBLL;
import Persistence.GenericDAO;
import Persistence.UsersDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class UsersGUI extends JFrame {

    private JButton insertBtn;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JButton backBtn;
    private JButton listBtn;

    private JLabel uidLabel;
    private JTextField uidField;
    private JLabel usernameLabel;
    private JTextField usernameField;

    private JLabel passwordLabel;
    private JPasswordField passwordField;

    private JLabel roleLabel;
    private JTextField roleField;

    public UsersGUI(AdminView mainGUI, Connection con) {
        UsersGUI u = this;
        LoginGUI loginGUI = new LoginGUI(con);
        UsersDAO userDAO = new UsersDAO(con);
        GenericDAO genericDAO = new GenericDAO();
        this.setTitle("Users Management");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(600, 200, 950, 700);
        this.getContentPane().setLayout(null);
        Font visibleFont = new Font("Arial", Font.PLAIN, 20);
        Font bigFont = new Font("Arial", Font.PLAIN, 36);

        uidLabel = new JLabel("User ID: ");
        uidLabel.setFont(visibleFont);
        uidLabel.setBounds(30, 30, 100, 30);
        getContentPane().add(uidLabel);

        uidField = new JTextField();
        uidField.setBounds(150, 30, 130, 30);
        getContentPane().add(uidField);

        usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(visibleFont);
        usernameLabel.setBounds(30, 70, 120, 30);
        getContentPane().add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 70, 130, 30);
        getContentPane().add(usernameField);

        passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(visibleFont);
        passwordLabel.setBounds(30, 110, 120, 30);
        getContentPane().add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 130, 30);
        getContentPane().add(passwordField);

        roleLabel = new JLabel("Role: ");
        roleLabel.setFont(visibleFont);
        roleLabel.setBounds(30, 150, 100, 30);
        getContentPane().add(roleLabel);

        roleField = new JTextField();
        roleField.setBounds(150, 150, 130, 30);
        getContentPane().add(roleField);

        insertBtn = new JButton("Insert");
        insertBtn.setBounds(30, 190, 100, 30);
        getContentPane().add(insertBtn);

        updateBtn = new JButton("Update");
        updateBtn.setBounds(30, 230, 100, 30);
        getContentPane().add(updateBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(30, 270, 100, 30);
        getContentPane().add(deleteBtn);

        listBtn = new JButton("List");
        listBtn.setBounds(30, 310, 100, 30);
        getContentPane().add(listBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(30, 350, 100, 30);
        getContentPane().add(backBtn);

        this.setLocationRelativeTo(null);

        insertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new UsersBLL(u, userDAO, genericDAO).insertValidate(getUidField(), getUsernameField(), getPasswordField(), getRoleField());
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new UsersBLL(u, userDAO, genericDAO).updateValidate(getUidField(), getUsernameField(), getPasswordField(), getRoleField());
            }
        });

        deleteBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new UsersBLL(u, userDAO, genericDAO).deleteValidate(getUidField());
            }
        });

        listBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new UsersBLL(u, userDAO, genericDAO).listUsers();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
                mainGUI.setVisible(true);

            }
        });
    }
        public String getUidField() {
            return uidField.getText();
        }

        public String getUsernameField() {
            return usernameField.getText();
        }

        public String getPasswordField() {
            return passwordField.getText();
        }

        public String getRoleField() {
            return roleField.getText();
        }

    }

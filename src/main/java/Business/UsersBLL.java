package Business;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Base64;
import javax.swing.*;

import Model.Shows;
import Model.Users;
import Persistence.GenericDAO;
import Persistence.UsersDAO;
import Presentation.LoginGUI;
import Presentation.UsersGUI;

public class UsersBLL {
    private UsersGUI userGUI;
    private UsersDAO userDAO;
    private GenericDAO genericDAO;

    public UsersBLL(UsersGUI userGUI, UsersDAO userDAO, GenericDAO genericDAO) {
        this.userGUI = userGUI;
        this.userDAO = userDAO;
        this.genericDAO = genericDAO;
    }

    public void insertValidate(String uid, String username, String password, String role) throws SQLException{
        boolean correctUid = true;
        int uID = 0;
        if (uid.equals(""))
            uID = userDAO.getLastID();
        else {
            try {
                uID = Integer.parseInt(uid);
            } catch (Exception e) {
                correctUid = false;
                JOptionPane.showMessageDialog(null, "Incorrect ID.");
            }
        }
        if (correctUid) {
            if (ValidateRole(role)) {
                //encode password
                password = encodePassword(password);
                Users myNewUser = new Users(uID, username, password, role);
                userDAO.insertUser(myNewUser);
            } else
                JOptionPane.showMessageDialog(null, "Incorrect input.");
        }
    }

    public void updateValidate(String uid, String username, String password, String role) {
        boolean correctUid = true;
        int uID = 0;
        if (uid.equals(""))
            correctUid = false;
        else {
            try {
                uID = Integer.parseInt(uid);
            } catch (Exception e) {
                correctUid = false;
                JOptionPane.showMessageDialog(null, "Incorrect ID.");
            }
        }
        if (correctUid) {
            if (ValidateRole(role)) {
                password = encodePassword(password);
                Users myUser = new Users(uID, username, password, role);
                userDAO.updateUser(myUser);
            } else
                JOptionPane.showMessageDialog(null, "Incorrect input.");
        }
    }

    public void deleteValidate(String uid) {
        boolean correctUid = true;
        int uID = 0;
        if (uid.equals(""))
            correctUid = false;
        else {
            try {
                uID = Integer.parseInt(uid);
            } catch (Exception e) {
                correctUid = false;
                JOptionPane.showMessageDialog(null, "Incorrect ID.");
            }
        }
        if (correctUid) {
            Users myUser = new Users(uID, "", "", "");
            userDAO.deleteUser(myUser);
        }
    }

    public void listUsers() throws SQLException {
        ArrayList<Users> usersList = userDAO.getUsersList();
        JScrollPane scrlPane = new JScrollPane();
        scrlPane.setBounds(320, 70, 600, 400);
        JTable showTable = new JTable();
        showTable = genericDAO.createTable(usersList);
        showTable.setEnabled(true);
        showTable.setVisible(true);
        scrlPane.setViewportView(showTable);
        userGUI.getContentPane().add(scrlPane);
    }

    public boolean ValidateRole(String role) {
        return role.equals("admin") || role.equals("cashier");
    }

    //function that takes password from GUI and encodes it using Base64
    public String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public String checkAccount(String username, String password) throws SQLException {
        //print to console the username and password
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        try {
            Users foundAccount = userDAO.searchAccount(username, password);
            if(foundAccount.getRole().equals("admin")){
                return "admin";
            }
            else if(foundAccount.getRole().equals("cashier")){
                return "cashier";
            }
            else
                return "null";
        } catch (Exception e) {
            System.out.println("User not found.");
            return "null";
        }
    }
}

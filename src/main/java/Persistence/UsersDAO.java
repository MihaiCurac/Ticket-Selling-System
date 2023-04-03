package Persistence;

import Model.Shows;
import Model.Users;
import Presentation.ShowsGUI;
import Presentation.UsersGUI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;

public class UsersDAO extends GenericDAO {

    private static Connection showConnection;
    private Statement userStatement;

    public UsersDAO(Connection con) {
        showConnection = con;
        try {
            this.userStatement = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //same as ShowDAO, but for Users
    public void insertUser(Users myNewUser) throws SQLException {
        Insert(myNewUser);
    }

    public void updateUser(Users myUser) {
        try {
            Users myNewUser = searchID(myUser.getUid());
            myNewUser.setUsername(myUser.getUsername());
            myNewUser.setPassword(myUser.getPassword());
            myNewUser.setRole(myUser.getRole());
            Update(myNewUser);
        } catch (Exception e) {
            System.out.println("User not found.");
        }
    }

    public void deleteUser(Users myUser) {
        try {
            Users myNewUser = searchID(myUser.getUid());
            if (myNewUser != null)
                Delete(myNewUser);
        } catch (Exception e) {
            System.out.println("Delete error!");
        }
    }

    public int getLastID() throws SQLException {
        ResultSet rs = userStatement.executeQuery("SELECT max(uid) FROM users");
        int lastID = 0;
        while (rs.next()) {
            lastID = rs.getInt(1);
        }
        return lastID;
    }

    public ArrayList<Users> getUsersList() throws SQLException {
        ResultSet rs = userStatement.executeQuery("SELECT * FROM untold.users");
        ArrayList<Users> usersList = new ArrayList<Users>();
        while (rs.next()) {
            Users newUser = new Users(rs.getInt("uid"), rs.getString("username"),
                    rs.getString("password"), rs.getString("role"));
            usersList.add(newUser);
        }
        return usersList;
    }

    public Users searchID(int ID) throws SQLException {
        Users searchedUser = null;
        ArrayList<Users> usersList = getUsersList();
        for (Users currentUser : usersList) {
            if (currentUser.getUid() == ID) {
                searchedUser = currentUser;
                break;
            }
        }
        return searchedUser;
    }

    //function that decodes the Base64-encoded passwords from the database
    public String decodePassword(String password) {
        return new String(Base64.getDecoder().decode(password));
    }

    public Users searchAccount(String username, String password) throws SQLException {
        Users searchedUser = null;
        ArrayList<Users> usersList = getUsersList();
        for (Users currentUser : usersList) {
            if (currentUser.getUsername().equals(username) && (decodePassword(currentUser.getPassword()).equals(password))) {
                //print decoded passwords
                System.out.println("Decoded password: " + decodePassword(currentUser.getPassword()));
                searchedUser = currentUser;
                break;
            }
        }
        return searchedUser;
    }
}

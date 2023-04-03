package Persistence;

import Database.ConnectionFactory;
import Model.Users;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UsersDAOTest {

    Connection con = ConnectionFactory.getConnection();
    UsersDAO usersDAO = new UsersDAO(con);
    String username = "admin";
    String rawPassword = "admin";
    @Test
    void searchAccount() throws SQLException {
        Users searchedUser = usersDAO.searchAccount(username, rawPassword);
        assertEquals(rawPassword, usersDAO.decodePassword(searchedUser.getPassword()));
    }
}
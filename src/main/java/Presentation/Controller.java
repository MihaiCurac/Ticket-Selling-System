package Presentation;

import Business.UsersBLL;
import Database.ConnectionFactory;
import Persistence.GenericDAO;
import Persistence.UsersDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * Contains the Action Listeners for the main window's buttons.
 * @author Mihai Curac
 */
public class Controller {

    private AdminView adminView;
    private LoginGUI loginGUI;
    private PerformancesGUI performancesGUI;
    private ShowsGUI showsGUI;
    private UsersBLL userBLL;
    private UsersGUI userGUI;
    private UsersDAO userDAO;
    private GenericDAO genericDAO;
    private TicketsView ticketView;

    public void start() throws SQLException, ClassNotFoundException {

        Connection con = ConnectionFactory.getConnection();
        if(con != null)
        {
            System.out.println("Connected!");
        }

        loginGUI = new LoginGUI(con);
        adminView = new AdminView();
        performancesGUI = new PerformancesGUI(adminView, con);
        showsGUI = new ShowsGUI(adminView, con);
        userGUI = new UsersGUI(adminView, con);
        userDAO = new UsersDAO(con);
        genericDAO = new GenericDAO();
        userBLL = new UsersBLL(userGUI, userDAO, genericDAO);
        ticketView = new TicketsView(con);
        loginGUI.setVisible(true);
        initializeGUIButtons();
    }

    public void initializeGUIButtons() {
        loginGUI.loginActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(userBLL.checkAccount(loginGUI.getUsernameField(), loginGUI.getPasswordField()).equals("admin")){
                        loginGUI.setVisible(false);
                        adminView.setVisible(true);
                    }
                    else if(userBLL.checkAccount(loginGUI.getUsernameField(), loginGUI.getPasswordField()).equals("cashier")) {
                        loginGUI.setVisible(false);
                        ticketView.setVisible(true);
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Cannot find account with these credentials.");
                }
            }
        });

        adminView.performanceTableBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminView.setVisible(false);
                performancesGUI.setVisible(true);
            }
        });

        adminView.showTableBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminView.setVisible(false);
                showsGUI.setVisible(true);
            }
        });

        adminView.userTableBtnActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminView.setVisible(false);
                userGUI.setVisible(true);
            }
        });
    }
}

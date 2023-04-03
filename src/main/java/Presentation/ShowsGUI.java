package Presentation;

import Business.ShowsBLL;
import Business.TicketsBLL;
import Persistence.GenericDAO;
import Persistence.PerformancesDAO;
import Persistence.ShowsDAO;
import Persistence.TicketsDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *  Contains the GUI elements for the Show operations' window, including Action Listeners for
 *  the buttons.
 * @author Mihai Curac
 */
//the same as for PerformancesGUI, but for Shows
public class ShowsGUI extends JFrame {

    private JButton insertBtn;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JButton backBtn;
    private JButton listBtn;
    private JButton expCsvBtn;
    private JButton expXmlBtn;

    private JLabel sidLabel;
    private JTextField sidField;

    private JLabel titleLabel;
    private JTextField titleField;

    private JLabel datetimeLabel;
    private JTextField datetimeField;

    private JLabel maxticketsLabel;
    private JTextField maxticketsField;

    private JLabel priceLabel;
    private JTextField priceField;

    public ShowsGUI(AdminView mainGUI, Connection con) {
        ShowsGUI s = this;
        ShowsDAO showDAO = new ShowsDAO(con);
        PerformancesDAO performanceDAO = new PerformancesDAO(con);
        TicketsDAO ticketDAO = new TicketsDAO(con);
        GenericDAO genericDAO = new GenericDAO();
        this.setTitle("Shows Management");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(600, 200, 1200, 700);
        this.getContentPane().setLayout(null);
        Font visibleFont = new Font("Arial", Font.PLAIN, 20);
        Font bigFont = new Font("Arial", Font.PLAIN, 36);

        sidLabel = new JLabel("Show ID: ");
        sidLabel.setFont(visibleFont);
        sidLabel.setBounds(30, 30, 100, 30);
        getContentPane().add(sidLabel);

        sidField = new JTextField();
        sidField.setBounds(175, 30, 130, 30);
        getContentPane().add(sidField);

        titleLabel = new JLabel("Title: ");
        titleLabel.setFont(visibleFont);
        titleLabel.setBounds(30, 70, 100, 30);
        getContentPane().add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(175, 70, 130, 30);
        getContentPane().add(titleField);

        datetimeLabel = new JLabel("Date and Time: ");
        datetimeLabel.setFont(visibleFont);
        datetimeLabel.setBounds(30, 110, 150, 30);
        getContentPane().add(datetimeLabel);

        datetimeField = new JTextField();
        datetimeField.setBounds(175, 110, 130, 30);
        getContentPane().add(datetimeField);

        maxticketsLabel = new JLabel("Max Tickets: ");
        maxticketsLabel.setFont(visibleFont);
        maxticketsLabel.setBounds(30, 150, 150, 30);
        getContentPane().add(maxticketsLabel);

        maxticketsField = new JTextField();
        maxticketsField.setBounds(175, 150, 130, 30);
        getContentPane().add(maxticketsField);

        priceLabel = new JLabel("Price: ");
        priceLabel.setFont(visibleFont);
        priceLabel.setBounds(30, 190, 150, 30);
        getContentPane().add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(175, 190, 130, 30);
        getContentPane().add(priceField);

        insertBtn = new JButton("Insert");
        insertBtn.setFont(visibleFont);
        insertBtn.setBounds(30, 230, 100, 30);
        getContentPane().add(insertBtn);

        updateBtn = new JButton("Update");
        updateBtn.setFont(visibleFont);
        updateBtn.setBounds(30, 280, 100, 30);
        getContentPane().add(updateBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setFont(visibleFont);
        deleteBtn.setBounds(30, 330, 100, 30);
        getContentPane().add(deleteBtn);

        listBtn = new JButton("List");
        listBtn.setFont(visibleFont);
        listBtn.setBounds(30, 380, 100, 30);
        getContentPane().add(listBtn);

        backBtn = new JButton("Back");
        backBtn.setFont(visibleFont);
        backBtn.setBounds(30, 430, 100, 30);
        getContentPane().add(backBtn);

        expCsvBtn = new JButton("Export to CSV");
        expCsvBtn.setFont(visibleFont);
        expCsvBtn.setBounds(30, 480, 170, 30);
        getContentPane().add(expCsvBtn);

        expXmlBtn = new JButton("Export to XML");
        expXmlBtn.setFont(visibleFont);
        expXmlBtn.setBounds(30, 530, 170, 30);
        getContentPane().add(expXmlBtn);

        this.setLocationRelativeTo(null);

        insertBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ShowsBLL(s, showDAO, performanceDAO, ticketDAO, genericDAO).insertValidate(getSidField(), getTitleField(), getDatetimeField(), getMaxticketsField(), getPriceField());
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowsBLL(s, showDAO, performanceDAO, ticketDAO, genericDAO).updateValidate(getSidField(), getTitleField(), getDatetimeField(), getMaxticketsField(), getPriceField());
            }
        });

        deleteBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowsBLL(s, showDAO, performanceDAO, ticketDAO, genericDAO).deleteValidate(getSidField());
            }
        });

        listBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ShowsBLL(s, showDAO, performanceDAO, ticketDAO, genericDAO).listShows();
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

        expCsvBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ShowsBLL(s, showDAO, performanceDAO, ticketDAO, genericDAO).prepareExport(getSidField(), "csv");
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        expXmlBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ShowsBLL(s, showDAO, performanceDAO, ticketDAO, genericDAO).prepareExport(getSidField(), "xml");
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public String getSidField() {
        return sidField.getText();
    }

    public String getTitleField() {
        return titleField.getText();
    }

    public String getDatetimeField() {
        return datetimeField.getText();
    }

    public String getMaxticketsField() {
        return maxticketsField.getText();
    }

    public String getPriceField() {
        return priceField.getText();
    }

}

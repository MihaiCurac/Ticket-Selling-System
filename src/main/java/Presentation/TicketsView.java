package Presentation;

import Business.ShowsBLL;
import Business.TicketsBLL;
import Persistence.GenericDAO;
import Persistence.ShowsDAO;
import Persistence.TicketsDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

//exactly like ShowsGUI, but for Tickets
public class TicketsView extends JFrame{

    private JButton insertBtn;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JButton listBtn;
    private JLabel tidLabel;
    private JTextField tidField;
    private JLabel sidLabel;
    private JTextField sidField;
    private JLabel nrSeatsLabel;
    private JTextField nrSeatsField;
    public TicketsView(Connection con) {
        TicketsView t = this;
        TicketsDAO ticketDAO = new TicketsDAO(con);
        ShowsDAO showDAO = new ShowsDAO(con);
        GenericDAO genericDAO = new GenericDAO();
        this.setTitle("Tickets Management");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(600, 200, 900, 700);
        this.getContentPane().setLayout(null);
        Font visibleFont = new Font("Arial", Font.PLAIN, 20);
        Font bigFont = new Font("Arial", Font.PLAIN, 36);

        tidLabel = new JLabel("Ticket ID: ");
        tidLabel.setFont(visibleFont);
        tidLabel.setBounds(30, 30, 100, 30);
        getContentPane().add(tidLabel);

        tidField = new JTextField();
        tidField.setBounds(120, 30, 130, 30);
        getContentPane().add(tidField);

        sidLabel = new JLabel("Show ID: ");
        sidLabel.setFont(visibleFont);
        sidLabel.setBounds(30, 70, 100, 30);
        getContentPane().add(sidLabel);

        sidField = new JTextField();
        sidField.setBounds(120, 70, 130, 30);
        getContentPane().add(sidField);

        nrSeatsLabel = new JLabel("Number of seats: ");
        nrSeatsLabel.setFont(visibleFont);
        nrSeatsLabel.setBounds(30, 110, 200, 30);
        getContentPane().add(nrSeatsLabel);

        nrSeatsField = new JTextField();
        nrSeatsField.setBounds(200, 110, 130, 30);
        getContentPane().add(nrSeatsField);

        insertBtn = new JButton("Insert");
        insertBtn.setFont(visibleFont);
        insertBtn.setBounds(30, 150, 100, 30);
        getContentPane().add(insertBtn);

        updateBtn = new JButton("Update");
        updateBtn.setFont(visibleFont);
        updateBtn.setBounds(140, 150, 100, 30);
        getContentPane().add(updateBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setFont(visibleFont);
        deleteBtn.setBounds(250, 150, 100, 30);
        getContentPane().add(deleteBtn);

        listBtn = new JButton("List");
        listBtn.setFont(visibleFont);
        listBtn.setBounds(360, 150, 100, 30);
        getContentPane().add(listBtn);

        this.setLocationRelativeTo(null);

        insertBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new TicketsBLL(t, ticketDAO, showDAO, genericDAO).insertValidate(getTidField(), getSidField(), getNrSeatsField());
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    new TicketsBLL(t, ticketDAO, showDAO, genericDAO).updateValidate(getTidField(), getSidField(), getNrSeatsField());
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    new TicketsBLL(t, ticketDAO, showDAO, genericDAO).deleteValidate(getTidField());
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        listBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new TicketsBLL(t, ticketDAO, showDAO, genericDAO).listTickets();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
    }

    public String getTidField() {
        return tidField.getText();
    }

    public String getSidField() {
        return sidField.getText();
    }

    public String getNrSeatsField() {
        return nrSeatsField.getText();
    }

}

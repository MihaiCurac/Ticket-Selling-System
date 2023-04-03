package Business;

import Model.Shows;
import Model.Tickets;
import Persistence.GenericDAO;
import Persistence.PerformancesDAO;
import Persistence.ShowsDAO;
import Persistence.TicketsDAO;
import Presentation.ShowsGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ShowsBLL {

    private ShowsGUI showGUI;
    private ShowsDAO showDAO;
    private PerformancesDAO performanceDAO;
    private TicketsDAO ticketDAO;
    private GenericDAO genericDAO;

    public ShowsBLL(ShowsGUI showGUI, ShowsDAO showDAO, PerformancesDAO performanceDAO, TicketsDAO ticketDAO, GenericDAO genericDAO) {
        this.showGUI = showGUI;
        this.showDAO = showDAO;
        this.performanceDAO = performanceDAO;
        this.ticketDAO = ticketDAO;
        this.genericDAO = genericDAO;
    }

    public void insertValidate(String sid, String title, String date_time, String max_tickets, String price) throws SQLException {
        boolean correctSid = true;
        int sID = 0;
        if (sid.equals(""))
            sID = showDAO.getLastID();
        else {
            try {
                sID = Integer.parseInt(sid);
            } catch (Exception e) {
                correctSid = false;
                JOptionPane.showMessageDialog(null, "Incorrect ID.");
            }
        }
        if (correctSid) {
            if (ValidateShow(date_time, max_tickets, price)) {
                Shows myNewShow = new Shows(sID, title, date_time, Integer.parseInt(max_tickets), Double.parseDouble(price));
                showDAO.insertShow(myNewShow);
            } else
                JOptionPane.showMessageDialog(null, "Incorrect input.");
        }
    }

    public void updateValidate(String sid, String title, String date_time, String max_tickets, String price) {
        boolean correctSid = true;
        int sID = 0;
        if (sid.equals(""))
            correctSid = false;
        else {
            try {
                sID = Integer.parseInt(sid);
            } catch (Exception e) {
                correctSid = false;
                JOptionPane.showMessageDialog(null, "Incorrect ID.");
            }
        }
        if (correctSid) {
            if (ValidateShow(date_time, max_tickets, price)) {
                Shows myShow = new Shows(sID, title, date_time, Integer.parseInt(max_tickets), Double.parseDouble(price));
                showDAO.updateShow(myShow);
            } else
                JOptionPane.showMessageDialog(null, "Incorrect input.");
        }
    }

    public void deleteValidate(String sid) {
        boolean correctSid = true;
        int sID = 0;
        try {
            sID = Integer.parseInt(sid);
        } catch (Exception e) {
            correctSid = false;
            JOptionPane.showMessageDialog(null, "Incorrect ID.");
        }
        if (correctSid) {
            Shows myShow = new Shows(sID, "", "", 0, 0.0);
            showDAO.deleteShow(myShow);
        }
    }

    public void listShows() throws SQLException {
        ArrayList<Shows> showsList = showDAO.getShowsList();
        JScrollPane scrlPane = new JScrollPane();
        scrlPane.setBounds(320, 70, 850, 400);
        JTable showTable = new JTable();
        showTable = genericDAO.createTable(showsList);
        ArrayList<String> performances = new ArrayList<>();
        for (Shows shows : showsList) {
            performances.add(performanceDAO.searchPerformances(shows.getSid()));
        }
        DefaultTableModel model = (DefaultTableModel) showTable.getModel();
        model.addColumn("performances", performances.toArray());
        showTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        showTable.getColumnModel().getColumn(2).setPreferredWidth(170);
        showTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        showTable.getColumnModel().getColumn(5).setPreferredWidth(400);
        showTable.setEnabled(true);
        showTable.setVisible(true);
        scrlPane.setViewportView(showTable);
        showGUI.getContentPane().add(scrlPane);
    }

    public void prepareExport(String sid, String format) throws SQLException, IOException {
        String ticketsExp = "";
        boolean correctSid = true;
        int sID = 0;
        if (sid.equals(""))
            correctSid = false;
        else {
            try {
                sID = Integer.parseInt(sid);
            } catch (Exception e) {
                correctSid = false;
                JOptionPane.showMessageDialog(null, "Incorrect ID.");
            }
        }
        if (correctSid) {
            Shows show = showDAO.searchID(sID);
            if (show != null) {
                ticketsExp = ticketsExp.concat("Tickets for Show: " + show.getTitle());
                ticketsExp = ticketsExp.concat(", Date and Time: " + show.getDate_time());
                ticketsExp = ticketsExp.concat("\n");
                ArrayList<Tickets> ticketsList = ticketDAO.getTicketsList();
                for (Tickets ticket : ticketsList) {
                    if(ticket.getSid() == sID){
                        ticketsExp = ticketsExp.concat("Ticket ID: " + ticket.getTid());
                        ticketsExp = ticketsExp.concat(", Number of seats: " + ticket.getNrSeats());
                        ticketsExp = ticketsExp.concat(", Price: " + ticket.getNrSeats() * show.getPrice());
                        ticketsExp = ticketsExp.concat("\n");
                    }
                }
                TicketExporterFactory ticketFactory = new TicketExporterFactory();
                try {
                    TicketExporter ticketExporter = ticketFactory.getTicketExporter(format);
                    ticketExporter.exportTickets(ticketsExp);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            } else
                JOptionPane.showMessageDialog(null, "Show not found.");
        } else
            JOptionPane.showMessageDialog(null, "Incorrect input.");
    }

    public boolean ValidateDatetime(String datetime) {
        if (datetime.length() != 16)
            return false;
        if (datetime.charAt(4) != '-' || datetime.charAt(7) != '-' || datetime.charAt(10) != ' ' || datetime.charAt(13) != ':')
            return false;
        for (int i = 0; i < 4; i++)
            if (datetime.charAt(i) < '0' || datetime.charAt(i) > '9')
                return false;
        for (int i = 5; i < 7; i++)
            if (datetime.charAt(i) < '0' || datetime.charAt(i) > '9')
                return false;
        for (int i = 8; i < 10; i++)
            if (datetime.charAt(i) < '0' || datetime.charAt(i) > '9')
                return false;
        for (int i = 11; i < 13; i++)
            if (datetime.charAt(i) < '0' || datetime.charAt(i) > '9')
                return false;
        for (int i = 14; i < 16; i++)
            if (datetime.charAt(i) < '0' || datetime.charAt(i) > '9')
                return false;
        return true;
    }

    public boolean ValidateTickets(String tickets) {
        if (tickets.charAt(0) == '0')
            return false;
        for (int i = 0; i < tickets.length(); i++)
            if (tickets.charAt(i) < '0' || tickets.charAt(i) > '9')
                return false;
        return true;
    }

    public boolean ValidatePrice(String price) {
        int dot = 0;
        for (int i = 0; i < price.length(); i++) {
            if (price.charAt(i) == '.') {
                dot++;
                continue;
            }
            if (price.charAt(i) < '0' || price.charAt(i) > '9')
                return false;
        }
        if (dot > 1)
            return false;
        return true;
    }

    public boolean ValidateShow(String datetime, String tickets, String price) {
        if (!ValidateDatetime(datetime))
            return false;
        if (!ValidateTickets(tickets))
            return false;
        if (!ValidatePrice(price))
            return false;
        return true;
    }
}

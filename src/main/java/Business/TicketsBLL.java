package Business;

import Model.Shows;
import Model.Tickets;
import Persistence.GenericDAO;
import Persistence.ShowsDAO;
import Persistence.TicketsDAO;
import Presentation.ShowsGUI;
import Presentation.TicketsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketsBLL {

    private TicketsView TicketsView;
    private TicketsDAO ticketDAO;
    private ShowsDAO showDAO;
    private GenericDAO genericDAO;

    public TicketsBLL(TicketsView TicketsView, TicketsDAO ticketDAO, ShowsDAO showDAO, GenericDAO genericDAO) {
        this.TicketsView = TicketsView;
        this.ticketDAO = ticketDAO;
        this.showDAO = showDAO;
        this.genericDAO = genericDAO;
    }

    public void insertValidate(String tid, String sid, String nrSeats) throws SQLException {
        boolean correctTid = true;
        int tID = 0;
        if (tid.equals(""))
            tID = ticketDAO.getLastID();
        else {
            try {
                tID = Integer.parseInt(tid);
            } catch (Exception e) {
                correctTid = false;
                JOptionPane.showMessageDialog(null, "Incorrect ID.");
            }
        }
        boolean correctSid = true;
        int sID = 0;
        if (sid.equals(""))
            correctSid = false;
        else {
            try {
                sID = Integer.parseInt(sid);
            } catch (Exception e) {
                correctSid = false;
                JOptionPane.showMessageDialog(null, "Incorrect Show ID.");
            }
        }
        if (correctTid & correctSid) {
            Shows myShow = showDAO.searchID(sID);
            if (ValidateNrSeats(nrSeats, sID)) {
                Tickets myNewTicket = new Tickets(tID, sID, Integer.parseInt(nrSeats));
                ticketDAO.insertTicket(myNewTicket);
                myShow.setMax_tickets(myShow.getMax_tickets() - Integer.parseInt(nrSeats));
                showDAO.updateShow(myShow);
            } else
                JOptionPane.showMessageDialog(null, "Incorrect input.");
        }
    }

    public void updateValidate(String tid, String sid, String nrSeats) throws SQLException {
        boolean correctTid = true;
        int tID = 0;
        if (tid.equals(""))
            correctTid = false;
        else {
            try {
                tID = Integer.parseInt(tid);
            } catch (Exception e) {
                correctTid = false;
                JOptionPane.showMessageDialog(null, "Incorrect ID.");
            }
        }
        boolean correctSid = true;
        int sID = 0;
        if (sid.equals(""))
            correctSid = false;
        else {
            try {
                sID = Integer.parseInt(sid);
            } catch (Exception e) {
                correctSid = false;
                JOptionPane.showMessageDialog(null, "Incorrect Show ID.");
            }
        }
        if (correctTid && correctSid) {
            Shows myShow = showDAO.searchID(sID);
            if (myShow != null) {
                if (Integer.parseInt(nrSeats) <= myShow.getMax_tickets()) {
                    Tickets myOgTicket = ticketDAO.searchID(tID);
                    Tickets myNewTicket = new Tickets(tID, sID, Integer.parseInt(nrSeats));
                    Shows oldShow = showDAO.searchID(myOgTicket.getSid());
                    if (oldShow.getSid() != sID) {
                        oldShow.setMax_tickets(oldShow.getMax_tickets() + myOgTicket.getNrSeats());
                        myShow.setMax_tickets(myShow.getMax_tickets() - Integer.parseInt(nrSeats));
                        showDAO.updateShow(oldShow);
                    }
                    else {
                        myShow.setMax_tickets(myShow.getMax_tickets() + myOgTicket.getNrSeats() - Integer.parseInt(nrSeats));
                    }
                    showDAO.updateShow(myShow);
                    ticketDAO.updateTicket(myNewTicket);
                } else
                    JOptionPane.showMessageDialog(null, "Not enough seats available.");
            } else
                JOptionPane.showMessageDialog(null, "Show not found.");
        } else
            JOptionPane.showMessageDialog(null, "Incorrect input.");
    }

    public void deleteValidate(String tid) throws SQLException {
        boolean correctTid = true;
        int tID = 0;
        try {
            tID = Integer.parseInt(tid);
        } catch (Exception e) {
            correctTid = false;
            JOptionPane.showMessageDialog(null, "Incorrect ID.");
        }
        if (correctTid) {
            Tickets myTicket = new Tickets(tID, 0, 0);
            Tickets myOgTicket = ticketDAO.searchID(tID);
            Shows oldShow = showDAO.searchID(myOgTicket.getSid());
            oldShow.setMax_tickets(oldShow.getMax_tickets() + myOgTicket.getNrSeats());
            ticketDAO.deleteTicket(myTicket);
        }
    }

    public void listTickets() throws SQLException {

        ArrayList<Tickets> ticketsList = ticketDAO.getTicketsList();
        JScrollPane scrlPane = new JScrollPane();
        scrlPane.setBounds(140, 200, 600, 400);
        JTable ticketTable = new JTable();
        ticketTable = genericDAO.createTable(ticketsList);
        DefaultTableModel model = (DefaultTableModel) ticketTable.getModel();
        ArrayList<Double> prices = new ArrayList<>();
        for (Tickets ticket : ticketsList) {
            Shows show = showDAO.searchID(ticket.getSid());
            prices.add(ticket.getNrSeats() * show.getPrice());
        }
        model.addColumn("price", prices.toArray());
        ticketTable.setEnabled(true);
        ticketTable.setVisible(true);
        scrlPane.setViewportView(ticketTable);
        TicketsView.getContentPane().add(scrlPane);
    }

    public boolean ValidateNrSeats(String nrSeats, int sID) throws SQLException {
        if (nrSeats.charAt(0) == '0')
            return false;
        for (int i = 0; i < nrSeats.length(); i++)
            if (nrSeats.charAt(i) < '0' || nrSeats.charAt(i) > '9')
                return false;
        Shows myShow = showDAO.searchID(sID);
        if (myShow != null) {
            if (Integer.parseInt(nrSeats) <= myShow.getMax_tickets()) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Not enough seats available.");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Show not found.");
            return false;
        }
    }

}

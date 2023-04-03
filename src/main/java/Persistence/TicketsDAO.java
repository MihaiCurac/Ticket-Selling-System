package Persistence;

import Model.Tickets;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TicketsDAO extends GenericDAO{

    private static Connection ticketConnection;
    private Statement ticketStatement;

    public TicketsDAO(Connection con){
        ticketConnection = con;
        try {
            this.ticketStatement = con.createStatement();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertTicket(Tickets myNewTicket) throws SQLException {
        Insert(myNewTicket);
    }

    public void updateTicket(Tickets myTicket) {
        try {
            Tickets myNewTicket = searchID(myTicket.getTid());
            myNewTicket.setSid(myTicket.getSid());
            myNewTicket.setNrSeats(myTicket.getNrSeats());
            Update(myNewTicket);
        } catch (Exception e) {
            System.out.println("Ticket not found.");
        }
    }

    public void deleteTicket(Tickets myTicket) {
        try {
            Tickets myNewTicket = searchID(myTicket.getTid());
            if (myNewTicket != null)
                Delete(myNewTicket);
        } catch (Exception e) {
            System.out.println("Delete error!");
        }
    }

    public int getLastID() throws SQLException {
        ResultSet rs = ticketStatement.executeQuery("SELECT max(tid) FROM tickets");
        int lastID = 0;
        while (rs.next()) {
            lastID = rs.getInt(1);
        }
        return lastID + 1;
    }

    public ArrayList<Tickets> getTicketsList() throws SQLException {
        ResultSet rs = ticketStatement.executeQuery("SELECT * FROM untold.tickets");
        ArrayList<Tickets> ticketsList = new ArrayList<Tickets>();
        while (rs.next()){
            Tickets newTicket = new Tickets(rs.getInt("tid"), rs.getInt("sid"), rs.getInt("nrSeats"));
            ticketsList.add(newTicket);
        }
        return ticketsList;
    }

    public Tickets searchID(int ID) throws SQLException {
        Tickets searchedTicket = null;
        ArrayList<Tickets> ticketsList = getTicketsList();
        for (Tickets currentTicket : ticketsList) {
            if (currentTicket.getTid() == ID) {
                searchedTicket = currentTicket;
                break;
            }
        }
        return searchedTicket;
    }
}

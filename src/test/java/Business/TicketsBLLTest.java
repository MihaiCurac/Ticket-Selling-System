package Business;

import Database.ConnectionFactory;
import Persistence.GenericDAO;
import Persistence.ShowsDAO;
import Persistence.TicketsDAO;
import Presentation.TicketsView;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TicketsBLLTest {

    Connection con = ConnectionFactory.getConnection();
    TicketsView ticketsView = new TicketsView(con);
    TicketsDAO ticketsDAO = new TicketsDAO(con);
    ShowsDAO showsDAO = new ShowsDAO(con);
    GenericDAO genericDAO = new GenericDAO();
    TicketsBLL ticketsBLL = new TicketsBLL(ticketsView, ticketsDAO, showsDAO, genericDAO);

    int tid = 1;
    int sid = 2;
    String nrSeats = "25000";

    @Test
    void testInsertValidate() throws SQLException {
    }

    @Test
    void validateNrSeats() throws SQLException {
        assertFalse(ticketsBLL.ValidateNrSeats(nrSeats, sid));
    }
}
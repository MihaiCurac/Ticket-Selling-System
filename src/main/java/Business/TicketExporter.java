package Business;

import java.io.IOException;

public interface TicketExporter {
    public void exportTickets(String ticketsExp) throws IOException;
}

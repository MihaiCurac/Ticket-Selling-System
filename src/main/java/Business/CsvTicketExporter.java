package Business;
import Model.Shows;
import Model.Tickets;
import Persistence.ShowsDAO;
import Persistence.TicketsDAO;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
public class CsvTicketExporter implements TicketExporter{

    private final String filename;

    public CsvTicketExporter(String filename) {
        this.filename = filename;
    }

    @Override
    public void exportTickets(String ticketsExp) throws IOException {
        FileWriter csvWriter = new FileWriter(filename);
        csvWriter.append(ticketsExp);
        csvWriter.flush();
        csvWriter.close();
    }
}

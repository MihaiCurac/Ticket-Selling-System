package Business;

import java.io.FileWriter;
import java.io.IOException;

public class XmlTicketExplorer implements TicketExporter{

    private String filename;

    public XmlTicketExplorer(String filename) {
        this.filename = filename;
    }

    @Override
    public void exportTickets(String ticketsExp) throws IOException {
        FileWriter xmlWriter = new FileWriter(filename);
        xmlWriter.append(ticketsExp);
        xmlWriter.flush();
        xmlWriter.close();
    }
}
